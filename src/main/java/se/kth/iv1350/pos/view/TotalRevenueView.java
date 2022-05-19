/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.pos.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import se.kth.iv1350.pos.integration.TotalRevenueObserver;

/**
 * This class tracks any changes made to the total revenue and prints
 * current total revenue onto the display.
 * Implements the Observer pattern.
 */
public class TotalRevenueView implements View, TotalRevenueObserver {
    private static final TotalRevenueView totRevView = new TotalRevenueView();
    private double totalRevenue;
    private Scanner in;
    
    private TotalRevenueView() {
        totalRevenue = 0;
        in = new Scanner(System.in);
    }
    
    public static TotalRevenueView getTotalRevenueView() {
        return totRevView;
    }
    
    @Override
    public void runProgram() {
        boolean ongoingSession = true;
        
        while(ongoingSession) {
            printTotalIncome();
            ongoingSession = selectOption();
        }
    }
    
    @Override
    public void newPurchase(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
    
    private void printTotalIncome() {
        String currentDateAndTime = this.getCurrentDateAndTime();
        System.out.println(currentDateAndTime
                + " \nTotal Revenue: "
                + totalRevenue
                + "\n");
    }
    
    private String getCurrentDateAndTime() {
        SimpleDateFormat formatter = new SimpleDateFormat ("dd/MM/yyyy HH:mm:ss");
        Date dateAndTime = new Date();
        
        return formatter.format(dateAndTime);
    }
    
    private boolean selectOption() {
        System.out.println("\nOptions:"
                + "\n1) Enter 'r' to refresh the total revenue."
                + "\n2) Press any other key + enter to go back to previous menu.");
        String selectedOption = in.nextLine();
        return selectedOption.equalsIgnoreCase("r");
    }
}
