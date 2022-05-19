/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package se.kth.iv1350.pos.integration;

import java.util.LinkedList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import se.kth.iv1350.pos.dto.ItemDiscountDTO;
import se.kth.iv1350.pos.dto.SaleDiscountDTO;

/**
 *
 */
public class DiscountDatabaseTest {
    private DiscountDatabase instance;
    private ItemDiscountDTO tobaccoDiscounts;
    private SaleDiscountDTO mediumSaleDiscount;
    private double actualTobaccoDiscountAmount;
    private double mediumSaleDiscountAmount;
    
    @BeforeEach
    public void setUp() {
        instance = DiscountDatabase.getDiscountDatabase();
        tobaccoDiscounts = instance.getItemDiscounts().getLast();
        mediumSaleDiscount = instance.getSaleDiscounts().get(1);
        actualTobaccoDiscountAmount = tobaccoDiscounts.getItemDiscountAmount();
        mediumSaleDiscountAmount = mediumSaleDiscount.getTotalCostDiscountAmount();
    }
    
    @AfterEach
    public void tearDown() {
        instance = null;
        tobaccoDiscounts = null;
    }

    @Test
    public void testDiscountDatabaseConstructorForItemDiscountsWhenEqual() {
        double expTobaccoDiscountAmount = 12;
        
        double expResult = expTobaccoDiscountAmount;
        double result = actualTobaccoDiscountAmount;
        assertEquals(expResult, result, "Actual tobacco discount amount does not"
                + "equal the expected tobacco discount amount");
    }
    
    @Test
    public void testDiscountDatabaseConstructorForItemDiscountsWhenNotEqual() {
        double wrongResult = 11;
        double result = actualTobaccoDiscountAmount;
        assertNotEquals(wrongResult, result, "Actual tobacco discount amount"
                + "equals the wrong discount amount");
    }

    @Test
    public void testDiscountDatabaseConstructorForSaleDiscountsWhenEqual() {
        double expMediumSaleDiscountAmount = 10;
        
        double expResult = expMediumSaleDiscountAmount;
        double result = mediumSaleDiscountAmount;
        assertEquals(expResult, result, "Actual medium sale discount amount does"
                + "not equal the expected medium sale discount amount");
    }
    
    @Test
    public void testDiscountDatabaseConstructorForSaleDiscountsWhenNotEqual() {
        double wrongResult = 11;
        double result = mediumSaleDiscountAmount;
        assertNotEquals(wrongResult, result, "Actual medium sale discount amount"
                + "equals the wrong discount amount");
    }
    
}
