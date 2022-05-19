/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package se.kth.iv1350.pos.integration;

/**
 *
 */
public class ItemIdentifierException extends BusinessLogicException {
    private int itemIdentifier;
    
    /**
     * Constructs an instance of <code>ItemIdentifierException</code>
     * without the specified detail message and invalid item identifier.
     *
     */
    public ItemIdentifierException() {
    }

    /**
     * Constructs an instance of <code>ItemIdentifierException</code> with
     * the specified detail message and invalid item identifier..
     *
     * @param msg               the detailed message.
     * @param itemIdentifier    the missing or incorrect item identifier
     */
    public ItemIdentifierException(String msg, int itemIdentifier) {
        super(msg);
        this.itemIdentifier = itemIdentifier;
    }
    
    public int getItemIdentifier() {
        return itemIdentifier;
    }
}
