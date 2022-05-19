/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package se.kth.iv1350.pos.integration;

import se.kth.iv1350.pos.dto.ItemDTO;


/**
 *
 * @author danie
 */
public class ItemNotInInventoryException extends BusinessLogicException {
    private ItemDTO itemToBeAddedToSale;

    /**
     * Creates a new instance of <code>ItemNotInInventoryException</code>
     * without detail message.
     */
    public ItemNotInInventoryException() {
    }

    /**
     * Constructs an instance of <code>ItemNotInInventoryException</code> with
     * the specified detail message.
     *
     * @param msg                       the detail message
     */
    public ItemNotInInventoryException(String msg,
                                        ItemDTO itemToBeAddedToSale) {
        super(msg);
        this.itemToBeAddedToSale = itemToBeAddedToSale;
    }
    
    /**
     * Get the item that is to be added to sale.
     * 
     * @return the item that is to be added to sale
     */
    public ItemDTO getItemToBeAddedToSale() {
        return itemToBeAddedToSale;
    }
}
