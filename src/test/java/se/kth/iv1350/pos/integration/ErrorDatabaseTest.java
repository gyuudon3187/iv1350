/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package se.kth.iv1350.pos.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author danie
 */
public class ErrorDatabaseTest {
    private ErrorDatabase instance;
    
    @BeforeEach
    public void setUp() {
        instance = ErrorDatabase.getErrorDatabase();
    }
    
    @AfterEach
    public void tearDown() {
        instance = null;
    }
    
    @Test
    public void testLogExceptionForGenericBusinessLogicExceptionWhenEqual() {
        BusinessLogicException ble = new BusinessLogicException();
        instance.logException(ble);
        Exception actualException = instance.getMiscellaneousBusinessLogicExceptionLog().getLast();
        
        Exception expResult = ble;
        Exception result = actualException;
        assertEquals(expResult, result, "Expected actual exception to be"
                + " BusinessLogicException, but it was not.");
    }
    
    @Test
    public void testLogExceptionForGenericBusinessLogicExceptionWhenNotEqual() {
        BusinessLogicException ble = new BusinessLogicException();
        instance.logException(ble);
        Exception actualException = instance.getMiscellaneousBusinessLogicExceptionLog().getLast();
        
        Exception wrongResult = new Exception();
        Exception result = actualException;
        assertNotEquals(wrongResult, result, "The BusinessLogicException was not"
                + " properly logged.");
    }
    
    @Test
    public void testLogExceptionForDatabaseFailureExceptionWhenEqual() {
        DatabaseFailureException dfe = new DatabaseFailureException();
        instance.logException(dfe);
        Exception actualException = instance.getMiscellaneousExceptionLog().getLast();
        
        Exception expResult = dfe;
        Exception result = actualException;
        assertEquals(expResult, result, "Expected actual exception to be"
                + " DatabaseFailureException, but it was not.");
    }
    
    @Test
    public void testLogExceptionForDatabaseFailureExceptionWhenNotEqual() {
        DatabaseFailureException dfe = new DatabaseFailureException();
        instance.logException(dfe);
        Exception actualException = instance.getMiscellaneousExceptionLog().getLast();
        
        Exception wrongResult = new BusinessLogicException();
        Exception result = actualException;
        assertNotEquals(wrongResult, result, "The DatabaseFailureException was not"
                + " properly logged.");
    }
    
    @Test
    public void testLogExceptionForItemIdentifierFormatExceptionWhenEqual() {
        ItemIdentifierFormatException iife = new ItemIdentifierFormatException();
        instance.logException(iife);
        Exception actualException = instance.getInvalidItemIdentifierLog().getLast();
        
        Exception expResult = iife;
        Exception result = actualException;
        assertEquals(expResult, result, "Expected actual exception to be"
                + " ItemIdentifierFormatException, but it was not.");
    }
    
    @Test
    public void testLogExceptionForItemIdentifierFormatExceptionWhenNotEqual() {
        ItemIdentifierFormatException iife = new ItemIdentifierFormatException();
        instance.logException(iife);
        Exception actualException = instance.getInvalidItemIdentifierLog().getLast();
        
        Exception wrongResult = new Exception();
        Exception result = actualException;
        assertNotEquals(wrongResult, result, "The logged exception was not an"
                + " ItemIdentifierFormatException.");
    }
    
    @Test
    public void testLogExceptionForItemIdentifierNotFoundExceptionWhenEqual() {
        ItemIdentifierNotFoundException iinfe = new ItemIdentifierNotFoundException();
        instance.logException(iinfe);
        Exception actualException = instance.getInvalidItemIdentifierLog().getLast();
        
        Exception expResult = iinfe;
        Exception result = actualException;
        assertEquals(expResult, result, "Expected actual exception to be"
                + " ItemIdentifierNotFoundException, but it was not.");
    }
    
    @Test
    public void testLogExceptionForItemIdentifierNotFoundExceptionWhenNotEqual() {
        ItemIdentifierNotFoundException iinfe = new ItemIdentifierNotFoundException();
        instance.logException(iinfe);
        Exception actualException = instance.getInvalidItemIdentifierLog().getLast();
        
        Exception wrongResult = new Exception();
        Exception result = actualException;
        assertNotEquals(wrongResult, result, "The logged exception was not an"
                + " ItemIdentifierNotFoundException.");
    }

    @Test
    public void testLogExceptionForItemNotInInventoryExceptionWhenEqual() {
        ItemNotInInventoryException iniie = new ItemNotInInventoryException();
        instance.logException(iniie);
        Exception actualException = instance.getItemNotInInvLog().getLast();
        
        Exception expResult = iniie;
        Exception result = actualException;
        assertEquals(expResult, result, "Expected actual exception to be"
                + " ItemNotInInventoryException, but it was not.");
    }
    
    @Test
    public void testLogExceptionForItemNotInInventoryExceptionWhenNotEqual() {
        ItemNotInInventoryException iniie = new ItemNotInInventoryException();
        instance.logException(iniie);
        Exception actualException = instance.getItemNotInInvLog().getLast();
        
        Exception wrongResult = new Exception();
        Exception result = actualException;
        assertNotEquals(wrongResult, result, "The logged exception was not an"
                + " ItemNotInInventoryException.");
    }
}
