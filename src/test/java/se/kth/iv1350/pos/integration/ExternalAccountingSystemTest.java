/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package se.kth.iv1350.pos.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import se.kth.iv1350.pos.dto.SaleDTO;
import se.kth.iv1350.pos.dto.ItemDTO;
import se.kth.iv1350.pos.model.Sale;
import se.kth.iv1350.pos.controller.Controller;
import se.kth.iv1350.pos.controller.OperationFailedException;

/**
 * Tests the methods of the ExternalAccountingSystem class.
 */
public class ExternalAccountingSystemTest {
    private ExternalAccountingSystem instance;
    private ExternalInventorySystem extInvSys;
    private Controller contr;
    private int yoghurtIdentifier = 452283101;
    private int bananaIdentifier = 452283102;
    private int tobaccoIdentifier = 452283103;
    private SaleDTO sale;
    
    @BeforeEach
    public void setUp() throws ItemIdentifierFormatException,
                                ItemIdentifierNotFoundException,
                                ItemNotInInventoryException,
                                DatabaseFailureException,
                                OperationFailedException {
        instance = ExternalAccountingSystem.getExternalAccountingSystem();
        extInvSys = ExternalInventorySystem.getExternalInventorySystem();
        contr = new Controller();
        
        contr.startSale();
        contr.registerItem(yoghurtIdentifier);
        contr.registerItem(bananaIdentifier);
        contr.endSale();
        contr.pay(200);
        sale = contr.startSale();
        contr.registerItem(tobaccoIdentifier);
        SaleDTO compSale = contr.endSale();
        contr.pay(300);
    }
    
    @AfterEach
    public void tearDown() {
        instance = null;
        extInvSys = null;
        contr = null;
    }

    @Test
    public void testGetLatestSaleIdentifierWhenEqual() {
        int expResult = 4;
        int result = sale.getSaleIdentifier(); // Cannot test getLatestSaleIdentifier
                                               // directly since the private instance
                                               // of ExternalAccountingSystem attributed
                                               // to the Controller object 'contr.
                                               // Instead the method can be verified
                                               // by checking the sale identifier
                                               // of the SaleDTO object returned
                                               // by the method contr.startSale()
        assertEquals(expResult, result, "The sale identifier should have been"
                + "4, but it was not");
    }
    
    @Test
    public void testGetLatestSaleIdentifierWhenNotEqual() {
        int wrongResult = 4;
        int result = sale.getSaleIdentifier(); // Cannot test getLatestSaleIdentifier
                                               // directly since the private instance
                                               // of ExternalAccountingSystem attributed
                                               // to the Controller object 'contr.
                                               // Instead the method can be verified
                                               // by checking the sale identifier
                                               // of the SaleDTO object returned
                                               // by the method contr.startSale()
        assertNotEquals(wrongResult, result, "The sale identifier equals 4,"
                + "even though only one item exists in the financial log");
    }
}
