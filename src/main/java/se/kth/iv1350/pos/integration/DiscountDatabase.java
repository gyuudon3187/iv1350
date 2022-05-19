package se.kth.iv1350.pos.integration;

import java.util.LinkedList;
import java.util.Arrays;
import se.kth.iv1350.pos.dto.ItemDiscountDTO;
import se.kth.iv1350.pos.dto.SaleDiscountDTO;

/**
 * Database containing information about all item and sale discounts.
 * Is instantiated according to the singleton pattern.
 */
public class DiscountDatabase {
        private static final DiscountDatabase discdb = new DiscountDatabase();

	private LinkedList<ItemDiscountDTO> itemDiscounts;

	private LinkedList<SaleDiscountDTO> saleDiscounts;

        /**
         * Constructor. Instantiates all the discounts.
         */
	private DiscountDatabase() {
            
            int yoghurtIdentifier = 452283101;
            int yoghurtQuantityForItemQuantityDiscountToBeApplicable = 2;
            double yoghurtItemDiscountAmount = 0;
            double yoghurtItemQuantityDiscountAmount = 5;
            double yoghurtMembersOnlyItemDiscountAmount = 8;
            ItemDiscountDTO yoghurtDiscount = 
                    new ItemDiscountDTO(yoghurtIdentifier,
                                        yoghurtQuantityForItemQuantityDiscountToBeApplicable,
                                        yoghurtItemDiscountAmount,
                                        yoghurtItemQuantityDiscountAmount,
                                        yoghurtMembersOnlyItemDiscountAmount);
            
            int tobaccoIdentifier = 452283103;
            int tobaccoQuantityForItemQuantityDiscountToBeApplicable = 0;
            double tobaccoItemDiscountAmount = 12;
            double tobaccoItemQuantityDiscountAmount = 0;
            double tobaccoMembersOnlyItemDiscountAmount = 0;
            ItemDiscountDTO tobaccoDiscount =
                    new ItemDiscountDTO (tobaccoIdentifier,
                                        tobaccoQuantityForItemQuantityDiscountToBeApplicable,
                                        tobaccoItemDiscountAmount,
                                        tobaccoItemQuantityDiscountAmount,
                                        tobaccoMembersOnlyItemDiscountAmount);
            
            itemDiscounts = new LinkedList<>(Arrays.asList(yoghurtDiscount, tobaccoDiscount));
            
            double lowAmountForTotalCostDiscountToBeApplicable = 100;
            double lowTotalCostDiscountAmount = 5;
            double lowMembersOnlyDiscountRate = 0.1;
            SaleDiscountDTO lowTotalCostDiscount =
                    new SaleDiscountDTO(lowAmountForTotalCostDiscountToBeApplicable,
                                        lowTotalCostDiscountAmount,
                                        lowMembersOnlyDiscountRate);
            double mediumAmountForTotalCostDiscountToBeApplicable = 200;
            double mediumTotalCostDiscountAmount = 10;
            double mediumMembersOnlyDiscountRate = 0.2;
            SaleDiscountDTO mediumTotalCostDiscount =
                    new SaleDiscountDTO(mediumAmountForTotalCostDiscountToBeApplicable,
                                        mediumTotalCostDiscountAmount,
                                        mediumMembersOnlyDiscountRate);
            double highAmountForTotalCostDiscountToBeApplicable = 300;
            double highTotalCostDiscountAmount = 20;
            double highMembersOnlyDiscountRate = 0.3;
            SaleDiscountDTO highTotalCostDiscount =
                    new SaleDiscountDTO(highAmountForTotalCostDiscountToBeApplicable,
                                        highTotalCostDiscountAmount,
                                        highMembersOnlyDiscountRate);
            
            saleDiscounts = new LinkedList<>(Arrays.asList(
                    lowTotalCostDiscount, mediumTotalCostDiscount, highTotalCostDiscount));
	}
        
        public static DiscountDatabase getDiscountDatabase() {
            return discdb;
        }

        /**
         * Get all item discounts.
         * 
         * @return  collection of all item discounts
         */
	public LinkedList<ItemDiscountDTO> getItemDiscounts() {
		return itemDiscounts;
	}

        /**
         * Get all sale discounts.
         * 
         * @return  collection of all sale discounts
         */
	public LinkedList<SaleDiscountDTO> getSaleDiscounts() {
		return saleDiscounts;
	}

}
