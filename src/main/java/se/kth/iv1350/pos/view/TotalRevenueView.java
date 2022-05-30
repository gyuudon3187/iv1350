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
public class TotalRevenueView extends TotalRevenueTemplate implements View {
    private static final TotalRevenueView totRevView = new TotalRevenueView();
    private TotalRevenueView() {}
    
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
    protected void doShowTotalIncome() throws Exception {
        printTotalIncome();
    }
    
    @Override
    protected void handleErrors(Exception e) {
        e.printStackTrace();
    }
    
    private void printTotalIncome() {
        System.out.println(super.getTotalRevenueInfo());
    }
    
    private boolean selectOption() {
        System.out.println("\nOptions:"
                + "\n1) Enter 'r' to refresh the total revenue."
                + "\n2) Press any other key + enter to go back to previous menu.");
        String selectedOption = in.nextLine();
        return selectedOption.equalsIgnoreCase("r");
    }
}
