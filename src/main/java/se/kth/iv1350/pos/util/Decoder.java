/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.pos.util;

/**
 * Helping class for conversion between differing data types.
 */
public class Decoder {
    /**
     * Verifies whether the scanned data is an item identifier or not.
     * 
     * @param scannedBarcode    raw string data inputted by user
     * @return                  scanned barcode is an item identifier
     */
    public boolean isItemIdentifier(int scannedBarcode) {
        if(String.valueOf(scannedBarcode).length() != 9) {
            return false;
        }
        return true;
    }
}
