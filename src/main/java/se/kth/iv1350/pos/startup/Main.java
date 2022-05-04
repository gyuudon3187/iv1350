package se.kth.iv1350.pos.startup;

import se.kth.iv1350.pos.controller.Controller;
import se.kth.iv1350.pos.view.View;

/**
 * Contains the main method to start the application.
 */
public class Main {
    /**
     * The main method used to start the application.
     * 
     * @param args The application does not require any command line parameters.
     */
    public static void main(String[] args) {
        Controller contr = new Controller();
        View view = new View(contr);
        view.runProgram();
    }

}
