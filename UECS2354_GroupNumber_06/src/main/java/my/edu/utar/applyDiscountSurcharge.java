package my.edu.utar;

public class applyDiscountSurcharge {

	public static double getPassengerTypeDiscount(String passengerType, double distance) {
		if (passengerType == "Adult") {
			return 1.0;
		} else if (passengerType == "Senior citizen") {
			return 0.5;
		} else if (passengerType == "Student") {
			return 0.7;
		} else if (passengerType == "Child") {
			if (distance < 5) {
				return 0;
			} else {
				return 0.5;
			}
		} else {
			throw new IllegalArgumentException("Passenger type is undefined");
		}
	}
	
	public static double getTimeDateDiscount() {
		return 1.0;
		// update later
	}
	
	public static int getFlatSurcharge() {
		return 1;
		//update later
	}
	
	public static double getTotalDiscount() {
		return 1.0;
		//update later
	}
}
