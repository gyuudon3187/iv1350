package se.kth.iv1350.pos.integration;

import java.util.HashMap;
import java.util.LinkedList;
import se.kth.iv1350.pos.dto.SaleDTO;

/**
 * External system handling accounting.
 * Is instantiated according to the singleton pattern.
 */
public class ExternalAccountingSystem {
        private static final ExternalAccountingSystem extAcctSys =
                new ExternalAccountingSystem();
    
        private LinkedList<TotalRevenueObserver> totalRevenueObservers;
                
	private LinkedList<SaleDTO> saleLog;

	private HashMap<Integer, Double> finLog;
        
        private double totalRevenue;

        /**
         * Constructor.
         */
	private ExternalAccountingSystem() {
            totalRevenueObservers = new LinkedList<>();
            saleLog = new LinkedList<>();
            finLog = new HashMap<>();
            totalRevenue = 0;
	}
        
        public void addTotalRevenueObservers(LinkedList<TotalRevenueObserver> observers) {
            if(totalRevenueObservers.isEmpty()) {
                totalRevenueObservers.addAll(observers);
            }
            
            if(!totalRevenueObservers.isEmpty()) {
                for(TotalRevenueObserver observer : observers) {
                    if(!totalRevenueObservers.contains(observer)) {
                        totalRevenueObservers.add(observer);
                    }
                }
            }
        }
        
        public static ExternalAccountingSystem getExternalAccountingSystem() {
            return extAcctSys;
        }
        
        /**
         * Get the latest sale identifier (which equals the amount of entries
         * contained within the data structure)
         * 
         * @return  the latest sale identifier
         */
        public int getLatestSaleIdentifier() {
            int latestSaleIdentifier = finLog.size();
            
            return latestSaleIdentifier;
        }
        
        private void notifyObservers() {
            for(TotalRevenueObserver observer : totalRevenueObservers) {
                Double immutableTotalRevenue = totalRevenue;
                observer.newPurchase(immutableTotalRevenue);
            }
        }

        /**
         * Updates the financial log with information from a completed sale.
         * 
         * @param completedSale a completed sale
         */
	public void updateFinancialLog(SaleDTO completedSale) {
            Integer saleIdentifier = completedSale.getSaleIdentifier();
            Double revenue = completedSale.getTotalPrice();
            
            finLog.put(saleIdentifier, revenue);
            totalRevenue += revenue;
            this.notifyObservers();
	}

        /**
         * Logs a completed sale in the sale logs.
         * 
         * @param completedSale a completed sale
         */
	public void updateSaleLog(SaleDTO completedSale) {
            saleLog.add(completedSale);
	}
}
