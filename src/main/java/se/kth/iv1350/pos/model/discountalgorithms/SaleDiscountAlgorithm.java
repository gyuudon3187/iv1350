/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.pos.model.discountalgorithms;

import java.util.LinkedList;
import se.kth.iv1350.pos.dto.DiscountDTO;
import se.kth.iv1350.pos.dto.SaleDTO;
import se.kth.iv1350.pos.dto.SaleDiscountDTO;

/**
 * Algorithm for finding and calculating applicable sale discounts.
 * Is adhering to the Strategy pattern.
 */
public class SaleDiscountAlgorithm implements DiscountAlgorithmStrategy {
    SaleDiscountAlgorithm() {}
    
    private SaleDiscountDTO extractApplicableSaleDiscounts(boolean customerIsMember,
                                                            LinkedList<SaleDiscountDTO> saleDiscounts,
                                                            SaleDTO saleInfoForDeterminingApplicableDiscounts) {
        double totalCostDiscountAmount = 0;
        double membersOnlyDiscountRate = 0;
        
        for(SaleDiscountDTO saleDiscount: saleDiscounts) {
            double totalPrice = saleInfoForDeterminingApplicableDiscounts.getTotalPrice();
            double amountForDiscountToBeApplicable = saleDiscount.getAmountForTotalCostDiscountToBeApplicable();

            if(totalPrice > amountForDiscountToBeApplicable) {
                totalCostDiscountAmount = saleDiscount.getTotalCostDiscountAmount();
                
                if(customerIsMember) {
                    membersOnlyDiscountRate = saleDiscount.getMembersOnlyDiscountRate();
                }
            } 
        }
        
        return new SaleDiscountDTO(0, totalCostDiscountAmount, membersOnlyDiscountRate);
    }
    
    private double calculateNewTotalPriceAfterDiscount(SaleDiscountDTO applicableSaleDiscounts,
                                                        SaleDTO saleInfoForDeterminingTotalPrice) {
        double membersOnlyDiscountRate = applicableSaleDiscounts.getMembersOnlyDiscountRate();
        double totalCostDiscountAmount = applicableSaleDiscounts.getTotalCostDiscountAmount();
        double currentTotalPrice = saleInfoForDeterminingTotalPrice.getTotalPrice();
        
        double newTotalPrice = currentTotalPrice - (currentTotalPrice * membersOnlyDiscountRate);
        newTotalPrice -= totalCostDiscountAmount;
                
        return newTotalPrice;
    }
    
    @Override
    public <T extends DiscountDTO> double findAndCalculateApplicableDiscounts(
                                                        boolean customerIsMember,
                                                        LinkedList<T> discounts,
                                                        SaleDTO saleInfoForDeterminingApplicableDiscounts) {
        LinkedList<SaleDiscountDTO> saleDiscounts =
                (LinkedList<SaleDiscountDTO>)(LinkedList<?>) discounts;

        SaleDiscountDTO applicableSaleDiscounts = 
                extractApplicableSaleDiscounts(customerIsMember,
                                               saleDiscounts,
                                               saleInfoForDeterminingApplicableDiscounts);
        
        double newTotalPrice = calculateNewTotalPriceAfterDiscount(applicableSaleDiscounts,
                                                                   saleInfoForDeterminingApplicableDiscounts);
        return newTotalPrice;
    }
}
