/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.pos.view;

import java.util.Scanner;
import se.kth.iv1350.pos.controller.Controller;

/**
 * Creates various user interfaces for the user to swap between.
 * 
 */
public class ViewFactory {
    private Scanner in;
    private Controller contr;
    private AdminView admin;
    
    
    public ViewFactory(Controller contr) {
        this.contr = contr;
        in = new Scanner(System.in);
    }
    
    public View getView(String selectedView) throws InvalidUserInputException {
        switch(selectedView) {
            case "AdminView" -> {
                return new AdminView(contr, this);
            }
            case "CashierView" -> {
                return new CashierView(contr);
            }
            case "TotalRevenueView" -> {
                return TotalRevenueView.getTotalRevenueView();
            }
            case "TotalRevenueFileOutput" -> {
                return TotalRevenueFileOutput.getTotalRevenueFileOutput();
            }
            default -> throw new InvalidUserInputException();
        }
    }
}
