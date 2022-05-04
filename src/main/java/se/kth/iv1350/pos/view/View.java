package se.kth.iv1350.pos.view;

import java.util.*;
import se.kth.iv1350.pos.controller.Controller;
import se.kth.iv1350.pos.dto.SaleDTO;
import se.kth.iv1350.pos.dto.ItemDTO;
import se.kth.iv1350.pos.util.Decoder;

/**
 * Placeholder for the real view. Contains hard-coded execution with calls to
 * all system operations in the controller.
 */
public class View {

	private Controller contr;
        private Scanner in = new Scanner(System.in);
        private Decoder decoder = new Decoder();
        
        enum Options {
            MODIFY_QUANTITY,
            END_SALE,
            SCAN,
            ENTER_DISCOUNT,
            SKIP_DISCOUNT;
        }
        
        /**
         * Constructor.
         * 
         * @param contr the controller to which the UI passes method calls 
         */
	public View(Controller contr) {
            this.contr = contr;
	}
        
        /**
         * Sample execution of the program.
         */
        public void runProgram () {
            this.runSaleOperations();
            this.runDiscountOperations();
            this.runPaymentOperations();
        }
        
        /**
         * Signal for discount if requested by customer.
         * 
         * @return  a summary of the sale used by the UI
         */
        private SaleDTO enterDiscountMode() {
            System.out.println("Please enter the customer's identification: ");
            String customerID = in.nextLine();
            SaleDTO summaryOfCurrentSale = contr.checkForApplicableDiscountsAndApply(customerID);
            return summaryOfCurrentSale;
        }
        
        /**
         * Displays the updated information of the ongoing sale.
         * 
         * @param summaryOfCurrentSale    the sale summary to be printed
         */
	private void displaySaleSummary(SaleDTO summaryOfCurrentSale) {
            this.displayHeaderOfSaleSummary();
            this.displayItem(summaryOfCurrentSale);
            this.displayRunningTotal(summaryOfCurrentSale);
            
            if(summaryOfCurrentSale.isOngoing() == false) {
                this.displayTotalPriceAndVAT(summaryOfCurrentSale);
            }
            
            if(summaryOfCurrentSale.getPayment() != null) {
                this.displayReceiptMessage();
            }
        }
        
        /**
         * Display the header of the sale summary.
         */
        private void displayHeaderOfSaleSummary() {
            System.out.println("[Summary of ongoing sale]\n");
        }
        
        /**
         * Displays message stating that item has been added successfully.
         */
        private void displayItemSuccessfullyAddedMessage() {
            System.out.println("Item succesfully added.");
        }
        
        /**
         * Displays message stating that the scanned item identifier is invalid.
         */
        private void displayInvalidIdentifierMessage() {
            System.out.println("Invalid item identifier. Try again.");
        }
        
        /**
         * Displays message stating that the user input is invalid.
         */
        private void displayInvalidInputMessage() {
            System.out.println("Invalid input. Try again.");
        }
        
        /**
         * Displays message saying that the user input is invalid, and that
         * the user must choose between the options 'y' and 'n'.
         */
        private void displayInvalidInputMessageWithYNOptions() {
            System.out.println("Invalid option. Please choose"
                            + " between the options 'y' and 'n'");
        }
        
        /**
         * Display the items contained within the current sale.
         * 
         * @param summaryOfCurrentSale  the sale summary containing the item info to be printed
         */
        private void displayItem(SaleDTO summaryOfCurrentSale) {
            System.out.println("Items in sale:");
            
            String spacesBetweenNameAndQuantity = "\t";
            String spacesBetweenQuantityAndPrice = "\t\t";
            for(ItemDTO containerOfItemInfo: summaryOfCurrentSale.getItemsInSale()) {
                System.out.println(containerOfItemInfo.getItemName() +
                        spacesBetweenNameAndQuantity +
                        "x" +
                        containerOfItemInfo.getItemQuantityInSale() +
                        spacesBetweenQuantityAndPrice +
                        containerOfItemInfo.getItemPrice() +
                        " kr");
            }
        }
        
        /**
         * Displays the payment and the change.
         * 
         * @param summaryOfCurrentSale  the sale summary containing the payment and change
         */
        private void displayPaymentAndChange(SaleDTO summaryOfCurrentSale) {
            System.out.println("Payment: " + summaryOfCurrentSale);
        }
        
        /**
         * Displays information about where the receipt can be found.
         */
        private void displayReceiptMessage() {
            System.out.println("\n\nThank you for shopping at Daniel's Supermarket!\n"
                    + "Your receipt has been printed and can be found over at "
                    + System.getProperty("user.dir")
                    + "\nWe're looking forward to seeing you again.");
        }
        
        /**
         * Display the running total of the sale.
         * 
         * @param summaryOfCurrentSale  the sale summary containing the running total to be printed
         */
        private void displayRunningTotal(SaleDTO summaryOfCurrentSale) {
            System.out.println("\nRunning total:" + summaryOfCurrentSale.getRunningTotal());
        }
        
        /**
         * Displays the total price and VAT after an ended sale.
         */
        private void displayTotalPriceAndVAT(SaleDTO summaryOfCurrentSale) {
            double totalPrice = summaryOfCurrentSale.getTotalPrice();
            double totalVAT = summaryOfCurrentSale.getTotalVAT();
            
            System.out.printf("\nTotal price: " +
                                "%.2f" +
                                "\nTotal VAT: " +
                                "%.2f" + "\n", totalPrice, totalVAT);
        }
        
        /**
         * Executes all discount related operations.
         */
        private void runDiscountOperations() {
            Options selectedOption = this.selectDiscountOption();
            
            if(selectedOption == Options.ENTER_DISCOUNT) {
                SaleDTO summaryOfCurrentSale = this.enterDiscountMode();
                this.displaySaleSummary(summaryOfCurrentSale);
            }
        }
        
        /**
         * Executes all payment related operations. 
         */
        private void runPaymentOperations() {
            System.out.print("Enter the amount of cash you wish to pay by: ");
            double paidAmount = in.nextDouble();
            contr.pay(paidAmount);
            
            this.displayReceiptMessage();
        }
        
        /**
         * Executes all sale related operations.
         */
        private void runSaleOperations() {
            SaleDTO summaryOfCurrentSale = contr.startSale();
            Options selectedOption;
            
            while (summaryOfCurrentSale == null || summaryOfCurrentSale.isOngoing()) {
                selectedOption = this.selectSaleOption();
                switch(selectedOption) {
                    case SCAN -> {
                        boolean scanOngoing = true;
                        while (scanOngoing) {
                            String scannedBarcode = this.scanBarcode();
                        
                            if(scannedBarcode.equals("Invalid input")) {
                                this.displayInvalidInputMessage();
                            }   else {
                                summaryOfCurrentSale = contr.scanItem(scannedBarcode);
                                if(summaryOfCurrentSale == null) {
                                    this.displayInvalidIdentifierMessage();
                                } else {
                                    this.displayItemSuccessfullyAddedMessage();
                                }
                                
                                boolean scanAgain = this.selectWhetherToScanAgain();
                                if(!(scanAgain)) {
                                    scanOngoing = false;
                                }
                            }
                        }
                    }
                    case MODIFY_QUANTITY -> {
                        System.out.println("Set a new quantity: ");
                        int newQuantity = in.nextInt();
                        summaryOfCurrentSale = contr.setItemQuantityForLatestScannedItem(newQuantity);
                    }
                    case END_SALE -> {
                        System.out.println("Sale ended.");
                        summaryOfCurrentSale = contr.endSale();
                    }
                    default -> {
                        this.displayInvalidInputMessage();
                        continue;
                    }
                }
                
                if(summaryOfCurrentSale != null) {
                    this.displaySaleSummary(summaryOfCurrentSale);
                }
            }
        }
        
        /**
         * Scan the raw, unprocessed data of the barcode and determine
         * whether it is a proper item identifier or not.
         * 
         * @return  the raw data of the barcode, which is either an item identifier or invalid input
         */
        private String scanBarcode() {
            System.out.println("Please scan the barcode (enter item identifier)");
            
            String userInput = in.nextLine();
            boolean isItemIdentifier = decoder.isItemIdentifier(userInput);
            
            if(isItemIdentifier) {
                return userInput;
            }   else {
                return "Invalid input";
            }
        }
        
        /**
         * Give the user the option to scan another item after having
         * scanned one.
         * 
         * @return  boolean for whether to scan again or not
         */
        private boolean selectWhetherToScanAgain() {
            System.out.println("Do you wish to scan another item? (y/n)");
            
            while(true) {
                String userInput = in.nextLine();
                switch(userInput) {
                    case "y" -> {
                        return true;
                    }
                    case "n" -> {
                        return false;
                    }
                    default -> System.out.println("Invalid option. Please choose"
                            + " between the options 'y' and 'n'");
                }
            }
        }
        
        /**
         * Give the user the option to request a discount or not.
         * 
         * @return  the option chosen by the user
         */
        private Options selectDiscountOption() {
            System.out.println("Does the customer request discount? (y/n)");
            
            while(true) {
                String userInput = in.nextLine();
                switch(userInput) {
                    case "y" -> {
                        return Options.ENTER_DISCOUNT;
                    }
                    case "n" -> {
                        return Options.SKIP_DISCOUNT;
                    }
                    default -> System.out.println("Invalid option. Please choose"
                            + " between the options 'y' and 'n'");
                }
            }
        }

        /**
         * Give the user the options to:
         * 1) scan an item
         * 2) modify the quantity of the latest scanned item
         * 3) end the ongoing sale
         * 
         * @return  the option chosen by the user
         */
        private Options selectSaleOption() {
            System.out.println("If you wish to scan items, enter 'scan'.\n"
                    + "If you wish to modify the quantity of the latest"
                    + " scanned item, enter 'quantity'.\n "
                    + "If you wish to end the sale, enter 'end'.");
            
            while(true) {
                String userInput = in.nextLine();
                switch(userInput) {
                    case "scan" -> {
                        return Options.SCAN;
                    }
                    case "quantity" -> {
                        return Options.MODIFY_QUANTITY;
                    }
                    case "end" -> {
                        return Options.END_SALE;
                    }
                    default -> this.displayInvalidInputMessage();
                }
            }
        }
}
