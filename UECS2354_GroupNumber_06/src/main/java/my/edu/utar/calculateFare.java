package my.edu.utar;

import java.time.LocalDateTime;

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
		 if (distance < 1 || distance > 30) {
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
	 
	public double calTotalFare(String passengerType, int qtt, LocalDateTime time, String start, String end) {
		double totalFare = 0.0;
		
		ri.setDistance(start, end);
		
		double distance = ri.getDistance();
		
		double baseFare = getBaseFare(distance);
		
		double totalDiscount = aps.getTotalDiscount(passengerType,distance,time);
		
		int flatSurcharge = aps.getFlatSurcharge(time);
		
		totalFare = (baseFare*totalDiscount + (double)flatSurcharge)*qtt;	
		
		return totalFare;
	}
}
