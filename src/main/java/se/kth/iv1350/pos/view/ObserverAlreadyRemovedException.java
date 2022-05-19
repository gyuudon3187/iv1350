/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package se.kth.iv1350.pos.view;

/**
 *
 * @author danie
 */
public class ObserverAlreadyRemovedException extends Exception {

    /**
     * Creates a new instance of <code>ObserverAlreadyRemovedException</code>
     * without detail message.
     */
    public ObserverAlreadyRemovedException() {
    }

    /**
     * Constructs an instance of <code>ObserverAlreadyRemovedException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ObserverAlreadyRemovedException(String msg) {
        super(msg);
    }
}
