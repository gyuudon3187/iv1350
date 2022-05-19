/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package se.kth.iv1350.pos.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import se.kth.iv1350.pos.controller.Controller;
import se.kth.iv1350.pos.dto.ItemDTO;
import se.kth.iv1350.pos.dto.SaleDTO;
import se.kth.iv1350.pos.integration.DatabaseFailureException;
import se.kth.iv1350.pos.integration.ExternalInventorySystem;
import se.kth.iv1350.pos.integration.ItemIdentifierFormatException;
import se.kth.iv1350.pos.integration.ItemIdentifierNotFoundException;
import se.kth.iv1350.pos.integration.ItemNotInInventoryException;

/**
 * Tests the methods of the Sale class.
 */
public class SaleTest {
    private Controller contr;
    private ExternalInventorySystem extInvSys;
    private Sale instance;
    private ItemDTO yoghurt;
    private ItemDTO banana;
    private ItemDTO detergent;
    private double priceOfYoghurt;
    private double priceOfBanana;
    private double priceOfTobacco;
    
    @BeforeEach
    public void setUp() throws ItemIdentifierFormatException,
                                ItemIdentifierNotFoundException,
                                ItemNotInInventoryException,
                                DatabaseFailureException {
        contr = new Controller();
        extInvSys = ExternalInventorySystem.getExternalInventorySystem();
        instance = new Sale(1);
        yoghurt = extInvSys.fetchItemInfo(452283101);
        banana = extInvSys.fetchItemInfo(452283102);
        detergent = extInvSys.fetchItemInfo(452283104);
        instance.addItemToSale(yoghurt);
        instance.addItemToSale(banana);
        instance.addItemToSale(detergent);
        priceOfYoghurt = yoghurt.getItemPrice();
        priceOfBanana = banana.getItemPrice();
        priceOfTobacco = detergent.getItemPrice();
    }
    
    @AfterEach
    public void tearDown() {
        extInvSys = null;
        instance = null;
        yoghurt = null;
        banana = null;
        detergent = null;
    }
    
    @Test
    public void testSaleConstructorForSaleIdentifierWhenEqual() {
        contr.startSale();
        contr.pay(0);
        SaleDTO helperObjForExtractingSaleIdentifier = contr.startSale();
        contr.pay(0);
        
        int expResult = 8;
        int result = helperObjForExtractingSaleIdentifier.getSaleIdentifier();
        assertEquals(expResult, result, "The sale identifier should be 4 since"
                + "four sales have been started, but it is not 4");
    }
    
    @Test
    public void testSaleConstructorForSaleIdentifierWhenNotEqual() {
        contr.startSale();
        contr.pay(0);
        SaleDTO helperObjForExtractingSaleIdentifier = contr.startSale();
        contr.pay(0);
        
        int wrongResult = 3;
        int result = helperObjForExtractingSaleIdentifier.getSaleIdentifier();
        assertNotEquals(wrongResult, result, "The sale identifier should be 2 since"
                + "two sales have been started, but it is not 2");
    }
    
    @Test
    public void testAddItemToSaleWhenIdentifierMatches() {
        String expResult = "Detergent";
        String result = instance.getItemsInSale().getLast().getItemName();
        assertEquals(expResult, result, "The name of the last added item 'Detergent'"
                + "differs from expected name");
    }
    
    @Test
    public void testAddItemToSaleWhenIdentifierDoesntMatch() {
        String wrongResult = "Yoghurt";
        String result = instance.getItemsInSale().getLast().getItemName();
        assertNotEquals(wrongResult, result, "The name  of the last added item"
                + "'Tobacco' equals 'Yoghurt'");
    }

    /*@Test
    public void testCheckForApplicableDiscounts() {
        boolean customerIsMember = false;
        Item itemsInSale = null;
        ItemDiscount itemDiscounts = null;
        Sale instance = new Sale();
        SaleDTO expResult = null;
        SaleDTO result = instance.checkForApplicableDiscounts(customerIsMember, itemsInSale, itemDiscounts);
        assertEquals(expResult, result);
    }*/

    @Test
    public void testProcessPaymentWhenChangeIsCorrectlyCalculated() {
        double cashAmount = 200;
        double totalPrice = priceOfYoghurt + priceOfBanana + priceOfTobacco;
        double diffBetweenPriceAndPayment = cashAmount - totalPrice;
        
        instance.endSale();
        CashRegister cashReg = CashRegister.getCashRegister();
        SaleDTO helperObjForExtractingChangeAmount =
                instance.processPayment(cashAmount, cashReg);
        double changeAmount =
                helperObjForExtractingChangeAmount.getPayment().getChangeAmount();
        
        double expResult = diffBetweenPriceAndPayment;
        double result = changeAmount;
        assertEquals(expResult, result, "The change differs from the difference"
                + "between the total price and the payment");
    }
    
    @Test
    public void testProcessPaymentWhenChangeIsIncorrectlyCalculated() {
        instance.endSale();
        
        CashRegister cashReg = CashRegister.getCashRegister();
        double cashAmount = 200;
        SaleDTO helperObjForExtractingChangeAmount =
                instance.processPayment(cashAmount, cashReg);
        double changeAmount =
                helperObjForExtractingChangeAmount.getPayment().getChangeAmount();
        
        double wrongResult = 0;
        double result = changeAmount;
        assertNotEquals(wrongResult, result, "A payment exceeding the total price"
                + "was made, but no change was given in return.");
    }
    
    @Test
    public void testProcessPaymentWhenPaymentIsProperlyRegisteredInSale() {
        CashRegister cashReg = CashRegister.getCashRegister();
        double cashAmount = 200;
        SaleDTO helperObjForExtractingChangeAmount = 
                instance.processPayment(cashAmount, cashReg);
        double paymentAmount =
                helperObjForExtractingChangeAmount.getPayment().getPaidAmount();
        
        double expResult = cashAmount;
        double result = paymentAmount;
        assertEquals(expResult, result, "The change differs from the difference"
                + "between the total price and the payment");
    }

    @Test
    public void testEndSaleTotalPriceWhenEqual() {
        instance.endSale();
        
        double expResult = priceOfYoghurt + priceOfBanana + priceOfTobacco;
        double result = instance.getTotalPrice();
        assertEquals(expResult, result, "Total price does not equal the correct amount");
    }
    
    @Test
    public void testEndSaleTotalPriceWhenNotEqual() {
        instance.endSale();
        
        double wrongResult = priceOfYoghurt + priceOfBanana;
        double result = instance.getTotalPrice();
        assertNotEquals(wrongResult, result, "Total price equals the price of"
                + "yoghurt + banana, but there's also a package of tobacco"
                + "in the sale.");
    }
    
    public void testEndSaleTotalVATWhenEqual() {
        double vatOfYoghurt = yoghurt.getItemVATRate();
        double vatOfBanana = banana.getItemVATRate();
        double vatOfTobacco = detergent.getItemVATRate();
        double expTotalVAT = (priceOfYoghurt * vatOfYoghurt)
                + (priceOfBanana * vatOfBanana)
                + (priceOfTobacco * vatOfTobacco);
        
        
        instance.endSale();
        
        double expResult = expTotalVAT;
        double result = instance.getTotalVAT();
        assertEquals(expResult, result, "Total VAT does not equal the correct amount");
    }
    
    public void testEndSaleTotalVATWhenNotEqual() {
        double vatOfYoghurt = yoghurt.getItemVATRate();
        double vatOfBanana = banana.getItemVATRate();
        double expTotalVAT = (priceOfYoghurt * vatOfYoghurt)
                + (priceOfBanana * vatOfBanana);
        
        
        instance.endSale();
        
        double wrongResult = expTotalVAT;
        double result = instance.getTotalVAT();
        assertEquals(wrongResult, result, "Total VAT equals the VAT amount of"
                + "yoghurt + banana, but there's also a package of tobacco"
                + "in the sale.");
    }

    @Test
    public void testSetItemQuantityForLatestScannedItemWhenEqual() {
        int newQuantity = 4;
        instance.setItemQuantityForLatestScannedItem(newQuantity);
        
        int expResult = newQuantity;
        int result = instance.getItemsInSale().getLast().getItemQuantityInSale();
        assertEquals(expResult, result, "Item quantity does not equal new quantity");
    }
    
    @Test
    public void testSetItemQuantityForLatestScannedItemWhenNotEqual() {
        int newQuantity = 4;
        instance.setItemQuantityForLatestScannedItem(newQuantity);
        
        int wrongResult = 3;
        int result = instance.getItemsInSale().getLast().getItemQuantityInSale();
        assertNotEquals(wrongResult, result, "New item quantity is 4, but it equals 3");
    }
    
}
