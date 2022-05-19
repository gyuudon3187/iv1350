/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.pos.model.discountalgorithms;

import java.util.LinkedList;

/**
 * Creates objects for using algorithms related to discounts.
 * Is designed according to the factory pattern.
 */
public class DiscountAlgorithmFactory {
    public LinkedList<DiscountAlgorithmStrategy> getAllDiscountAlgorithms() {
        LinkedList<DiscountAlgorithmStrategy> discountAlgorithms = new LinkedList<>() {{
            add(new ItemDiscountAlgorithm());
            add(new SaleDiscountAlgorithm());
        }};
        
        return discountAlgorithms;
    }
}
