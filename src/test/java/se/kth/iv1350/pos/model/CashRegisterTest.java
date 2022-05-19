/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package se.kth.iv1350.pos.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import se.kth.iv1350.pos.dto.ItemDTO;
import se.kth.iv1350.pos.dto.SaleDTO;
import se.kth.iv1350.pos.integration.DatabaseFailureException;
import se.kth.iv1350.pos.integration.ExternalInventorySystem;
import se.kth.iv1350.pos.integration.ItemIdentifierFormatException;
import se.kth.iv1350.pos.integration.ItemIdentifierNotFoundException;
import se.kth.iv1350.pos.integration.ItemNotInInventoryException;

/**
 *
 * @author danie
 */
public class CashRegisterTest {
    
    private CashRegister instance;
    private ExternalInventorySystem extInvSys;
    private double cashAmount;
    private Payment payment;
    private Sale currentSale;
    private ItemDTO yoghurt;
    private ItemDTO banana;
    private ItemDTO detergent;
    private double priceOfYoghurt;
    private double priceOfBanana;
    private double priceOfTobacco;
    private double totalPrice;
    private double diffBetweenPriceAndPayment;
    
    @BeforeEach
    public void setUp() throws ItemIdentifierFormatException,
                                ItemIdentifierNotFoundException,
                                ItemNotInInventoryException,
                                DatabaseFailureException {
        instance = CashRegister.getCashRegister();
        extInvSys = ExternalInventorySystem.getExternalInventorySystem();
        cashAmount = 200;
        payment = new Payment(cashAmount);
        currentSale = new Sale(1);
        yoghurt = extInvSys.fetchItemInfo(452283101);
        banana = extInvSys.fetchItemInfo(452283102);
        detergent = extInvSys.fetchItemInfo(452283104);
        currentSale.addItemToSale(yoghurt);
        currentSale.addItemToSale(banana);
        currentSale.addItemToSale(detergent);
        priceOfYoghurt = yoghurt.getItemPrice();
        priceOfBanana = banana.getItemPrice();
        priceOfTobacco = detergent.getItemPrice();
        totalPrice = priceOfYoghurt + priceOfBanana + priceOfTobacco;
        diffBetweenPriceAndPayment = payment.getCashAmount() - totalPrice;
    }
    
    @AfterEach
    public void tearDown() {
        instance = null;
        extInvSys = null;
        payment = null;
        currentSale = null;
        yoghurt = null;
        banana = null;
        detergent = null;
    }
    
    @Test
    public void testReturnChangeAndAddPaymentWhenPaymentIsProperlyAddedToCashReg() {
        double cashAmountInRegisterBeforeAddingPayment = instance.getCashAmountInRegister();
        instance.returnChangeAndAddPayment(diffBetweenPriceAndPayment, payment, currentSale);
        double cashAmountInRegisterAfterAddingPayment = instance.getCashAmountInRegister();
        double actualCashAmountAddedToRegister = 
                cashAmountInRegisterAfterAddingPayment - cashAmountInRegisterBeforeAddingPayment;
        
        double expResult = totalPrice;
        double result = actualCashAmountAddedToRegister;
        assertEquals(expResult, result, "The change differs from the difference"
                + "between the total price and the payment");
    }
    
    @Test
    public void testReturnChangeAndAddPaymentWhenPaymentIsNotProperlyAddedToCashReg() {
        double cashAmountInRegisterBeforeAddingPayment = instance.getCashAmountInRegister();
        instance.returnChangeAndAddPayment(diffBetweenPriceAndPayment, payment, currentSale);
        double cashAmountInRegisterAfterAddingPayment = instance.getCashAmountInRegister();
        double actualCashAmountAddedToRegister = 
                cashAmountInRegisterAfterAddingPayment - cashAmountInRegisterBeforeAddingPayment;
        
        double wrongResult = cashAmountInRegisterBeforeAddingPayment;
        double result = actualCashAmountAddedToRegister;
        assertNotEquals(wrongResult, result, "No cash has been added to cash register.");
    }
}
