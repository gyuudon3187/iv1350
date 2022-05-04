/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.pos.util;

/**
 * Helping class for conversion between differing data types.
 */
public class Decoder {
    public int decodeBarcode (String scannedBarcode) {
        return Integer.parseInt(scannedBarcode);
    }
    
    /**
     * Verifies whether the scanned data is an item identifier or not.
     * Solution inspired by https://stackoverflow.com/questions/237159/whats-the-best-way-to-check-if-a-string-represents-an-integer-in-java
     * 
     * @param scannedBarcode    raw string data inputted by user
     * @return                  scanned barcode is an item identifier
     */
    public boolean isItemIdentifier(String scannedBarcode) {
        if(scannedBarcode == null) {
            return false;
        }
        
        if(scannedBarcode.length() != 9) {
            return false;
        }
        
        for(int i = 0; i < scannedBarcode.length(); i++) {
            char ch = scannedBarcode.charAt(i);
            if(ch < '0' || ch > '9') {
                return false;
            }
        }
        
        return true;
    }
}
