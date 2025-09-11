package my.edu.utar;

import java.time.*;

public class applyDiscountSurcharge {

	public double getPassengerTypeDiscount(String passengerType, double distance) {
		
		if (distance < 0) {
			throw new IllegalArgumentException("Distance cannot be negative");
		}
		
		if (passengerType.equalsIgnoreCase("Adult")) {
			return 1.0;
		} else if (passengerType.equalsIgnoreCase("Senior Citizen")) {
			return 0.5;
		} else if (passengerType.equalsIgnoreCase("Student")) {
			return 0.7;
		} else if (passengerType.equalsIgnoreCase("Child")) {
			if (distance < 5) {
				return 0;
			} else {
				return 0.5;
			}
		} else {
			throw new IllegalArgumentException("Passenger type is undefined");
		}
	}
	
	public double getTimeDateDiscount(LocalDateTime ticketDateTime) {
		
		if (ticketDateTime == null)
			throw new IllegalArgumentException("Ticket Date Time cannot be null");
		
		DayOfWeek ticketDay = ticketDateTime.getDayOfWeek();
		LocalTime ticketTime = ticketDateTime.toLocalTime();
		
		LocalTime peakHourMorningStart = LocalTime.of(6, 30);
		LocalTime peakHourMorningEnd = LocalTime.of(9, 30);
		
		LocalTime peakHourEveningStart = LocalTime.of(17, 0);
		LocalTime peakHourEveningEnd = LocalTime.of(20, 0);
		
		boolean isPeakHour =  (!ticketTime.isBefore(peakHourMorningStart) && !ticketTime.isAfter(peakHourMorningEnd))
			     || (!ticketTime.isBefore(peakHourEveningStart) && !ticketTime.isAfter(peakHourEveningEnd));
		
		if (ticketDay != DayOfWeek.SATURDAY && ticketDay != DayOfWeek.SUNDAY) {
			if (isPeakHour) {
				return 1.2;
			} else {
				return 1.0;
			}
		} else {
			return 0.9;
		}
	}
	
	public int getFlatSurcharge(LocalDateTime ticketDateTime) {
		if (ticketDateTime == null)
			throw new IllegalArgumentException("Ticket Date Time cannot be null");
		
		DayOfWeek ticketDay = ticketDateTime.getDayOfWeek();
		LocalTime ticketTime = ticketDateTime.toLocalTime();
		
		LocalTime nightTime = LocalTime.of(22,00);
		
		if (ticketDay == DayOfWeek.SATURDAY || ticketDay == DayOfWeek.SUNDAY) {
			return 0;
		} else {
			if (!ticketTime.isBefore(nightTime)) {
				return 2;
			} else {
				return 0;
			}
		}
	}
	
	public double getTotalDiscount(String passengerType, double distance, LocalDateTime ticketDateTime) {
		return getPassengerTypeDiscount(passengerType,distance) * getTimeDateDiscount(ticketDateTime);
	}
}
