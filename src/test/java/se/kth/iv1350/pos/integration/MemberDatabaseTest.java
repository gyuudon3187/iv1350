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
public class MemberDatabaseTest {
    
    private MemberDatabase instance;
    
    @BeforeEach
    public void setUp() {
        instance = new MemberDatabase();
    }
    
    @AfterEach
    public void tearDown() {
        instance = null;
    }

    @Test
    public void testValidateCustomerIDWhenMemberExists() {
        String customerID = "8405231243";
        
        boolean expResult = true;
        boolean result = instance.validateCustomerID(customerID);
        assertEquals(expResult, result, "Returns invalid customer ID, even though customer exists");
    }
    
    @Test
    public void testValidateCustomerIDWhenMemberDoesNotExist() {
        String customerID = "8405231244";
        boolean expResult = false;
        boolean result = instance.validateCustomerID(customerID);
        assertEquals(expResult, result, "Returns valid customer ID, even though no such customer exists");
    }
    
}
