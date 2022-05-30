package se.kth.iv1350.pos.view;

import java.util.*;
import se.kth.iv1350.pos.controller.Controller;
import se.kth.iv1350.pos.controller.OperationFailedException;
import se.kth.iv1350.pos.integration.ItemIdentifierNotFoundException;
import se.kth.iv1350.pos.integration.ItemIdentifierFormatException;
import se.kth.iv1350.pos.dto.SaleDTO;
import se.kth.iv1350.pos.dto.ItemDTO;
import se.kth.iv1350.pos.util.Decoder;

/**
 * Placeholder for the real view. Contains hard-coded execution with calls to
 * all system operations in the controller.
 */
public class CashierView implements View {

    private static CashierView view;
    private Controller contr;
    private Scanner in = new Scanner(System.in);
    private Decoder decoder = new Decoder();

    private enum Options {
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
    public CashierView(Controller contr) {
        this.contr = contr;
    }
    
    private void addInitialObservers() {
        TotalRevenueFileOutput totRevFoutObserver = 
                new TotalRevenueFileOutput();
        if(!contr.getTotalRevenueObservers().contains(totRevFoutObserver)) {
            contr.addTotalRevenueObserver(totRevFoutObserver);
        }

        TotalRevenueView totRevViewObserver = 
                TotalRevenueView.getTotalRevenueView();
        if(!contr.getTotalRevenueObservers().contains(totRevViewObserver)) {
            contr.addTotalRevenueObserver(totRevViewObserver);
        }
    }

    /**
     * Runs a sample execution of the program.
     */
    
    @Override
    public void runProgram () {
        boolean executionOngoing = true;
        addInitialObservers();

        while(executionOngoing) {
            this.runSaleOperations();
            this.runDiscountOperations();
            this.runPaymentOperations();
            executionOngoing = this.runProgramAgain();
        }

    }

    private SaleDTO enterDiscountMode() {
        System.out.println("Please enter the customer's identification: ");
        String customerID = in.nextLine();

        return contr.checkForApplicableDiscountsAndApply(customerID);
    }

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

    private void displayHeaderOfSaleSummary() {
        System.out.println("[Summary of ongoing sale]\n");
    }

    private void displayItemSuccessfullyAddedMessage() {
        System.out.println("Item succesfully added.");
    }

    private void displayInvalidIdentifierMessage() {
        System.out.println("Invalid item identifier. The identifier should"
                + " consist of 9 digits. Try again.");
    }

    private void displayInvalidInputMessage() {
        System.out.println("Invalid input. Try again.");
    }

    private void displayInvalidInputMessageWithYNOptions() {
        System.out.println("Invalid option. Please choose"
                        + " between the options 'y' and 'n'");
    }

    private void displayNoSuchIdentifierMessage() {
        System.out.println("Could not perform scan since no item with"
                + " the entered identifier exists. The developers have"
                + " been informed of this issue.");
    }

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

    private void displayOperationFailedMessage() {
        System.out.println("The requested operation could not be completed."
                + " Please contact the administrator for assistance.");
    }

    private void displayReceiptMessage() {
        System.out.println("\n\nThank you for shopping at Daniel's Supermarket!\n"
                + "Your receipt has been printed and can be found over at "
                + System.getProperty("user.dir")
                + "\nWe're looking forward to seeing you again.");
    }

    private void displayRunningTotal(SaleDTO summaryOfCurrentSale) {
        System.out.println("\nRunning total:" + summaryOfCurrentSale.getRunningTotal());
    }

    private void displayTotalPriceAndVAT(SaleDTO summaryOfCurrentSale) {
        double totalPrice = summaryOfCurrentSale.getTotalPrice();
        double totalVAT = summaryOfCurrentSale.getTotalVAT();

        System.out.printf("\nTotal price: " +
                            "%.2f" +
                            "\nTotal VAT: " +
                            "%.2f" + "\n", totalPrice, totalVAT);
    }

    private SaleDTO endSale() {
        System.out.println("Sale ended.");
        return contr.endSale();
    }

    private SaleDTO modifyQuantity() {
        System.out.println("Set a new quantity: ");
                    int newQuantity = in.nextInt();
                    return contr.setItemQuantityForLatestScannedItem(newQuantity);
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

    private boolean runProgramAgain() {
        System.out.println("\n\nStart a new sale? (y/n)");

        while(true) {
            in.nextLine(); // Clear garbage input
            String userInput = in.nextLine();
            switch(userInput) {
                case "y" -> {
                    return true;
                }
                case "n" -> {
                    return false;
                }
                default -> this.displayInvalidInputMessageWithYNOptions();
            }
        }
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
                    summaryOfCurrentSale = 
                            this.scanAndRegisterItem(summaryOfCurrentSale);
                }
                case MODIFY_QUANTITY -> {
                    summaryOfCurrentSale = this.modifyQuantity();
                }
                case END_SALE -> {
                    summaryOfCurrentSale = this.endSale();
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

    // Seminar 4 task 1 a)
    private SaleDTO scanAndRegisterItem(SaleDTO summaryOfCurrentSale) {
        boolean scanOngoing = true;

            while (scanOngoing) {
                int itemIdentifier = this.scanItem();
                try {
                    summaryOfCurrentSale = contr.registerItem(itemIdentifier);
                    
                // Seminar 4 task a)
                } catch (ItemIdentifierFormatException iife) {
                    this.displayInvalidIdentifierMessage();
                    continue;
                } catch (ItemIdentifierNotFoundException iinfe) {
                    this.displayNoSuchIdentifierMessage();
                    continue;
                
                // Seminar 4 task b)
                } catch (OperationFailedException ofe) {
                    this.displayOperationFailedMessage();
                    continue;
                }

                this.displayItemSuccessfullyAddedMessage();
                boolean scanAgain = this.selectWhetherToScanAgain();
                if(!(scanAgain)) {
                    scanOngoing = false;
                }
            }
        return summaryOfCurrentSale;
    }

    private int scanItem() {
        System.out.println("Please scan the item identifier");

        int userInput = in.nextInt();
        return userInput;
    }

    private boolean selectWhetherToScanAgain() {
        System.out.println("Do you wish to scan another item? (y/n)");

        while(true) {
            in.nextLine(); // Clear garbage input
            String userInput = in.nextLine();
            switch(userInput) {
                case "y" -> {
                    return true;
                }
                case "n" -> {
                    return false;
                }
                default -> this.displayInvalidInputMessageWithYNOptions();
            }
        }
    }

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
                default -> this.displayInvalidInputMessageWithYNOptions();
            }
        }
    }

    private Options selectSaleOption() {
        System.out.println("\nIf you wish to scan items, enter 'scan'.\n"
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
