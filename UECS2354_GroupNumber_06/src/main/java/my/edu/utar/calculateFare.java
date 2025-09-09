package my.edu.utar;

import java.time.LocalDateTime;
import java.util.Map;

public class calculateFare {

	private routeInfo ri;
	private applyDiscountSurcharge aps;
	
	public calculateFare() {
		ri = new routeInfo();
		aps = new applyDiscountSurcharge();
	}

	public calculateFare(routeInfo ri, applyDiscountSurcharge aps) {
        this.ri = ri;
        this.aps = aps;
    }

		
	public double getBaseFare(double distance) {
		 if (distance <= 0 || distance > 30) {
	        throw new IllegalArgumentException("Distance must be between 1 km and 30 km");
	     }
		 if(distance <= 5)
			 return 2.0;
		 else if(distance <= 10)
			 return 5.0;
		 else if(distance <=15)
			 return 10.0;
		 else if(distance<=20)
			 return 15.0;
		 else
			 return 20.0;
	}
	 
	
	public double calTotalFare(Map<String, Integer> passengerMap, LocalDateTime time, String start, String end) {
		if (passengerMap == null || passengerMap.isEmpty()) {
	        throw new IllegalArgumentException("Passenger map cannot be null or empty.");
	    }

	    double totalFare = 0.0;

	    // Set distance based on route
	    ri.setDistance(start, end);
	    double distance = ri.getDistance();
	    double baseFare = getBaseFare(distance);

	    // Loop over each passenger type and its quantity
	    for (Map.Entry<String, Integer> entry : passengerMap.entrySet()) {
	        String passengerType = entry.getKey();
	        int qtt = entry.getValue();

	        if (qtt <= 0) {
	            throw new IllegalArgumentException("Quantity cannot be zero or negative for passenger type: " + passengerType);
	        }

	        double totalDiscount = aps.getTotalDiscount(passengerType, distance, time);
	        if (totalDiscount < 0) {
	            throw new IllegalArgumentException("Discount factor cannot be negative for passenger type: " + passengerType);
	        }

	        int flatSurcharge = aps.getFlatSurcharge(time);
	        if (flatSurcharge < 0) {
	            throw new IllegalArgumentException("Flat surcharge cannot be negative.");
	        }

	        double farePerPassengerType = (baseFare * totalDiscount + (double) flatSurcharge) * qtt;

	        if (farePerPassengerType < 0) {
	            farePerPassengerType = 0.0; // prevent negative fare
	        }

	        totalFare += farePerPassengerType;
	    }
		
		return totalFare;
	}
}
