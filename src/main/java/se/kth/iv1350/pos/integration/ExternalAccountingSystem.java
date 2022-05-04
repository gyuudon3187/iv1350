package se.kth.iv1350.pos.integration;

import java.util.HashMap;
import java.util.LinkedList;
import se.kth.iv1350.pos.dto.SaleDTO;

/**
 * External system handling accounting.
 */
public class ExternalAccountingSystem {

	private LinkedList<SaleDTO> saleLog;

	private HashMap<Integer, Double> finLog;

        /**
         * Constructor.
         */
	public ExternalAccountingSystem() {
            saleLog = new LinkedList<>();
            finLog = new HashMap<>();
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

        /**
         * Updates the financial log with information from a completed sale.
         * 
         * @param completedSale a completed sale
         */
	public void updateFinancialLog(SaleDTO completedSale) {
            Integer saleIdentifier = completedSale.getSaleIdentifier();
            Double revenue = completedSale.getTotalPrice();
            
            finLog.put(saleIdentifier, revenue);
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
