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
         * Default constructor. Used as a helper object for accessing certain methods.
         */
        public ItemDiscount() {
        }
        
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
         * Extracts the discounts that can be applied to the item passed as an argument.
         * 
         * @param customerIsMember  whether the customer is registered as a member or not
         * @param item              the item to which discounts may be applicable
         * @param itemDiscount      the discounts that may be applicable to the item
         * @return                  the applicable item discounts, if any
         */
        private ItemDiscountDTO extractApplicableItemDiscounts(boolean customerIsMember,
                                            ItemDTO item,
                                            ItemDiscountDTO itemDiscount) {
            double discountAmount = itemDiscount.getItemDiscountAmount();
            double quantityDiscountAmount = 0;
            double membersOnlyDiscountAmount = 0;

            int itemQuantityInSale = item.getItemQuantityInSale();
            int quantityForQuantityDiscountToBeApplicable =
                    itemDiscount.getQuantityForItemQuantityDiscountToBeApplicable();
            
            if(itemQuantityInSale > quantityForQuantityDiscountToBeApplicable) {
                quantityDiscountAmount = itemDiscount.getItemQuantityDiscountAmount();
            }

            if(customerIsMember) {
                membersOnlyDiscountAmount = itemDiscount.getMembersOnlyItemDiscountAmount();
            }

            int discountIdentifier = item.getItemIdentifier();
            ItemDiscountDTO applicableDiscountsForSingleItem =
                    new ItemDiscountDTO(discountIdentifier,
                            quantityForQuantityDiscountToBeApplicable,
                            discountAmount,
                            quantityDiscountAmount,
                            membersOnlyDiscountAmount);
            
            return applicableDiscountsForSingleItem;
        }
        
        /**
         * Checks whether there are item discounts which identifier matches
         * any of the identifiers of the items in sale or not, and extracts them.
         * 
         * @param customerIsMember  whether the customer is registered as a member or not
         * @param itemDiscounts     the discounts that may be applicable to the item
         * @param saleInfoForDeterminingApplicableDiscounts sale info for determining applicable discounts
         * @return                  the extracted discounts
         */
        LinkedList<ItemDiscountDTO> checkForApplicableItemDiscounts(boolean customerIsMember,
                                                                    LinkedList<ItemDiscountDTO> itemDiscounts,
                                                                    SaleDTO  saleInfoForDeterminingApplicableDiscounts) {
            LinkedList<ItemDTO> itemsInThisSale = saleInfoForDeterminingApplicableDiscounts.getItemsInSale();
            LinkedList<ItemDiscountDTO> applicableDiscountsForItemsInSale = new LinkedList<>();

            for(ItemDTO item: itemsInThisSale) {
                for(ItemDiscountDTO itemDiscount: itemDiscounts) {
                    
                    int itemInSaleIdentifier = item.getItemIdentifier();
                    int itemDiscountIdentifier = itemDiscount.getItemIdentifier();

                    if(itemInSaleIdentifier == itemDiscountIdentifier) {
                        ItemDiscountDTO applicableDiscountsForSingleItem =
                                this.extractApplicableItemDiscounts(customerIsMember,
                                                                item,
                                                                itemDiscount);
                        applicableDiscountsForItemsInSale.add(applicableDiscountsForSingleItem);
                    }
                }
            }
            
            return applicableDiscountsForItemsInSale;
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
