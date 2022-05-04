package se.kth.iv1350.pos.model;

/**
 * Class for payments made by either cash or credit or both.
 */
public class Payment {

    private double cashAmount;

    private double paidAmount;

    private double changeAmount;

    /**
     * Constructor. Only used for payments made by cash.
     * 
     * @param cashAmount    the amount of cash received as payment
     */
    public Payment(double cashAmount) {
        this.cashAmount = cashAmount;
    }

    /**
     * Get the amount of cash made as payment.
     * 
     * @return  amount of cash made as payment
     */
    double getCashAmount() {
        return cashAmount;
    }
    
    /**
     * Get the amount of change that was given to the customer.
     * 
     * @return  the amount of change given to the customer
     */
    public double getChangeAmount() {
        return changeAmount;
    }
    
    /**
     * Get the amount of cash paid by the customer.
     * 
     * @return  the amount of cash paid by the customer
     */
    public double getPaidAmount() {
        return paidAmount;
    }
    
    /**
     * Set the amount paid by customer for the current sale.
     * 
     * @param paidAmount    amount paid by customer for sale
     */
    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }
        
    /**
     * Sets the change, that is to be returned to the customer, to the
     * amount that corresponds to the difference between the price and
     * the received payment.
     * 
     * @param diffBetweenPriceAndPayment    the difference between the total price and the payment
     */
    void setChange(double diffBetweenPriceAndPayment) {
        changeAmount = diffBetweenPriceAndPayment;
    }
    
    
}
