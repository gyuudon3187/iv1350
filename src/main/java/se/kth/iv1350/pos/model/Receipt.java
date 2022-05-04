package se.kth.iv1350.pos.model;

import java.util.LinkedList;
import se.kth.iv1350.pos.dto.ItemDTO;
import se.kth.iv1350.pos.dto.SaleDTO;

/**
 * Class for receipts.
 */
public class Receipt {
    
    private LinkedList<ItemDTO> itemsInSale;
    
    private Payment payment;

    private String saleDateAndTime;

    private double runningTotal;

    private double totalPrice;

    private double totalVAT;

    /**
     * Constructor.
     * 
     * @param completedSale     sale information used for instantiating attributes
     */
    public Receipt(SaleDTO completedSale) {
        itemsInSale = completedSale.getItemsInSale();
        payment = completedSale.getPayment();
        saleDateAndTime = completedSale.getDateAndTimeOfSale();
        runningTotal = completedSale.getRunningTotal();
        totalPrice = completedSale.getTotalPrice();
        totalVAT = completedSale.getTotalVAT();
    }
    
    /**
     * Get the collection of items that have been registered in the sale.
     * 
     * @return  the collection of items
     */
    public LinkedList<ItemDTO> getItemsInSale() {
        return this.itemsInSale;
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
     * Get the running total of the sale.
     * 
     * @return  running total
     */
    public double getRunningTotal() {
        return this.runningTotal;
    }
    
    /**
     * Get the total price of the sale.
     * 
     * @return  total price
     */
    public double getTotalPrice() {
        return this.totalPrice;
    }
    
    /**
     * Get the total tax amount of the sale.
     * 
     * @return  total VAT
     */
    public double getTotalVAT() {
        return this.totalVAT;
    }
}
