/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.pos.view;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import se.kth.iv1350.pos.integration.TotalRevenueObserver;

/**
 * Keeps track of the total revenue and prints it as a text file upon the
 * user's request. Implements the Observer pattern.
 */
public class TotalRevenueFileOutput implements View, TotalRevenueObserver {
    private static final TotalRevenueFileOutput totRevFout =
            new TotalRevenueFileOutput();
    private final Scanner in = new Scanner(System.in);
    private PrintWriter logger;
    private double totalRevenue;
    
    private TotalRevenueFileOutput() {
        try {
            logger = new PrintWriter(new FileWriter("RevenueLog.txt"), true);
        } catch (IOException e) {
            System.out.println("UNABLE TO LOG");
            e.printStackTrace();
        }
        
        totalRevenue = 0;
    }
    
    public static TotalRevenueFileOutput getTotalRevenueFileOutput() {
        return totRevFout;
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
    public void newPurchase(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
    
    private void printTotalIncome() {
        SimpleDateFormat formatter = new SimpleDateFormat ("dd/MM/yyyy HH:mm:ss");
        Date dateAndTime = new Date();
        String currentDateAndTime = formatter.format(dateAndTime);
        
        logger.println(currentDateAndTime
                + " \nTotal Revenue: "
                + totalRevenue
                + "\n");
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
