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

/**
 * Tests the methods of the ExternalAccountingSystem class.
 */
public class ExternalAccountingSystemTest {
    private ExternalAccountingSystem instance;
    private ExternalInventorySystem extInvSys;
    private Controller contr;
    private Sale sale;
    private ItemDTO yoghurt;
    private ItemDTO banana;
    private ItemDTO tobacco;
    
    @BeforeEach
    public void setUp() {
        instance = new ExternalAccountingSystem();
        extInvSys = new ExternalInventorySystem();
        contr = new Controller();
        sale = new Sale(1);
        yoghurt = extInvSys. fetchItemInfo(452283101);
        banana = extInvSys.fetchItemInfo(452283102);
        tobacco = extInvSys.fetchItemInfo(452283103);
    }
    
    @AfterEach
    public void tearDown() {
        instance = null;
        extInvSys = null;
        sale = null;
        yoghurt = null;
        banana = null;
        tobacco = null; 
    }

    @Test
    public void testGetLatestSaleIdentifierWhenEqual() {
        SaleDTO helperObjForIncrementingSalesInSaleLog = new SaleDTO(sale);
        instance.updateFinancialLog(helperObjForIncrementingSalesInSaleLog);
        
        int expResult = 1;
        int result = instance.getLatestSaleIdentifier();
        assertEquals(expResult, result, "The sale identifier should have been"
                + "1, but it was not");
    }
    
    @Test
    public void testGetLatestSaleIdentifierWhenNotEqual() {
        SaleDTO helperObjForIncrementingSalesInFinLog = new SaleDTO(sale);
        instance.updateFinancialLog(helperObjForIncrementingSalesInFinLog);
        
        int wrongResult = 2;
        int result = instance.getLatestSaleIdentifier();
        assertNotEquals(wrongResult, result, "The sale identifier equals 2,"
                + "even though only one item exists in the financial log");
    }
}
