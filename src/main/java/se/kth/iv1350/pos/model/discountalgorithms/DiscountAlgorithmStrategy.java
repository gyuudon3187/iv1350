/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package se.kth.iv1350.pos.model.discountalgorithms;

import java.util.LinkedList;
import se.kth.iv1350.pos.dto.DiscountDTO;
import se.kth.iv1350.pos.dto.SaleDTO;

/**
 * Class for facilitating swapping between the two different algorithms used
 * for finding and calculating sale and item discounts.
 * Is following the Strategy pattern.
 */
public interface DiscountAlgorithmStrategy {
    <T extends DiscountDTO> double findAndCalculateApplicableDiscounts(boolean customerIsMember,
                                                        LinkedList<T> discounts,
                                                        SaleDTO  saleInfoForDeterminingApplicableDiscounts);
}
