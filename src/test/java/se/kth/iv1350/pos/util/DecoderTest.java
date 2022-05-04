/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package se.kth.iv1350.pos.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the methods of the Decoder class.
 */
public class DecoderTest {
    private Decoder instance;
    
    @BeforeEach
    public void setUp() {
        instance = new Decoder();
    }
    
    @AfterEach
    public void tearDown() {
        instance = null;
    }

    @Test
    public void testDecodeBarcodeWhenEqual() {
        String scannedBarcode = "452283101";
        int expResult = 452283101;
        int result = instance.decodeBarcode(scannedBarcode);
        assertEquals(expResult, result, "The returned data type isn't an integer");
    }

    @Test
    public void testIsItemIdentifierWhenEqual() {
        String scannedBarcode = "452283101";
        boolean expResult = true;
        boolean result = instance.isItemIdentifier(scannedBarcode);
        assertEquals(expResult, result, "Passed an item identifier but method"
                + "returns false");
    }
    
    @Test
    public void testIsItemIdentifierWhenNotEqual() {
        String scannedBarcode = "452283101d";
        boolean expResult = true;
        boolean result = instance.isItemIdentifier(scannedBarcode);
        assertNotEquals(expResult, result, "Passed an argument which is not an"
                + "item identifier, but method returns true");
    }
    
}
