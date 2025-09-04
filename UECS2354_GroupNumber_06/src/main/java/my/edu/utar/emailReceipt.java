package my.edu.utar;

public class emailReceipt {
	public void sendReceipt(booking b) {

		//Build receipt content
		StringBuilder r = new StringBuilder();
		
		//Notice: I will change the getter later, I push the code first
		/*
		 * r.append("=========== E-Ticket / Peyment Receipt ===========\n");
		 * r.append("Booking ID : ").append(b.getBookingID()).append("\n");
		 * r.append("User		 : ").append(b.getUserName()).append("\n");
		 * r.append("Email		 : ").append(b.getUserEmail()).append("\n");
		 * r.append("To		 : ").append(b.getStartStation()).append("\n");
		 * r.append("From		 : ").append(b.getEndStation()).append("\n");
		 * r.append("Travel Date: ").append(b.getTravelDate()).append("\n");
		 * r.append("Travel Time: ").append(b.getTravelTime()).append("\n");
		 * r.append("Base Fare	 : ").append(b.getBaseFare()).append("\n");
		 * r.append("Discount/Surcharge: ").append(b.getDiscount()).append("\n");
		 * r.append("Total Fare : ").append(b.getTotalFare()).append("\n");
		 * r.append("Payment Method	: ").append(b.getEndStation()).append("\n");
		 * r.append("==================================================\n");
		 * 
		 * //Simulate email sending System.out.println("Sending receipt email to: " +
		 * b.getEmail()); System.out.println(r.toString());
		 */

	}
}
