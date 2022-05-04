/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package se.kth.iv1350.pos.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import se.kth.iv1350.pos.controller.Controller;
import se.kth.iv1350.pos.dto.ItemDTO;
import se.kth.iv1350.pos.dto.SaleDTO;
import se.kth.iv1350.pos.integration.ExternalInventorySystem;
import se.kth.iv1350.pos.model.Sale;
import se.kth.iv1350.pos.model.CashRegister;

/**
 * Tests methods of the ExternalInventorySystem class.
 */
public class ExternalInventorySystemTest {
    private ExternalInventorySystem instance;
    
    @BeforeEach
    public void setUp() {
        instance = new ExternalInventorySystem();
    }
    
    @AfterEach
    public void tearDown() {
        instance = null;
    }
    
    @Test
    public void testFetchItemInfoWhenItemExists() {
        int itemIdentifier = 452283101;
        
        String expResult = "Yoghurt";
        String result = instance.fetchItemInfo(itemIdentifier).getItemName();
        assertEquals(expResult, result, "Item name differs from expected name");
    }
    
    @Test
    public void testFetchItemInfoWhenItemDoesntExist() {
        int itemIdentifier = 0;
        
        ItemDTO expResult = null;
        ItemDTO result = instance.fetchItemInfo(itemIdentifier);
        assertEquals(expResult, result);
    }

    @Test
    public void testVerifyIdentifierWhenItemExists() {
        int itemIdentifier = 452283101;
        
        boolean expResult = true;
        boolean result = instance.verifyIdentifier(itemIdentifier);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testVerifyIdentifierWhenItemDoesntExist() {
        int itemIdentifier = 0;
        
        boolean expResult = true;
        boolean result = instance.verifyIdentifier(itemIdentifier);
        assertNotEquals(expResult, result);
    }

    @Test
    public void testUpdateInventoryLogWhenEqual() {
        int yoghurtItemIdentifier = 452283101;
        ItemDTO yoghurt = instance.fetchItemInfo(yoghurtItemIdentifier);
        int currentYoghurtQuantityInInventory = yoghurt.getItemQuantityInventory();
        
        Sale helperObjForModifyingItemsInSale = new Sale(0);
        helperObjForModifyingItemsInSale.addItemToSale(yoghurt);
        int yoghurtNewQuantityToBeBought = 3;
        helperObjForModifyingItemsInSale.setItemQuantityForLatestScannedItem(yoghurtNewQuantityToBeBought);
        helperObjForModifyingItemsInSale.endSale();
        double randomAmountToPay = 50;
        CashRegister cashReg = new CashRegister();
        SaleDTO completedSale = helperObjForModifyingItemsInSale.processPayment(randomAmountToPay, cashReg);
        instance.updateInventoryLog(completedSale);
        
        int expQuantityOfYoghurtInInventory = currentYoghurtQuantityInInventory - yoghurtNewQuantityToBeBought;
        ItemDTO updatedYoghurt = instance.fetchItemInfo(yoghurtItemIdentifier);
        int actualQuantityOfYoghurtInInventory = 
                updatedYoghurt.getItemQuantityInventory();
        
        int expResult = expQuantityOfYoghurtInInventory;
        int result = actualQuantityOfYoghurtInInventory;
        assertEquals(expResult, result, "The new yoghurt quantity in inventory"
                + " does not equal the expected quantity.");
        
    }
    
    @Test
    public void testUpdateInventoryLogWhenNotEqual() {
        int yoghurtItemIdentifier = 452283101;
        ItemDTO yoghurt = instance.fetchItemInfo(yoghurtItemIdentifier);
        int currentYoghurtQuantityInInventory = yoghurt.getItemQuantityInventory();
        
        Sale helperObjForModifyingItemsInSale = new Sale(0);
        helperObjForModifyingItemsInSale.addItemToSale(yoghurt);
        int yoghurtNewQuantityToBeBought = 3;
        helperObjForModifyingItemsInSale.setItemQuantityForLatestScannedItem(yoghurtNewQuantityToBeBought);
        helperObjForModifyingItemsInSale.endSale();
        double randomAmountToPay = 50;
        CashRegister cashReg = new CashRegister();
        SaleDTO completedSale = helperObjForModifyingItemsInSale.processPayment(randomAmountToPay, cashReg);
        instance.updateInventoryLog(completedSale);
        
        int expQuantityOfYoghurtInInventory = currentYoghurtQuantityInInventory - yoghurtNewQuantityToBeBought;
        ItemDTO updatedYoghurt = instance.fetchItemInfo(yoghurtItemIdentifier);
        int actualQuantityOfYoghurtInInventory = 
                updatedYoghurt.getItemQuantityInventory();
        
        int wrongResult = -1;
        int result = actualQuantityOfYoghurtInInventory;
        assertNotEquals(wrongResult, result, "The new yoghurt quantity in inventory"
                + " equals the wrong quantity.");
        
    }
    
}
