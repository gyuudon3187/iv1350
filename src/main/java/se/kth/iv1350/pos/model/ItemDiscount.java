package se.kth.iv1350.pos.model;

import java.util.LinkedList;
import se.kth.iv1350.pos.dto.ItemDTO;
import se.kth.iv1350.pos.dto.SaleDTO;
import se.kth.iv1350.pos.dto.ItemDiscountDTO;

/**
 * Class containing the discounts that pertain to a specific item.
 */
public class ItemDiscount {

	private int itemIdentifier;

	private int quantityForItemQuantityDiscountToBeApplicable;

	private double itemDiscountAmount;

	private double itemQuantityDiscountAmount;

	private double membersOnlyItemDiscountAmount;
        
        /**
         * Constructor.
         * 
         * @param itemIdentifier
         * @param quantityForItemQuantityDiscountToBeApplicable
         * @param itemDiscountAmount
         * @param itemQuantityDiscountAmount
         * @param membersOnlyItemDiscountAmount 
         */
        public ItemDiscount(int itemIdentifier, int quantityForItemQuantityDiscountToBeApplicable,
                double itemDiscountAmount, double itemQuantityDiscountAmount, double membersOnlyItemDiscountAmount) {
            this.itemIdentifier = itemIdentifier;
            this.quantityForItemQuantityDiscountToBeApplicable = quantityForItemQuantityDiscountToBeApplicable;
            this.itemDiscountAmount = itemDiscountAmount;
            this.itemQuantityDiscountAmount = itemQuantityDiscountAmount;
            this.membersOnlyItemDiscountAmount = membersOnlyItemDiscountAmount;
        }

        /**
         * Get the identifier of the item to which the discounts apply.
         * 
         * @return  the item identifier
         */
	public int getItemIdentifier() {
            return this.itemIdentifier;
	}

        /**
         * Get the discount amount that applies by buying a specific item.
         * @return 
         */
	public double getItemDiscountAmount() {
            return this.itemDiscountAmount;
	}

        /**
         * Get the discount amount that applies by buying a certain quantity
         * of a specific item.
         * 
         * @return  the discount amount that applies by buying a certain quantity
         */
	public double getItemQuantityDiscountAmount() {
            return this.itemQuantityDiscountAmount;
	}

        /**
         * Get the discount amount that applies only for members for a specific item.
         * 
         * @return  the discount amount that applies only for members for a specific item
         */
	public double getMembersOnlyItemDiscountAmount() {
            return this.membersOnlyItemDiscountAmount;
	}
        
        /**
         * Get the quantity needed for the discount, that applies when a
         * certain quantity of an item is bought by the customer, to be applicable.
         * 
         * @return  the quantity needed for the above described discount to be applied
         */
        public int getQuantityForItemQuantityDiscountToBeApplicable() {
            return this.quantityForItemQuantityDiscountToBeApplicable;
        }

}
