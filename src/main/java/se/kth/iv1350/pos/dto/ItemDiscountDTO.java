package se.kth.iv1350.pos.dto;

import se.kth.iv1350.pos.model.ItemDiscount;

/**
 * Class for containing and transferring immutable information about discounts
 * pertaining to a specific item.
 */
public class ItemDiscountDTO {
    
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
	public ItemDiscountDTO(int itemIdentifier,
                                int quantityForItemQuantityDiscountToBeApplicable,
                                double itemDiscountAmount,
                                double itemQuantityDiscountAmount,
                                double membersOnlyItemDiscountAmount) {
            this.itemIdentifier = itemIdentifier;
            this.quantityForItemQuantityDiscountToBeApplicable = quantityForItemQuantityDiscountToBeApplicable;
            this.itemDiscountAmount = itemDiscountAmount;
            this.itemQuantityDiscountAmount = itemQuantityDiscountAmount;
            this.membersOnlyItemDiscountAmount = membersOnlyItemDiscountAmount;
	}
        
        /**
         * Constructor which instantiates its attributes by copying information
         * from an existing item discount object.
         * 
         * @param itemDiscount 
         */
        public ItemDiscountDTO(ItemDiscount itemDiscount) {
            this.itemIdentifier = itemDiscount.getItemIdentifier();
            this.quantityForItemQuantityDiscountToBeApplicable = itemDiscount.getQuantityForItemQuantityDiscountToBeApplicable();
            this.itemDiscountAmount = itemDiscount.getItemDiscountAmount();
            this.itemQuantityDiscountAmount = itemDiscount.getItemQuantityDiscountAmount();
            this.membersOnlyItemDiscountAmount = itemDiscount.getMembersOnlyItemDiscountAmount();
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
         * 
         * @return  the discount amount that applies by buying a specific item.
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
