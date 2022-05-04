package se.kth.iv1350.pos.integration;

import java.io.*;
import se.kth.iv1350.pos.dto.ItemDTO;
import se.kth.iv1350.pos.model.Receipt;

/**
 * System for printing receipts.
 */
public class ReceiptPrinter {
    
    /**
     * Default constructor.
     */
    public ReceiptPrinter() {
    }
    
    /**
     * Writes the header of the receipt to the receipt file to be printed.
     * 
     * @param writer  FileWriter object for writing to the receipt file to be printed
     */
    private void writeHeaderOfReceipt(BufferedWriter writer) {
        try {
            writer.write("<<Daniel's Supermarket>>");
            writer.write("\n\t-Receipt-\n\n");
        }   catch (IOException e) {
            e.printStackTrace();
        }
            
    }
    
    /**
     * Writes the items on the receipt to the receipt file to be printed.
     * 
     * @param receipt   the receipt to be printed
     * @param writer      FileWriter object for writing to the receipt to be printed
     */
    private void writeItem(Receipt receipt, BufferedWriter writer) {
        try {
            writer.write("Purchased items:\n");

            String spacesBetweenNameAndQuantity = "\t";
            String spacesBetweenQuantityAndPrice = "\t\t";
            for(ItemDTO containerOfItemInfo: receipt.getItemsInSale()) {
                writer.write(containerOfItemInfo.getItemName() +
                        spacesBetweenNameAndQuantity +
                        "x" +
                        containerOfItemInfo.getItemQuantityInSale() +
                        spacesBetweenQuantityAndPrice +
                        containerOfItemInfo.getItemPrice() +
                        " kr\n");
            }
        }   catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
         * Writes the payment and the change to the receipt.
         * 
         * @param receipt  the sale summary containing the payment and change
         * @param writer      FileWriter object for writing to the receipt to be printed
         */
        private void writePaymentAndChange(Receipt receipt, BufferedWriter writer) {
            try {
                writer.write("\n\nPayment: " + receipt.getPayment().getPaidAmount()
                        + "\nChange: " + receipt.getPayment().getChangeAmount());
            }   catch (IOException e) {
                e.printStackTrace();
            }
            
        }
    
    /**
     * Writes the total price and tax amount of the receipt to the receipt
     * file to be printed.
     * 
     * @param receipt   the receipt to be printed
     * @param writer      FileWriter object for writing to the receipt to be printed
     */
    private void writeTotalPriceAndVAT(Receipt receipt, BufferedWriter writer) {
        try {
            writer.write("\n\nTotal price: " + receipt.getTotalPrice() +
                            "\nTotal VAT: " +
                            receipt.getTotalVAT());
        }   catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints the receipt as a text file.
     * 
     * @param receipt   the receipt to be printed
     */
    public void printReceipt(Receipt receipt) {
        String receiptFileName = "receipt.txt";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(receiptFileName));
            this.writeHeaderOfReceipt(writer);
            this.writeItem(receipt, writer);
            this.writeTotalPriceAndVAT(receipt, writer);
            this.writePaymentAndChange(receipt, writer);
            
            writer.close();
        }   catch (IOException e) {
            e.printStackTrace();
        }
    }


}
