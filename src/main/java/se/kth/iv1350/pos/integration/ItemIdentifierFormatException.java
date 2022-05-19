/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package se.kth.iv1350.pos.integration;

/**
 *
 */
public class ItemIdentifierFormatException extends ItemIdentifierException {
    public ItemIdentifierFormatException() {
    }
    
    public ItemIdentifierFormatException(String msg, int itemIdentifier) {
        super(msg, itemIdentifier);
    }
}
