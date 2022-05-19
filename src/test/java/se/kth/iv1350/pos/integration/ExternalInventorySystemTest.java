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
        instance = ExternalInventorySystem.getExternalInventorySystem();
    }
    
    @AfterEach
    public void tearDown() {
        instance = null;
    }
    
    @Test
    public void testFetchItemInfoWhenItemExists() throws ItemIdentifierFormatException,
                                                            ItemIdentifierNotFoundException,
                                                            ItemNotInInventoryException,
                                                            DatabaseFailureException {
        int existingItemIdentifier = 452283101;
        
        String expResult = "Yoghurt";
        String result = instance.fetchItemInfo(existingItemIdentifier).getItemName();
        assertEquals(expResult, result, "Item name differs from expected name");
    }
    
    @Test
    public void testFetchItemInfoWhenIdentifierIsWrongFormat() throws ItemIdentifierNotFoundException,
                                                                ItemNotInInventoryException,
                                                                DatabaseFailureException {
        int anIntegerThatIsNotNineDigits = 0;
        int itemIdentifier = anIntegerThatIsNotNineDigits;
        
        assertThrows(ItemIdentifierFormatException.class,
                () -> instance.fetchItemInfo(itemIdentifier),
                "Expected ItemIdentifierFormatException to be thrown, but"
                + " it wasn't.");
    }
    
    @Test
    public void testFetchItemInfoWhenIdentifierIsNotFound() throws ItemIdentifierFormatException,
                                                                ItemNotInInventoryException,
                                                                DatabaseFailureException {
        int nonExistentItemIdentifier = 452283110;
        
        assertThrows(ItemIdentifierNotFoundException.class,
                () -> instance.fetchItemInfo(nonExistentItemIdentifier),
                "Expected ItemIdentifierNotFoundException to be thrown, but"
                + " it wasn't.");
    }
    
    @Test
    public void testFetchItemInfoWhenInsufficientInventoryQuantity() throws ItemIdentifierFormatException,
                                                                ItemIdentifierNotFoundException,
                                                                DatabaseFailureException {
        double randomPrice = 0;
        double randomVATRate = 0;
        int tobaccoIdentifier = 452283103;
        int randomQuantityInSale = 1;
        int zeroQuantityInInventory = 0;
        ItemDTO tobacco = new ItemDTO("Tobacco", randomPrice,randomVATRate,
                                        tobaccoIdentifier, randomQuantityInSale,
                                        zeroQuantityInInventory);
        
        instance.setItemQuantityInInventory(tobacco, zeroQuantityInInventory);
        
        assertThrows(ItemNotInInventoryException.class,
                () -> instance.fetchItemInfo(tobaccoIdentifier),
                "Expected ItemNotInInventoryException to be thrown, but"
                + " it wasn't.");
    }
    
    @Test
    public void testFetchItemInfoWhenNoRespondFromDatabase() throws ItemIdentifierFormatException,
                                                                ItemIdentifierNotFoundException,
                                                                ItemNotInInventoryException {
        int buggedItemIdentifier = 452283106;
        
        assertThrows(DatabaseFailureException.class,
                () -> instance.fetchItemInfo(buggedItemIdentifier),
                "Expected DatabaseFailureException to be thrown, but"
                + " it wasn't.");
    }
    
    @Test
    public void testSetItemQuantityInInventoryWhenEqual() throws ItemIdentifierFormatException,
                                                                    ItemIdentifierNotFoundException,
                                                                    ItemNotInInventoryException,
                                                                    DatabaseFailureException {
        double randomPrice = 0;
        double randomVATRate = 0;
        int tobaccoIdentifier = 452283103;
        int randomQuantityInSale = 1;
        int fourQuantityInInventory = 4;
        ItemDTO tobacco = new ItemDTO("Tobacco", randomPrice,randomVATRate,
                                        tobaccoIdentifier, randomQuantityInSale,
                                        fourQuantityInInventory);
        
        instance.setItemQuantityInInventory(tobacco, fourQuantityInInventory);
        tobacco = instance.fetchItemInfo(tobaccoIdentifier);
        int actualQuantityInInventory = tobacco.getItemQuantityInventory();
        
        int expResult = fourQuantityInInventory;
        int result = actualQuantityInInventory;
        assertEquals(expResult, result, "The inventory quantity was set to 0,"
                + " yet the actual quantity does not equal 0.");
    }

    @Test
    public void testUpdateInventoryLogWhenEqual() throws ItemIdentifierFormatException,
                                                            ItemIdentifierNotFoundException,
                                                            ItemNotInInventoryException,
                                                            DatabaseFailureException {
        int yoghurtItemIdentifier = 452283101;
        ItemDTO yoghurt = instance.fetchItemInfo(yoghurtItemIdentifier);
        int currentYoghurtQuantityInInventory = yoghurt.getItemQuantityInventory();
        
        Sale helperObjForModifyingItemsInSale = new Sale(0);
        helperObjForModifyingItemsInSale.addItemToSale(yoghurt);
        int yoghurtNewQuantityToBeBought = 3;
        helperObjForModifyingItemsInSale.setItemQuantityForLatestScannedItem(yoghurtNewQuantityToBeBought);
        helperObjForModifyingItemsInSale.endSale();
        double randomAmountToPay = 50;
        CashRegister cashReg = CashRegister.getCashRegister();
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
    public void testUpdateInventoryLogWhenNotEqual() throws ItemIdentifierFormatException,
                                                              ItemIdentifierNotFoundException,
                                                              ItemNotInInventoryException,
                                                              DatabaseFailureException {
        int yoghurtItemIdentifier = 452283101;
        ItemDTO yoghurt = instance.fetchItemInfo(yoghurtItemIdentifier);
        
        Sale helperObjForModifyingItemsInSale = new Sale(0);
        helperObjForModifyingItemsInSale.addItemToSale(yoghurt);
        int yoghurtNewQuantityToBeBought = 3;
        helperObjForModifyingItemsInSale.setItemQuantityForLatestScannedItem(yoghurtNewQuantityToBeBought);
        helperObjForModifyingItemsInSale.endSale();
        double randomAmountToPay = 50;
        CashRegister cashReg = CashRegister.getCashRegister();
        SaleDTO completedSale = helperObjForModifyingItemsInSale.processPayment(randomAmountToPay, cashReg);
        instance.updateInventoryLog(completedSale);
        
        ItemDTO updatedYoghurt = instance.fetchItemInfo(yoghurtItemIdentifier);
        int actualQuantityOfYoghurtInInventory = 
                updatedYoghurt.getItemQuantityInventory();
        
        int wrongResult = -1;
        int result = actualQuantityOfYoghurtInInventory;
        assertNotEquals(wrongResult, result, "The new yoghurt quantity in inventory"
                + " equals the wrong quantity.");
        
    }
    
}
