/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package se.kth.iv1350.pos.view;

/**
 *
 * @author danie
 */
public class ObserverAlreadyAddedException extends Exception {

    /**
     * Creates a new instance of <code>ObserverException</code> without detail
     * message.
     */
    public ObserverAlreadyAddedException() {
    }

    /**
     * Constructs an instance of <code>ObserverException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ObserverAlreadyAddedException(String msg) {
        super(msg);
    }
}
