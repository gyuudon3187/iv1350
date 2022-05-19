/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package se.kth.iv1350.pos.integration;

/**
 * Thrown when the database does not respond for whatever reason.
 */
public class DatabaseFailureException extends Exception {

    /**
     * Creates a new instance of <code>DatabaseFailureException</code> without
     * detail message.
     */
    public DatabaseFailureException() {
    }

    /**
     * Constructs an instance of <code>DatabaseFailureException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public DatabaseFailureException(String msg) {
        super(msg);
    }
}
