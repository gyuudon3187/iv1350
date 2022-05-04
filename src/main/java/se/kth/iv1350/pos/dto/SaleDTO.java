package se.kth.iv1350.pos.dto;

import java.util.LinkedList;
import se.kth.iv1350.pos.model.Item;
import se.kth.iv1350.pos.model.Payment;
import se.kth.iv1350.pos.model.Sale;

/**
 * Class for containing and transferring information about a single sale.
 */
public class SaleDTO {

    private LinkedList<ItemDTO> itemsInSale;
    
    private Payment payment;

    private String saleDateAndTime;
    
    private int saleIdentifier;

    private double runningTotal;

    private double totalPrice;

    private double totalVAT;
    
    private boolean ongoingSale;

    /**
     * Constructor. 
     * @param currentSale 
     */
    public SaleDTO(Sale currentSale) {
        LinkedList<Item> itemsToCopy = currentSale.getItemsInSale();
        LinkedList<ItemDTO> itemsToInstantiate = new LinkedList<>();
        
        for(Item item: itemsToCopy) {
            ItemDTO itemToInstantiate = new ItemDTO(item);
            itemsToInstantiate.add(itemToInstantiate);
        }
        
        itemsInSale = itemsToInstantiate;
        runningTotal = currentSale.getRunningTotal();
        saleDateAndTime = currentSale.getDateAndTimeOfSale();
        saleIdentifier = currentSale.getSaleIdentifier();
        payment = currentSale.getPayment();
        totalPrice = currentSale.getTotalPrice();
        totalVAT = currentSale.getTotalVAT();
        ongoingSale = currentSale.isOngoing();
    }

    /**
     * Get the collection of items in the sale.
     * 
     * @return  items in the sale
     */
    public LinkedList<ItemDTO> getItemsInSale() {
        return itemsInSale;
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
     * Get the date and time of when the sale started.
     * 
     * @return  the date and time of the sale
     */
    public String getDateAndTimeOfSale() {
        return saleDateAndTime;
    }
    
    /**
     * Get the running total of the sale.
     * 
     * @return  the running total of the sale
     */
    public double getRunningTotal() {
        return runningTotal;
    }
    
    public int getSaleIdentifier() {
        return this.saleIdentifier;
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
     * Get the total tax amount.
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
        return this.ongoingSale;
    }

}
