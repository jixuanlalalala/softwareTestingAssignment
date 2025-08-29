package my.edu.utar;

public class applyDiscountSurcharge {

	public double getPassengerTypeDiscount(String passengerType, double distance) {
		
		if (distance < 0) {
			throw new IllegalArgumentException("Distance cannot be negative");
		}
		
		if (passengerType.equals("Adult")) {
			return 1.0;
		} else if (passengerType.equals("Senior Citizen")) {
			return 0.5;
		} else if (passengerType.equals("Student")) {
			return 0.7;
		} else if (passengerType.equals("Child")) {
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
