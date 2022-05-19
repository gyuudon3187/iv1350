/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package se.kth.iv1350.pos.integration;

/**
 *
 */
public class ItemIdentifierNotFoundException extends ItemIdentifierException {
    public ItemIdentifierNotFoundException() {
    }
    
    /**
     * Constructs an instance of <code>NoSuchItemIdentifierException</code> with
     * the specified detail message.
     *
     * @param msg               the detail message.
     * @param itemIdentifier    the missing or incorrect item identifier
     */
    public ItemIdentifierNotFoundException(String msg, int itemIdentifier) {
        super(msg, itemIdentifier);
    }
}
