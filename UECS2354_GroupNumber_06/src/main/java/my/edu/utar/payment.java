package my.edu.utar;

public class payment {
	
	public boolean processPayment(String paymentMethod) {
		
		if (!paymentMethod.equals("E-wallet") && !paymentMethod.equals("Credit Card") && !paymentMethod.equals("Online Banking")) {
			return false;
		}
		else {
			return true;
		}
	}

}
