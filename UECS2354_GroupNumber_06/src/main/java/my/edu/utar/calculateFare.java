package my.edu.utar;

import java.time.LocalDateTime;

public class calculateFare {

	private routeInfo routeInfo;
	private applyDiscountSurcharge discountModule;
	
	public calculateFare(routeInfo routeInfo, applyDiscountSurcharge discountModule) {
        this.routeInfo = routeInfo;
        this.discountModule = discountModule;
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
	 
	public double calTotalFare(String passengerType, int qtt, String day, LocalDateTime time, String start, String end, String paymentMethod) {
		double totalFare = 0.0;
		
		int i = 0;
		while(i < qtt) {
			double distance = routeInfo.getDistance(start,end);
			double baseFare = getBaseFare(distance);
			double passengerDiscount = discountModule.getPassengerTypeDiscount(passengerType,distance);
			double dayTimeDiscount = discountModule.getTimeDateDiscount(time);
				
			totalFare += (baseFare * passengerDiscount * dayTimeDiscount) ;
			
			i++;
		}
		 
		 return totalFare;
	}
}
