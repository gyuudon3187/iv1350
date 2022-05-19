/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.pos.integration;

import java.util.LinkedList;

/**
 * Logs all errors. The instance of the database is created according to the
 * Singleton pattern.
 */
public class ErrorDatabase {
    private static final ErrorDatabase errordb = new ErrorDatabase();
    private LinkedList<ItemIdentifierException> invalidItemIdentifierLog;
    private LinkedList<ItemNotInInventoryException> itemNotInInvLog;
    private LinkedList<BusinessLogicException> miscellaneousBusinessLogicExceptionLog;
    private LinkedList<Exception> miscellaneousExceptionLog;
    
    private ErrorDatabase() {
        invalidItemIdentifierLog = new LinkedList<>();
        itemNotInInvLog = new LinkedList<>();
        miscellaneousBusinessLogicExceptionLog = new LinkedList<>();
        miscellaneousExceptionLog = new LinkedList<>();
    }
    
    private void sortBusinessLogicError(BusinessLogicException ble) {
        if(ble instanceof ItemIdentifierException iie) {
            invalidItemIdentifierLog.add(iie);
        } else if(ble instanceof ItemNotInInventoryException iniie) {
            itemNotInInvLog.add(iniie);
        } else {
            miscellaneousBusinessLogicExceptionLog.add(ble);
        }
    }
    
    /**
     * Gets the only instance of the error database.
     * 
     * @return  the only instance of the error database 
     */
    public static ErrorDatabase getErrorDatabase() {
        return errordb;
    }
    
    public LinkedList<ItemIdentifierException> getInvalidItemIdentifierLog() {
        return invalidItemIdentifierLog;
    }
    
    public LinkedList<ItemNotInInventoryException> getItemNotInInvLog() {
        return itemNotInInvLog;
    }
    
    public LinkedList<BusinessLogicException> getMiscellaneousBusinessLogicExceptionLog() {
        return miscellaneousBusinessLogicExceptionLog;
    }
    
    public LinkedList<Exception> getMiscellaneousExceptionLog(){
        return miscellaneousExceptionLog;
    }
        
    /**
     * Logs the reported exception.
     * 
     * @param e the reported exception
     */
    public void logException(Exception e) {
        if(e instanceof BusinessLogicException ble) {
            sortBusinessLogicError(ble);
        } else {
            miscellaneousExceptionLog.add(e);
        }
        
        
    }
}
