/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package se.kth.iv1350.pos.integration;

/**
 *
 * @author danie
 */
public class BusinessLogicException extends Exception {

    /**
     * Creates a new instance of <code>BusinessLogicException</code> without
     * detail message.
     */
    public BusinessLogicException() {
    }

    /**
     * Constructs an instance of <code>BusinessLogicException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public BusinessLogicException(String msg) {
        super(msg);
    }
}
