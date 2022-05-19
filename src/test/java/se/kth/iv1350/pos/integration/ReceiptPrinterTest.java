/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package se.kth.iv1350.pos.integration;

import java.io.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import se.kth.iv1350.pos.controller.Controller;
import se.kth.iv1350.pos.dto.ItemDTO;
import se.kth.iv1350.pos.model.Sale;
import se.kth.iv1350.pos.model.Receipt;
import se.kth.iv1350.pos.model.CashRegister;
import se.kth.iv1350.pos.dto.SaleDTO;

/**
 * Tests the methods of the ReceiptPrinter class.
 */
public class ReceiptPrinterTest {
    private Controller contr;
    private ExternalInventorySystem extInvSys;
    private CashRegister cashReg;
    private Sale sale;
    private ItemDTO yoghurt;
    
    @BeforeEach
    public void setUp() throws ItemIdentifierFormatException,
                                ItemIdentifierNotFoundException,
                                ItemNotInInventoryException,
                                DatabaseFailureException {
        contr = new Controller();
        extInvSys = ExternalInventorySystem.getExternalInventorySystem();
        cashReg = CashRegister.getCashRegister();
        sale = new Sale(1);
        yoghurt = extInvSys.fetchItemInfo(452283101);
        sale.addItemToSale(yoghurt);
        sale.endSale();
        double randomAmountToPay = 50;
        sale.processPayment(randomAmountToPay, cashReg);
        
        
    }
    
    @AfterEach
    public void tearDown() {
        contr = null;
        extInvSys = null;
        cashReg = null;
        sale = null;
    }

    @Test
    public void testPrintReceiptWhenReceiptFileExists() {
        String expFirstLineOfReceipt = "<<Daniel's Supermarket>>";
        String firstLineOfReceipt = "";
        
        ReceiptPrinter instance = new ReceiptPrinter();
        SaleDTO helpObjForReceiptConstructor = new SaleDTO(sale);
        Receipt receipt = new Receipt(helpObjForReceiptConstructor);
        instance.printReceipt(receipt);
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader("receipt.txt"));
            firstLineOfReceipt = reader.readLine();
        }   catch (IOException e) {
            e.printStackTrace();
        }
        
        String expResult = expFirstLineOfReceipt;
        String result = firstLineOfReceipt;
        assertEquals(expResult, result, "The first line of the receipt does not"
                + "equal the expected line, and therefore the receipt does"
                + "not exist.");
    }
    
    @Test
    public void testPrintReceiptWhenReceiptFileDoesntExist() {
        String wrongFirstLineOfReceipt = "<<ICA Supermarket>>";
        String firstLineOfReceipt = "";
        
        ReceiptPrinter instance = new ReceiptPrinter();
        SaleDTO helpObjForReceiptConstructor = new SaleDTO(sale);
        Receipt receipt = new Receipt(helpObjForReceiptConstructor);
        instance.printReceipt(receipt);
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader("receipt.txt"));
            firstLineOfReceipt = reader.readLine();
        }   catch (IOException e) {
            e.printStackTrace();
        }
        
        String wrongResult = wrongFirstLineOfReceipt;
        String result = firstLineOfReceipt;
        assertNotEquals(wrongResult, result, "The printer was supposed to print a"
                + "receipt from Daniel's Supermarket, but instead, it printed"
                + "a receipt from ICA Supermarket!");
    }
    
    
    
}
