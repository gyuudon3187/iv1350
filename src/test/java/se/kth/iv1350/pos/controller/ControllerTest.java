/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package se.kth.iv1350.pos.controller;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import se.kth.iv1350.pos.dto.SaleDTO;
import se.kth.iv1350.pos.integration.ItemIdentifierFormatException;
import se.kth.iv1350.pos.integration.ItemIdentifierNotFoundException;

/**
 * Tests the methods of the Controller class.
 * 
 */
public class ControllerTest {
    private Controller instance;
    
    @BeforeEach
    public void setUp() {
        instance = new Controller();
        instance.startSale();
    }
    
    @AfterEach
    public void tearDown() {
        instance = null;
    }

    @Test
    public void testStartSaleWhenEqual() {
        Date currentDateAndTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat ("dd/MM/yyyy HH:mm:ss");
        String currentDateAndTimeStr = formatter.format(currentDateAndTime);
        SaleDTO helperObjForExtractingDateAndTimeOfSale = instance.endSale();
        String dateAndTimeOfSale =
                helperObjForExtractingDateAndTimeOfSale.getDateAndTimeOfSale();
        
        String expResult = currentDateAndTimeStr;
        String result = dateAndTimeOfSale;
        assertEquals(expResult, result, "Current date/time differs from sale date/time");
    }

    @Test
    public void testRegisterItemWhenEqual() throws ItemIdentifierFormatException,
                                                ItemIdentifierNotFoundException,
                                                OperationFailedException {
        int itemIdentifier = 452283104;
        SaleDTO helperObjForExtractingLatestScannedItemName =
                instance.registerItem(itemIdentifier);
        String latestScannedItemName =
                helperObjForExtractingLatestScannedItemName.
                        getItemsInSale().getLast().getItemName();
        
        String expResult = "Detergent";
        String result = latestScannedItemName;
        assertEquals(expResult, result, "The name of the scanned item 'Detergent'"
                + " equals the name of some other item.");
    }
    
    @Test
    public void testRegisterItemWhenNotEqual() throws ItemIdentifierFormatException,
                                                    ItemIdentifierNotFoundException,
                                                    OperationFailedException {
        int itemIdentifier = 452283103;
        SaleDTO helperObjForExtractingLatestScannedItemName =
                instance.registerItem(itemIdentifier);
        String latestScannedItemName =
                helperObjForExtractingLatestScannedItemName.
                        getItemsInSale().getLast().getItemName();
        
        String wrongResult = "Detergent";
        String result = latestScannedItemName;
        assertNotEquals(wrongResult, result, "The name of the scanned item 'Tobacco'"
                + "equals the name of the item with identifier 452283104, i.e."
                + " 'Detergent'.");
    }
    
    @Test
    public void testRegisterItemWhenIdentifierIsIncorrectFormat() throws
                                                    ItemIdentifierNotFoundException,
                                                    OperationFailedException {
        int incorrectlyFormattedItemIdentifier = 0;
        assertThrows(ItemIdentifierFormatException.class,
                () -> instance.registerItem(incorrectlyFormattedItemIdentifier),
                "Expected ItemIdentifierFormatException to be thrown, but it"
                + " was not.");
    }
    
    @Test
    public void testRegisterItemWhenIdentifierDoesNotExist() throws
                                                    ItemIdentifierFormatException,
                                                    OperationFailedException {
        int nonExistentItemIdentifier = 452283110;
        assertThrows(ItemIdentifierNotFoundException.class,
                () -> instance.registerItem(nonExistentItemIdentifier),
                "Expected ItemIdentifierNotFoundException to be thrown, but it"
                + " was not.");
    }
    
    @Test
    public void testRegisterItemWhenOperationFailedException() throws
                                                    ItemIdentifierFormatException,
                                                    ItemIdentifierNotFoundException {
        int buggedItemIdentifier = 452283106;
        assertThrows(OperationFailedException.class,
                () -> instance.registerItem(buggedItemIdentifier),
                "Expected ItemIdentifierNotFoundException to be thrown, but it"
                + " was not.");
    }

    @Test
    public void testSetItemQuantityForLatestScannedItemIdentifierWhenEqual()
                                        throws ItemIdentifierFormatException,
                                            ItemIdentifierNotFoundException,
                                            OperationFailedException {
        int itemIdentifier = 452283101;
        instance.registerItem(itemIdentifier);
        int newQuantity = 3;
        instance.setItemQuantityForLatestScannedItem(newQuantity);
        SaleDTO helpObjForExtractingQuantityOfScannedItem = instance.endSale();
        int quantityOfScannedItem =
                helpObjForExtractingQuantityOfScannedItem.
                        getItemsInSale().getLast().getItemQuantityInSale();
        
        int expResult = newQuantity;
        int result = quantityOfScannedItem;
        assertEquals(expResult, result, "The newly set quantity differs from"
                + " the actual updated quantity.");
    }
    
    @Test
    public void testSetItemQuantityForLatestScannedItemIdentifierWhenNotEqual()
                                        throws ItemIdentifierFormatException,
                                                ItemIdentifierNotFoundException,
                                                OperationFailedException {
        int itemIdentifier = 452283101;
        instance.registerItem(itemIdentifier);
        int newQuantity = 3;
        instance.setItemQuantityForLatestScannedItem(newQuantity);
        SaleDTO helpObjForExtractingQuantityOfScannedItem = instance.endSale();
        int quantityOfScannedItem =
                helpObjForExtractingQuantityOfScannedItem.
                        getItemsInSale().getLast().getItemQuantityInSale();
        
        int wrongResult = 2;
        int result = quantityOfScannedItem;
        assertNotEquals(wrongResult, result, "The updated quantity equals an"
                + " unequal integer.");
    }

   /* @Test
    public void testEnterDiscountMode() {
        System.out.println("enterDiscountMode");
        Controller instance = new Controller();
        instance.enterDiscountMode();
        fail("The test case is a prototype.");
    }

    @Test
    public void testCheckForApplicableDiscounts() {
        System.out.println("checkForApplicableDiscounts");
        int customerID = 0;
        Controller instance = new Controller();
        Sale expResult = null;
        Sale result = instance.checkForApplicableDiscounts(customerID);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }*/
    
}
