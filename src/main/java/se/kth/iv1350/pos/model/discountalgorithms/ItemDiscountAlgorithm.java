/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.pos.model.discountalgorithms;

import java.util.LinkedList;
import se.kth.iv1350.pos.dto.ItemDTO;
import se.kth.iv1350.pos.dto.ItemDiscountDTO;
import se.kth.iv1350.pos.dto.SaleDTO;
import se.kth.iv1350.pos.dto.DiscountDTO;

/**
 * Algorithm for finding and calculating applicable item discounts.
 * Is adhering to the Strategy pattern.
 */
public class ItemDiscountAlgorithm implements DiscountAlgorithmStrategy {
    ItemDiscountAlgorithm() {}
    
    /**
      * Checks whether there are item discounts which identifier matches
      * any of the identifiers of the items in sale or not, and extracts them.
      * 
      * @param <T>
      * @param customerIsMember     whether the customer is registered as a member or not
      * @param discounts            the discounts that may be applicable to the item
      * @param saleInfoForDeterminingApplicableDiscounts sale info for determining applicable discounts
      * @return                     the extracted discounts
      */
    @Override
    public <T extends DiscountDTO> double findAndCalculateApplicableDiscounts(
                                                        boolean customerIsMember,
                                                        LinkedList<T> discounts,
                                                        SaleDTO  saleInfoForDeterminingApplicableDiscounts) {
        
        LinkedList<ItemDiscountDTO> itemDiscounts =
                (LinkedList<ItemDiscountDTO>)(LinkedList<?>) discounts;
        
        LinkedList<ItemDTO> itemsInThisSale =
                saleInfoForDeterminingApplicableDiscounts.getItemsInSale();
        
        LinkedList<ItemDiscountDTO> applicableDiscountsForItemsInSale =
                findAndExtractApplicableDiscounts(customerIsMember,
                                        itemsInThisSale,
                                        itemDiscounts);
        
        double totalPrice = saleInfoForDeterminingApplicableDiscounts.getTotalPrice();
        
        double newPriceAfterItemDiscount =
                calculateNewTotalPriceAfterDiscount(totalPrice,
                                                   applicableDiscountsForItemsInSale);

        return newPriceAfterItemDiscount;
    }
    
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
    
    private double calculateNewTotalPriceAfterDiscount(double totalPrice,
                                                  LinkedList<ItemDiscountDTO> applicableItemDiscounts) {
        double totalItemDiscountAmount = 0;
        
        for(ItemDiscountDTO itemDiscount: applicableItemDiscounts) {
            double itemDiscountAmount = itemDiscount.getItemDiscountAmount();
            double itemQuantityDiscountAmount = itemDiscount.getItemQuantityDiscountAmount();
            double membersOnlyItemDiscountAmount = itemDiscount.getMembersOnlyItemDiscountAmount();
            
            totalItemDiscountAmount +=
                    itemDiscountAmount
                    + itemQuantityDiscountAmount
                    + membersOnlyItemDiscountAmount;
        }
        
        return totalPrice - totalItemDiscountAmount;
    }
    
    private LinkedList<ItemDiscountDTO> findAndExtractApplicableDiscounts(boolean customerIsMember,
                                                                 LinkedList<ItemDTO> itemsInThisSale,
                                                                 LinkedList<ItemDiscountDTO> itemDiscounts) {
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
}
