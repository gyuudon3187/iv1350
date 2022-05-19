package se.kth.iv1350.pos.model;

import se.kth.iv1350.pos.dto.SaleDTO;

/**
 * Class for the cash register within the payment received for the sale is
 * stored.
 */
public class CashRegister {
        private static final CashRegister cashReg = new CashRegister();

	private double cashAmountInRegister;

        /**
         * Constructor. Initiates the amount of cash in the register to 5000.
         */
	private CashRegister() {
            double initialCashAmountInRegister = 5000;
            cashAmountInRegister = initialCashAmountInRegister;
	}

        /**
         * Adds the payment received for the sale to the cash amount in
         * the register.
         * 
         * @param payment   the payment received for the sale
         */
	private void addPaymentToCashAmountInRegister(Payment payment) {
            double amountToAddToRegister = payment.getCashAmount();
            this.setCashAmountInRegister(
                    cashAmountInRegister + amountToAddToRegister);
            payment.setPaidAmount(amountToAddToRegister);
	}

        /**
         * Symbollically outputs the change to return to the customer by
         * subtracting the change amount from the cash amount in the
         * register, and records the amount given as change.
         * 
         * @param diffBetweenPriceAndPayment    the difference between price and payment
         * @param currentSale                   this sale
         */
	private void outputChange(double diffBetweenPriceAndPayment,
                                    Payment payment) {
            this.setCashAmountInRegister(
                    cashAmountInRegister - diffBetweenPriceAndPayment);
            payment.setChange(diffBetweenPriceAndPayment);
	}
        
        /**
         * Sets the amount of cash in register.
         * 
         * @param newCashAmount the new cash amount
         */
        private void setCashAmountInRegister(double newCashAmount) {
            cashAmountInRegister = newCashAmount;
        }

        /**
         * Returns change to customer and adds payment to cash register.
         * 
         * @param diffBetweenPriceAndPayment    the difference between price and payment
         * @param payment                       the received payment
         * @param currentSale                   this sale
         * @return                              summary of sale used by the UI
         */
	SaleDTO returnChangeAndAddPayment(double diffBetweenPriceAndPayment,
                                        Payment payment,
                                        Sale currentSale) {
            this.outputChange(diffBetweenPriceAndPayment, payment);
            this.addPaymentToCashAmountInRegister(payment);
            
            SaleDTO completedSale = new SaleDTO(currentSale);
            return completedSale;
	}
        
        public double getCashAmountInRegister() {
            return cashAmountInRegister;
        }
        
        public static CashRegister getCashRegister() {
            return cashReg;
        }
}
