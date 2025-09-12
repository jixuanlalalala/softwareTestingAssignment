package my.edu.utar;

public class paymentMethodAdjustment {
	
	
	public double applyPaymentDiscount(String paymentType, double fare) {

		if (paymentType.equalsIgnoreCase("E-Wallet")){
			return fare;
		}
		else if (paymentType.equalsIgnoreCase("Credit Card")) {
			return 1.05*fare;
		} else if (paymentType.equalsIgnoreCase("Online Banking")) {
			return 0.95*fare;
		} else {
			throw new IllegalArgumentException("Payment method not accepted.");
		}
	}
}
