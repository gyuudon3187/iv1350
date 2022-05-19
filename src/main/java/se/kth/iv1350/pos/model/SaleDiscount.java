package se.kth.iv1350.pos.model;

import java.util.LinkedList;
import se.kth.iv1350.pos.dto.SaleDTO;
import se.kth.iv1350.pos.dto.SaleDiscountDTO;

/**
 * Class for discounts pertaining to the sale a whole.
 */
public class SaleDiscount {

	private double amountForTotalCostDiscountToBeApplicable;

	private double totalCostDiscountAmount;
        
        private double membersOnlyDiscountRate;

        /**
         * Constructor.
         * 
         * @param amountForTotalCostDiscountToBeApplicable
         * @param totalCostDiscountAmount 
         * @param membersOnlyDiscountRate 
         */
	public SaleDiscount(double amountForTotalCostDiscountToBeApplicable,
                            double totalCostDiscountAmount,
                            double membersOnlyDiscountRate) {
            this.amountForTotalCostDiscountToBeApplicable = amountForTotalCostDiscountToBeApplicable;
            this.totalCostDiscountAmount = totalCostDiscountAmount;
            this.membersOnlyDiscountRate = membersOnlyDiscountRate;
	}
        
        /**
         * Get the amount required for the discount (that is applicable only
         * when the payment exceeds a certain amount) to be applicable.
         * 
         * @return  the amount for the above described discount to be applicable
         */
        public double getAmountForTotalCostDiscountToBeApplicable() {
            return this.amountForTotalCostDiscountToBeApplicable;
        }

        /**
         * Get the amount of the discount (that is applicable only when the 
         * payment exceeds a certain amount).
         * 
         * @return  the amount of the above described discount
         */
	public double getTotalCostDiscountAmount() {
            return this.totalCostDiscountAmount;
	}
}
