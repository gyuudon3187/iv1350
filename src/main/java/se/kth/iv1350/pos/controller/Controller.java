package se.kth.iv1350.pos.controller;

import java.util.LinkedList;
import se.kth.iv1350.pos.integration.DiscountDatabase;
import se.kth.iv1350.pos.integration.ErrorDatabase;
import se.kth.iv1350.pos.integration.ExternalInventorySystem;
import se.kth.iv1350.pos.integration.ExternalAccountingSystem;
import se.kth.iv1350.pos.integration.DatabaseFailureException;
import se.kth.iv1350.pos.integration.ItemIdentifierNotFoundException;
import se.kth.iv1350.pos.integration.ItemIdentifierFormatException;
import se.kth.iv1350.pos.integration.ItemNotInInventoryException;
import se.kth.iv1350.pos.integration.MemberDatabase;
import se.kth.iv1350.pos.integration.ReceiptPrinter;
import se.kth.iv1350.pos.integration.TotalRevenueObserver;
import se.kth.iv1350.pos.model.Sale;
import se.kth.iv1350.pos.model.Receipt;
import se.kth.iv1350.pos.model.CashRegister;
import se.kth.iv1350.pos.view.View;
import se.kth.iv1350.pos.dto.ItemDTO;
import se.kth.iv1350.pos.dto.SaleDTO;
import se.kth.iv1350.pos.dto.DiscountDTO;
import se.kth.iv1350.pos.util.Decoder;

/**
 * This is the program's only controller. All calls to methods in the model
 * layer pass through this class.
 */
public class Controller {

    // Integration related variables
    private CashRegister cashReg;

    private DiscountDatabase discountdb;
    
    private ErrorDatabase errordb;

    private ExternalInventorySystem extInvSys;

    private ExternalAccountingSystem extAcctSys;

    private MemberDatabase memberdb;

    private ReceiptPrinter printer;
    
    private LinkedList<TotalRevenueObserver> totalRevenueObservers;

    // Other variables
    private Sale currentSale;
    
    // Helper variables
    private Decoder decoder;
    
    /**
     * Constructor.
     */
    public Controller() {
        cashReg = CashRegister.getCashRegister();
        discountdb = DiscountDatabase.getDiscountDatabase();
        errordb = ErrorDatabase.getErrorDatabase();
        extInvSys = ExternalInventorySystem.getExternalInventorySystem();
        extAcctSys = ExternalAccountingSystem.getExternalAccountingSystem();
        memberdb = MemberDatabase.getMemberDatabase();
        totalRevenueObservers = new LinkedList<>();
        printer = new ReceiptPrinter();
        decoder = new Decoder();
    }
    
    /**
     * Adds an observer for observing the total revenue.
     * 
     * @param observer 
     */
    public void addTotalRevenueObserver(View observer) {
        if(observer instanceof TotalRevenueObserver totRevObserver) {
            totalRevenueObservers.add(totRevObserver);
        }
    }
    
    public void removeTotalRevenueObserver(View observer) {
        if(observer instanceof TotalRevenueObserver totRevObserver) {
            totalRevenueObservers.remove(totRevObserver);
        }
    }
    
    public LinkedList<TotalRevenueObserver> getTotalRevenueObservers() {
        return totalRevenueObservers;
    }
    
    /**
     * Starts the sale by creating and instantiating a new sale object.
     * 
     * @return  a summary of the sale used by the UI
     */
    public SaleDTO startSale() {
        int latestSaleIdentifier = extAcctSys.getLatestSaleIdentifier();
        currentSale = new Sale(latestSaleIdentifier);
        SaleDTO summaryOfCurrentSale = new SaleDTO(currentSale);
        
        return summaryOfCurrentSale;
    }

    /**
     * Fetch the item to which the scanned identifier belongs and
     * add it to the ongoing sale.
     * 
     * @param   itemIdentifier  identifier of the item to be registered in sale
     * @return  a summary of the sale used by the UI
     * @throws  ItemIdentifierFormatException   if the identifier does't equal 
     *                                              the expected length, i.e.
     *                                              9 digits
     * @throws  ItemIdentifierNotFoundException if no item with the specified
     *                                              identifier could be found
     * @throws OperationFailedException         if the operation for some reason
     *                                              fails
     */
    public SaleDTO registerItem(int itemIdentifier) throws ItemIdentifierFormatException,
                                                            ItemIdentifierNotFoundException,
                                                            OperationFailedException {
        SaleDTO summaryOfCurrentSale = null;
        
        // Seminar 4 task 1 a)
        try {
            ItemDTO containerOfItemInfo = extInvSys.fetchItemInfo(itemIdentifier);
            summaryOfCurrentSale = currentSale.addItemToSale(containerOfItemInfo);
        } catch (ItemIdentifierFormatException iife) {
            throw new ItemIdentifierFormatException();
            
        } catch (ItemIdentifierNotFoundException iinfe) {
            errordb.logException(iinfe);
            throw new ItemIdentifierNotFoundException();
            
        } catch (ItemNotInInventoryException iniie) {
            errordb.logException(iniie);
            rectifyInventoryQtyDiscrepancies(iniie);
            
        // Seminar 4 task 1 b)
        } catch (DatabaseFailureException e) {
            errordb.logException(e);
            throw new OperationFailedException("Could not execute operation.", e);
        }
        
        return summaryOfCurrentSale;
    }

    /**
     * Set the quantity of the latest scanned item.
     * 
     * @param newQuantity   the new quantity to which the item's quantity will be set
     * @return              a summary of the sale used by the UI
     */
    public SaleDTO setItemQuantityForLatestScannedItem(int newQuantity) {
        SaleDTO summaryOfCurrentSale = currentSale.setItemQuantityForLatestScannedItem(newQuantity);
        return summaryOfCurrentSale;
    }

    /**
     * Ends ongoing sale by finalizing the total price and total tax amount,
     * and then marking the sale as no longer ongoing.
     * 
     * @return a summary of the sale used by the UI
     */
    public SaleDTO endSale() {
        SaleDTO summaryOfCurrentSale = currentSale.endSale();
        return summaryOfCurrentSale;
    }

    /**
     * Finds all applicable discounts and applies them to the ongoing sale.
     * 
     * @param customerID    the customer's member ID
     * @return              a summary of the sale used by the UI
     */
    public SaleDTO checkForApplicableDiscountsAndApply(String customerID) {
        boolean customerIsMember = memberdb.validateCustomerID(customerID);
        LinkedList<LinkedList<? extends DiscountDTO>> discounts = new LinkedList<>() {{
          add(discountdb.getItemDiscounts());  
          add(discountdb.getSaleDiscounts());
        }};
        
        SaleDTO summaryOfCurrentSale = currentSale.checkForApplicableDiscountsAndApply(customerIsMember, discounts);
                
        return summaryOfCurrentSale;
    }

    /**
     * Process the payment received from the customer, update the logs
     * related to the sale and print the receipt of the completed sale.
     * 
     * @param cashAmount    the cash amount paid by the customer
     */
    public void pay(double cashAmount) {
        SaleDTO completedSale = currentSale.processPayment(cashAmount, cashReg);
        
        this.updateExternalSystemLogsWithCompletedSale(completedSale);
        
        Receipt receipt = new Receipt(completedSale);
        printer.printReceipt(receipt);
    }
    
    private void updateExternalSystemLogsWithCompletedSale(SaleDTO completedSale) {
        extAcctSys.updateSaleLog(completedSale);
        extAcctSys.addTotalRevenueObservers(totalRevenueObservers);
        extAcctSys.updateFinancialLog(completedSale);
        extInvSys.updateInventoryLog(completedSale);
    }
    
    private void rectifyInventoryQtyDiscrepancies(ItemNotInInventoryException iniie) {
        ItemDTO itemToBeAddedToSale = iniie.getItemToBeAddedToSale();
        int currentQuantityinInventory =
                itemToBeAddedToSale.getItemQuantityInventory();
        int quantityToAdjustQuantityBy = 1;
        int correctQuantity = 
                currentQuantityinInventory + quantityToAdjustQuantityBy;

        extInvSys.setItemQuantityInInventory(itemToBeAddedToSale, correctQuantity);
    }

}
