/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package se.kth.iv1350.pos.integration;

/**
 * An interface for notifying the observers TotalRevenueView and
 * TotalRevenueFileOutput about changes to the total revenue stored
 * in the observed class ExternalAccountingSystem.
 * Is implemented according to the Observer pattern.
 */
public interface TotalRevenueObserver {
    void newPurchase(double totalRevenue);
}
