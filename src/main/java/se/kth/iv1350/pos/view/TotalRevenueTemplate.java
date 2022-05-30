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
 * Template class with members shared by the observers of the total revenue.
 */
public abstract class TotalRevenueTemplate implements TotalRevenueObserver {
    protected double totalRevenue;
    protected Scanner in;
    
    protected TotalRevenueTemplate() {
        totalRevenue = 0;
        in = new Scanner(System.in);
    }
    
    @Override
    public void newPurchase(double totalRevenue) {
        this.totalRevenue = totalRevenue;
        showTotalIncome();
    }
    
    protected String getTotalRevenueInfo() {
        return "\n" + getCurrentDateAndTime()
                + "\nTotal Revenue: "
                + totalRevenue;
    }
    
    protected abstract void doShowTotalIncome() throws Exception;
    
    protected abstract void handleErrors(Exception e);
    
    private void showTotalIncome() {
        try {
            doShowTotalIncome();
        } catch (Exception e) {
            handleErrors(e);
        }
    }
    
    private String getCurrentDateAndTime() {
        SimpleDateFormat formatter = new SimpleDateFormat ("dd/MM/yyyy HH:mm:ss");
        Date dateAndTime = new Date();
        
        return formatter.format(dateAndTime);
    }
}
