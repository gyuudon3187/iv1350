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
        
        /**
         * Default constructor. Used as a helper object for accessing certain methods.
         */
        public SaleDiscount() {
        }

        /**
         * Constructor.
         * 
         * @param amountForTotalCostDiscountToBeApplicable
         * @param totalCostDiscountAmount 
         */
	public SaleDiscount(double amountForTotalCostDiscountToBeApplicable,
                            double totalCostDiscountAmount) {
		this.amountForTotalCostDiscountToBeApplicable = amountForTotalCostDiscountToBeApplicable;
                this.totalCostDiscountAmount = totalCostDiscountAmount;
	}
        
        /**
         * Checks for discounts that may be applicable to the sale
         * 
         * @param saleDiscounts     the discounts that may be applicable to the sale
         * @param saleInfoForDeterminingApplicableDiscounts sale info for determining applicable discounts
         * @return                  the applicable discount, if any
         */
        double checkForApplicableSaleDiscount(LinkedList<SaleDiscountDTO> saleDiscounts,
                                                SaleDTO saleInfoForDeterminingApplicableDiscounts) {
            double applicableSaleDiscount = 0;
            for(SaleDiscountDTO saleDiscount: saleDiscounts) {

                double totalPrice = saleInfoForDeterminingApplicableDiscounts.getTotalPrice();
                double amountForDiscountToBeApplicable = saleDiscount.getAmountForTotalCostDiscountToBeApplicable();

                if(totalPrice > amountForDiscountToBeApplicable) {
                    applicableSaleDiscount = saleDiscount.getTotalCostDiscountAmount();
                }      
            }

            return applicableSaleDiscount;
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
