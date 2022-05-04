package se.kth.iv1350.pos.controller;

import java.util.LinkedList;
import se.kth.iv1350.pos.integration.DiscountDatabase;
import se.kth.iv1350.pos.integration.ExternalInventorySystem;
import se.kth.iv1350.pos.integration.ExternalAccountingSystem;
import se.kth.iv1350.pos.integration.MemberDatabase;
import se.kth.iv1350.pos.integration.ReceiptPrinter;
import se.kth.iv1350.pos.model.Sale;
import se.kth.iv1350.pos.model.Receipt;
import se.kth.iv1350.pos.model.CashRegister;
import se.kth.iv1350.pos.dto.ItemDTO;
import se.kth.iv1350.pos.dto.ItemDiscountDTO;
import se.kth.iv1350.pos.dto.SaleDTO;
import se.kth.iv1350.pos.dto.SaleDiscountDTO;
import se.kth.iv1350.pos.util.Decoder;

/**
 * This is the program's only controller. All calls to methods in the model
 * layer pass through this class.
 */
public class Controller {

    // Integration related variables
    private CashRegister cashReg;

    private DiscountDatabase discountdb;

    private ExternalInventorySystem extInvSys;

    private ExternalAccountingSystem extAcctSys;

    private MemberDatabase memberdb;

    private ReceiptPrinter printer;

    // Other variables
    private Sale currentSale;
    
    // Helper variables
    private Decoder decoder;
    
    /**
     * Constructor.
     */
    public Controller() {
        cashReg = new CashRegister();
        discountdb = new DiscountDatabase();
        extInvSys = new ExternalInventorySystem();
        extAcctSys = new ExternalAccountingSystem();
        memberdb = new MemberDatabase();
        printer = new ReceiptPrinter();
        decoder = new Decoder();
    }
    
    /**
     * Updates the the sale and financial logs in the external accounting
     * system, and the inventory log in the external inventory system.
     * 
     * @param completedSale 
     */
    private void updateExternalSystemLogsWithCompletedSale(SaleDTO completedSale) {
        extAcctSys.updateSaleLog(completedSale);
        extAcctSys.updateFinancialLog(completedSale);
        extInvSys.updateInventoryLog(completedSale);
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
     * @param   unprocessedBarcode
     * @return  a summary of the sale used by the UI
     */
    public SaleDTO scanItem(String unprocessedBarcode) {
        int itemIdentifier = decoder.decodeBarcode(unprocessedBarcode);
        boolean validIdentifier = extInvSys.verifyIdentifier(itemIdentifier);
        SaleDTO summaryOfCurrentSale = null;

        if (validIdentifier) {
            ItemDTO containerOfItemInfo = extInvSys.fetchItemInfo(itemIdentifier);
            summaryOfCurrentSale = currentSale.addItemToSale(containerOfItemInfo);
        } else {
            // Placeholder insert exception for invalid item identifier.
            // Since exceptions are not allowed for this seminar task,
            // the invalid identifier message will be displayed in View instead
            // upon returning 'null'.
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
        SaleDTO summaryOfCurrentSale = currentSale.endSale() ;
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
        LinkedList<ItemDiscountDTO> itemDiscounts = discountdb.getItemDiscounts();
        LinkedList<SaleDiscountDTO> saleDiscounts = discountdb.getSaleDiscounts();
        SaleDTO summaryOfCurrentSale = currentSale.checkForApplicableDiscountsAndApply(customerIsMember, itemDiscounts, saleDiscounts);
                
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

}
