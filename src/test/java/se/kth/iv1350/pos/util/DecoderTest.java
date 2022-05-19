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
    public void testIsItemIdentifierWhenEqual() {
        int itemIdentifier = 452283101;
        boolean expResult = true;
        boolean result = instance.isItemIdentifier(itemIdentifier);
        assertEquals(expResult, result, "Passed an item identifier but method"
                + "returns false");
    }
    
    @Test
    public void testIsItemIdentifierWhenNotEqual() {
        int itemIdentifier = 45228310;
        boolean expResult = true;
        boolean result = instance.isItemIdentifier(itemIdentifier);
        assertNotEquals(expResult, result, "Passed an argument which is not an"
                + "item identifier, but method returns true");
    }
    
}
