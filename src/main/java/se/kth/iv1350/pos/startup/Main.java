package se.kth.iv1350.pos.startup;

import java.util.Scanner;
import se.kth.iv1350.pos.controller.Controller;
import se.kth.iv1350.pos.view.*;

/**
 * Contains the main method to start the application.
 */
public class Main {
    private static final Scanner in = new Scanner(System.in);
    
    /**
     * The main method used to start the application.
     * 
     * @param args The application does not require any command line parameters.
     */
    public static void main(String[] args) {
        Controller contr = new Controller(); 
        ViewFactory viewFactory = new ViewFactory(contr);
        
        while(true) {
            selectView(viewFactory).runProgram();
        }
    }
    
    private static View selectView(ViewFactory viewFactory) {
        System.out.println("\nPlease select view mode:"
                + "\nAdmin view (enter 'AdminView')"
                + "\nCashier view (enter 'CashierView')"
                + "\nTotal revenue view (enter 'TotalRevenueView')"
                + "\nTotal revenue output view (enter 'TotalRevenueFileOutput')");
        
        while(true){
            try {
                String selectedView = in.nextLine();
                return viewFactory.getView(selectedView);
            } catch(InvalidUserInputException iuie) {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

}
