/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package se.kth.iv1350.pos.controller;

/**
 * Generic exception that is thrown when a system operation has failed due
 * to some exception thrown from a deeper layer.
 */
public class OperationFailedException extends Exception {

    /**
     * Constructs an instance of <code>OperationFailedException</code> with the
     * specified detail message.
     *
     * @param msg   the detail message
     */
    public OperationFailedException(String msg, Exception e) {
        super(msg, e);
    }
}
