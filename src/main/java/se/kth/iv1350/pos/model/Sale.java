package se.kth.iv1350.pos.model;

import java.util.LinkedList;
import java.text.SimpleDateFormat;
import java.util.Date;
import se.kth.iv1350.pos.dto.*;
import se.kth.iv1350.pos.model.discountalgorithms.*;

/**
 * Class containing all information relevant to an ongoing, single sale.
 */
public class Sale {
    private final LinkedList<Item> itemsInSale;
    
    private Payment payment;

    private String saleDateAndTime;
    
    private final int saleIdentifier;

    private double runningTotal;

    private double totalPrice;

    private double totalVAT;
    
    private boolean ongoingSale;

    /**
     * Constructor.
     * Instantiates sale identifier by means of adding 1 to the the sale
     * identifier of the sale prior to this sale.
     * 
     * @param latestSaleIdentifier  sale identifier of the previous sale
     */
    public Sale(int latestSaleIdentifier) {
        itemsInSale = new LinkedList<>();
        setDateAndTimeOfSale();
        saleIdentifier = latestSaleIdentifier + 1;
        runningTotal = 0;
        totalPrice = 0;
        totalVAT = 0;
        ongoingSale = true;
    }
    
    /**
     * Adds an item to the collection of the sale. This may be done in two ways:
     * 1.   If the item has been scanned previously, increment its quantity by
     *      one instead of adding a new entry.
     * 2.   If the item has not been scanned previously, add a new entry.
     * 
     * @param containerOfItemInfo   contains info about the item to be added
     * @return                      a summary of the sale used by the UI
     */
    public SaleDTO addItemToSale(ItemDTO containerOfItemInfo) {
        Item item = new Item(containerOfItemInfo);
        int itemIdentifier = item.getItemIdentifier();
        boolean itemExistsInSale = checkItemOccurrenceInSale(itemIdentifier);

        if(itemExistsInSale) {
            incrementLatestScannedItemQuantityByOne();
        } else {
            addNewItemToSale(item);
        }
        
        updateRunningTotal();
        return summarizeSale();
    }

    /**
     * Finds all applicable discounts and applies them to the ongoing sale.
     * 
     * @param customerIsMember  whether the customer is registered as a member or not
     * @param discounts     all item discounts that may be applicable
     * @return                  a summary of the sale used by the UI
     */
    public SaleDTO checkForApplicableDiscountsAndApply(boolean customerIsMember, 
                                                LinkedList<LinkedList<? extends DiscountDTO>> discounts) {
        
        LinkedList<DiscountAlgorithmStrategy> discountAlgorithms =
                new DiscountAlgorithmFactory().getAllDiscountAlgorithms();
        
        int i = 0;
        for(DiscountAlgorithmStrategy discountAlgorithm : discountAlgorithms) {
            SaleDTO saleInfoForDeterminingApplicableDiscounts = new SaleDTO(this);
            applyDiscounts(
                discountAlgorithm.
                    findAndCalculateApplicableDiscounts(
                                    customerIsMember,
                                    discounts.get(i++),
                                    saleInfoForDeterminingApplicableDiscounts));
        }
        
        return summarizeSale();
    }
    
    /**
     * Ends ongoing sale by finalizing the total price and total tax amount,
     * and then marking the sale as no longer ongoing.
     * 
     * @return  a summary of the sale used by the UI
     */
    public SaleDTO endSale() {
        finalizeTotalPrice();
        finalizeTotalVAT();
        setOngoingSaleStatus(false);
        
        return summarizeSale();
    }
    
    /**
     * Get the date and time of the sale.
     * 
     * @return  the date and time of the sale
     */
    public String getDateAndTimeOfSale() {
        return saleDateAndTime;
    }
    
    /**
     * Get payment for sale.
     * 
     * @return  payment
     */
    public Payment getPayment() {
        return payment;
    }
    
    /**
     * Get the collection of items that have been registered in the sale.
     * 
     * @return  the collection of items
     */
    public LinkedList<Item> getItemsInSale() {
        return itemsInSale;
    }
    
    /**
     * Get the identifier for distinguisting the sale from other sales.
     * 
     * @return  the sale identifier
     */
    public int getSaleIdentifier() {
        return saleIdentifier;
    }
    
    /**
     * Get the running total of the sale.
     * 
     * @return  the running total of the sale
     */
    public double getRunningTotal() {
        return runningTotal;
    }
    
    /**
     * Get the total price of the sale.
     * 
     * @return  the total price of the sale
     */
    public double getTotalPrice() {
        return totalPrice;
    }
    
    /**
     * Get the total tax amount of the sale.
     * 
     * @return  the total tax amount of the sale
     */
    public double getTotalVAT() {
        return totalVAT;
    }
    
    /**
     * Get the status of whether the sale is ongoing or not.
     * 
     * @return  the status of whether the sale is ongoing or not
     */
    public boolean isOngoing() {
        return ongoingSale;
    }

    /**
     * Performs all operations related to processing the payment:
     * 1. Return change to customer
     * 2. Add payment to the cash register
     * 
     * @param cash      the cash amount received from the customer
     * @param cashReg   a reference to the cash register for accessing relevant methods
     * @return          the completed sale
     */
    public SaleDTO processPayment(double cash, CashRegister cashReg) {
        payment = new Payment(cash);
        double diffBetweenPriceAndPayment = calculateDiffBetweenPriceAndPayment(payment);
        SaleDTO completedSale = cashReg.returnChangeAndAddPayment(diffBetweenPriceAndPayment, payment, this);
        
        return completedSale;
    }

    /**
     * Sets the quantity of the latest scanned item.
     * 
     * @param itemQuantity  the new quantity of the item
     * @return              a summary of the sale used by the UI
     */
    public SaleDTO setItemQuantityForLatestScannedItem(int itemQuantity) {
        this.getItemsInSale().getLast().setItemQuantityInSale(itemQuantity);
        this.updateRunningTotal();
        
        return summarizeSale();
    }
    
    /**
     * Marks the sale as ongoing.
     * 
     * @param ongoingStatus boolean for whether the sale is ongoing or not
     */
    public void setOngoingSaleStatus(boolean ongoingStatus) {
        ongoingSale = ongoingStatus;
    }
    
    private void addNewItemToSale(Item item) {
        itemsInSale.add(item);
    }
    
    private void applyDiscounts(double newTotalPriceAfterDiscount) {
        totalPrice = newTotalPriceAfterDiscount;
        runningTotal = newTotalPriceAfterDiscount;
    }

    private double calculateDiffBetweenPriceAndPayment(Payment payment) {
        double totalPriceOfSale = getTotalPrice();
        double cashAmount = payment.getCashAmount();
        double diffBetweenPriceAndPayment = cashAmount - totalPriceOfSale;
        
        return diffBetweenPriceAndPayment;
    }

    private boolean checkItemOccurrenceInSale(int itemIdentifier) {
        boolean itemExistsInSale = false;

        for(Item item: itemsInSale) {
            if(item.getItemIdentifier() == itemIdentifier) {
                itemExistsInSale = true;
            }
        }
        
        return itemExistsInSale;
    }

    private void finalizeTotalPrice() {
        totalPrice = runningTotal;
    }
    
    private void finalizeTotalVAT() {
        for (Item item: itemsInSale) {
            totalVAT += (item.getItemQuantityInSale() * item.getItemPrice()
                                                      * item.getItemVATRate());
        }
    }
    
    private void incrementLatestScannedItemQuantityByOne() {
        int amountToIncrementBy = 1;

        Item latestScannedItem = itemsInSale.getLast();
        int currentQuantityInSale = latestScannedItem.getItemQuantityInSale();

        latestScannedItem.setItemQuantityInSale(currentQuantityInSale + amountToIncrementBy);
    }

    private void setDateAndTimeOfSale() {
        SimpleDateFormat formatter = new SimpleDateFormat ("dd/MM/yyyy HH:mm:ss");
        Date dateAndTime = new Date();
        saleDateAndTime = formatter.format(dateAndTime);
    }
    
    private SaleDTO summarizeSale() {
        return new SaleDTO(this);
    }

    private void updateRunningTotal() {
        runningTotal = 0;
        for(Item item: itemsInSale) {
            double itemPrice = item.getItemPrice();
            double itemQuantityInSale = item.getItemQuantityInSale();
            runningTotal += itemPrice * itemQuantityInSale;
        }
    }
}
