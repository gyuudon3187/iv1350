/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.pos.dto;

import se.kth.iv1350.pos.model.SaleDiscount;

/**
 * Class for containing and transferring immutable information about discounts
 * that apply for a sale as a whole.
 */
public class SaleDiscountDTO {
    
    private double amountForTotalCostDiscountToBeApplicable;

    private double totalCostDiscountAmount;
    
    /**
     * Constructor.
     * 
     * @param amountForTotalCostDiscountToBeApplicable
     * @param totalCostDiscountAmount 
     */
    public SaleDiscountDTO(double amountForTotalCostDiscountToBeApplicable,
                            double totalCostDiscountAmount) {
        this.amountForTotalCostDiscountToBeApplicable =
                amountForTotalCostDiscountToBeApplicable;
        this.totalCostDiscountAmount = totalCostDiscountAmount;
    }
    
    /**
     * Constructor which instantiates its attributes by copying information
     * from an existing sale discount object.
     * 
     * @param saleDiscountToBeCopied
     */
    public SaleDiscountDTO(SaleDiscount saleDiscountToBeCopied) {
        this.amountForTotalCostDiscountToBeApplicable =
                saleDiscountToBeCopied.getAmountForTotalCostDiscountToBeApplicable();
        this.totalCostDiscountAmount =
                saleDiscountToBeCopied.getTotalCostDiscountAmount();
    }
    
    /**
     * Get the amount the customer needs to pay in order to be elligible
     * for an discount applied only for purchases over a certain price amount.
     * 
     * @return  the amount to be paid in order to be elligible a the sale discount
     */
    public double getAmountForTotalCostDiscountToBeApplicable() {
        return this.amountForTotalCostDiscountToBeApplicable;
    }

    /**
     * Get the amount of the sale discount.
     * 
     * @return  the amount of the sale discount
     */
    public double getTotalCostDiscountAmount() {
        return this.totalCostDiscountAmount;
    }
}
