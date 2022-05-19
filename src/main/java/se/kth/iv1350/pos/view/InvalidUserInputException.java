/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package se.kth.iv1350.pos.view;

/**
 * Thrown when invalid user input is given to a method.
 */
public class InvalidUserInputException extends Exception {

    /**
     * Creates a new instance of <code>InvalidUserInput</code> without detail
     * message.
     */
    public InvalidUserInputException() {
    }

    /**
     * Constructs an instance of <code>InvalidUserInput</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidUserInputException(String msg) {
        super(msg);
    }
}
