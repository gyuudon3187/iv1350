package se.kth.iv1350.pos.model;

import java.util.LinkedList;
import java.text.SimpleDateFormat;
import java.util.Date;
import se.kth.iv1350.pos.dto.ItemDTO;
import se.kth.iv1350.pos.dto.ItemDiscountDTO;
import se.kth.iv1350.pos.dto.SaleDTO;
import se.kth.iv1350.pos.dto.SaleDiscountDTO;

/**
 * Class containing all information relevant to an ongoing, single sale.
 */
public class Sale {
    private LinkedList<Item> itemsInSale;
    
    private Payment payment;

    private String saleDateAndTime;
    
    private int saleIdentifier;

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
     * Adds a new entry of an item to the collection of items in sale.
     * 
     * @param item  the item to be added to the collection
     */
    private void addNewItemToSale(Item item) {
        itemsInSale.add(item);
    }
    
    /**
     * Subtracts the discounts that pertain to a specific item from the
     * total price of the sale.
     * 
     * @param applicableItemDiscounts   a collection of discounts of a specific item
     */
    private void applyItemDiscounts(LinkedList<ItemDiscountDTO> applicableItemDiscounts) {
        for(ItemDiscountDTO itemDiscount: applicableItemDiscounts) {
            double itemDiscountAmount = itemDiscount.getItemDiscountAmount();
            double itemQuantityDiscountAmount = itemDiscount.getItemQuantityDiscountAmount();
            double membersOnlyItemDiscountAmount = itemDiscount.getMembersOnlyItemDiscountAmount();
            
            double totalItemDiscount =
                    itemDiscountAmount
                    + itemQuantityDiscountAmount
                    + membersOnlyItemDiscountAmount;
            
            this.totalPrice -= totalItemDiscount;
        }
    }
    
    /**
     * Substracts the discount that pertain to the sale as a whole from
     * the total price of the sale.
     * 
     * @param applicableSaleDiscount    the discount that is applicable to the sale
     */
    private void applySaleDiscount(double applicableSaleDiscount) {
        this.totalPrice -= applicableSaleDiscount;
    }

    private double calculateDiffBetweenPriceAndPayment(Payment payment) {
        double totalPriceOfSale = this.getTotalPrice();
        double cashAmount = payment.getCashAmount();
        double diffBetweenPriceAndPayment = cashAmount - totalPriceOfSale;
        
        return diffBetweenPriceAndPayment;
    }

    /**
     * Checks whether an item exists in the collection of items registered
     * in the sale or not.
     * 
     * @param itemIdentifier    the identifier of the item to be checked
     * @return                  the occurrence or absence of the item
     */
    private boolean checkItemOccurrenceInSale(int itemIdentifier) {
        boolean itemExistsInSale = false;

        for(Item item: itemsInSale) {
            if(item.getItemIdentifier() == itemIdentifier) {
                itemExistsInSale = true;
            }
        }
        
        return itemExistsInSale;
    }

    /**
     * Sets the total price of the entire sale to the running total.
     */
    private void finalizeTotalPrice() {
        totalPrice = runningTotal;
    }
    
    /**
     * Sets the total tax amount to the aggregate of item prices multiplied
     * by each respective item's tax rate.
     */
    private void finalizeTotalVAT() {
        for (Item item: itemsInSale) {
            totalVAT += item.getItemPrice() * (double) item.getItemVATRate();
        }
    }
    
    /**
     * Increments the quantity of the latest scanned item by one.
     */
    private void incrementLatestScannedItemQuantityByOne() {
        int amountToIncrementBy = 1;

        Item latestScannedItem = itemsInSale.getLast();
        int currentQuantityInSale = latestScannedItem.getItemQuantityInSale();

        latestScannedItem.setItemQuantityInSale(currentQuantityInSale + amountToIncrementBy);
    }

    /**
     * Sets the date and time of the sale to the current date and time.
     */
    private void setDateAndTimeOfSale() {
        SimpleDateFormat formatter = new SimpleDateFormat ("dd/MM/yyyy HH:mm:ss");
        Date dateAndTime = new Date();
        saleDateAndTime = formatter.format(dateAndTime);
    }

    /**
     * Increments the running total by the price of the latest scanned item's price.
     */
    private void updateRunningTotal() {
        runningTotal = 0;
        for(Item item: this.itemsInSale) {
            double itemPrice = item.getItemPrice();
            double itemQuantityInSale = item.getItemQuantityInSale();
            runningTotal += itemPrice * itemQuantityInSale;
        }
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
        boolean itemExistsInSale = this.checkItemOccurrenceInSale(itemIdentifier);

        if(itemExistsInSale) {
            this.incrementLatestScannedItemQuantityByOne();
        } else {
            this.addNewItemToSale(item);
        }

        this.updateRunningTotal();
        SaleDTO summaryOfCurrentSale = new SaleDTO(this);
        return summaryOfCurrentSale;
    }

    /**
     * Finds all applicable discounts and applies them to the ongoing sale.
     * 
     * @param customerIsMember  whether the customer is registered as a member or not
     * @param itemDiscounts     all item discounts that may match with items in sale
     * @param saleDiscounts     all sale discounts that may match with the sale
     * @return                  a summary of the sale used by the UI
     */
    public SaleDTO checkForApplicableDiscountsAndApply(boolean customerIsMember, 
                                                LinkedList<ItemDiscountDTO> itemDiscounts,
                                                LinkedList<SaleDiscountDTO> saleDiscounts) {
        
        ItemDiscount itemDiscountHelper = new ItemDiscount();
        SaleDiscount saleDiscountHelper = new SaleDiscount();
        SaleDTO saleInfoForDeterminingApplicableDiscounts = new SaleDTO(this);
        
        LinkedList<ItemDiscountDTO> applicableItemDiscounts =
                itemDiscountHelper.checkForApplicableItemDiscounts(customerIsMember,
                                                                itemDiscounts,
                                                                saleInfoForDeterminingApplicableDiscounts);
        double applicableSaleDiscount =
                saleDiscountHelper.checkForApplicableSaleDiscount(saleDiscounts,
                                                                saleInfoForDeterminingApplicableDiscounts);
        
        this.applyItemDiscounts(applicableItemDiscounts);
        this.applySaleDiscount(applicableSaleDiscount);
        
        SaleDTO summaryOfCurrentSale = new SaleDTO(this);
        return summaryOfCurrentSale;
    }
    
    /**
     * Ends ongoing sale by finalizing the total price and total tax amount,
     * and then marking the sale as no longer ongoing.
     * 
     * @return  a summary of the sale used by the UI
     */
    public SaleDTO endSale() {
        this.finalizeTotalPrice();
        this.finalizeTotalVAT();
        this.setOngoingSaleStatus(false);
        SaleDTO summaryOfCurrentSale = new SaleDTO(this);

        return summaryOfCurrentSale;
    }
    
    /**
     * Get the date and time of the sale.
     * 
     * @return  the date and time of the sale
     */
    public String getDateAndTimeOfSale() {
        return this.saleDateAndTime;
    }
    
    /**
     * Get payment for sale.
     * 
     * @return  payment
     */
    public Payment getPayment() {
        return this.payment;
    }
    
    /**
     * Get the collection of items that have been registered in the sale.
     * 
     * @return  the collection of items
     */
    public LinkedList<Item> getItemsInSale() {
        return this.itemsInSale;
    }
    
    /**
     * Get the identifier for distinguisting the sale from other sales.
     * 
     * @return  the sale identifier
     */
    public int getSaleIdentifier() {
        return this.saleIdentifier;
    }
    
    /**
     * Get the running total of the sale.
     * 
     * @return  the running total of the sale
     */
    public double getRunningTotal() {
        return this.runningTotal;
    }
    
    /**
     * Get the total price of the sale.
     * 
     * @return  the total price of the sale
     */
    public double getTotalPrice() {
        return this.totalPrice;
    }
    
    /**
     * Get the total tax amount of the sale.
     * 
     * @return  the total tax amount of the sale
     */
    public double getTotalVAT() {
        return this.totalVAT;
    }
    
    /**
     * Get the status of whether the sale is ongoing or not.
     * 
     * @return  the status of whether the sale is ongoing or not
     */
    public boolean isOngoing() {
        return this.ongoingSale;
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
        double diffBetweenPriceAndPayment = this.calculateDiffBetweenPriceAndPayment(payment);
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
        SaleDTO summaryOfCurrentSale = new SaleDTO(this);

        return summaryOfCurrentSale;
    }
    
    /**
     * Marks the sale as ongoing.
     * 
     * @param ongoingStatus boolean for whether the sale is ongoing or not
     */
    public void setOngoingSaleStatus(boolean ongoingStatus) {
        this.ongoingSale = ongoingStatus;
    }
}
