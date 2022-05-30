/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.pos.view;

import java.io.*;

/**
 * Keeps track of the total revenue and prints it as a text file upon the
 * user's request. Implements the Observer pattern.
 */
public class TotalRevenueFileOutput extends TotalRevenueTemplate implements View {
    private PrintWriter logger;
    
    protected TotalRevenueFileOutput() {
        try {
            logger = new PrintWriter(new FileWriter("RevenueLog.txt"), true);
        } catch (IOException e) {
            System.out.println("UNABLE TO LOG");
            e.printStackTrace();
        }
    }
    
    @Override
    public void runProgram() {
        boolean printOngoing = true;
        
        while(printOngoing) {
            boolean firstPrintCompleted = false;
            this.selectPrintOption(firstPrintCompleted);
            this.printTotalIncome();
            firstPrintCompleted = true;
            printOngoing = this.selectPrintOption(firstPrintCompleted);
        }
    }
    
    @Override
    protected void doShowTotalIncome() {
        printTotalIncome();
    }
    
    @Override
    protected void handleErrors(Exception e) {
        e.printStackTrace();
    }
    
    private void printTotalIncome() {
        logger.println(super.getTotalRevenueInfo());
    }
    
    private boolean selectPrintOption(boolean firstPrintCompleted) {
        if(firstPrintCompleted) {
            System.out.println("Do wish to print again?");
        } else {
            System.out.println("Do you wish to print the current total revenue"
                + " as a text file? (y/n)");
        }
        
        while(true){
            String selectedOption = in.nextLine();
            if(selectedOption.equalsIgnoreCase("y")) {
                return true;
            } else if(selectedOption.equalsIgnoreCase("n")) {
                return false;
            } else {
                System.out.println("Please select between the options y/n.");
            }
        }
    }
}
