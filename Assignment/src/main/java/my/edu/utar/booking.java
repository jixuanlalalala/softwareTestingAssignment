package my.edu.utar;
import java.text.*;
import java.time.*;
import java.time.format.*;
import java.util.*;


public class booking {
	private user user;
	private String guestUser;
	private String travelDay;
	private String travelStartStation;
	private String travelEndStation;
	private String paymentMethod;
	private String paymentStatus; //paid or pending
	private String bookingStatus; // confirmed or cancelled or in progress
	
	private Map<String, Integer> passengers; //store the passenger type and quantity for each passenger types
	
	private double totalFare;
	private double discountDetails;
	private double fareAfterDiscount;
	
	//for registered user
	public booking(user User, String travelDay, String travelStart, String travelEnd, String paymentMethod, String paymentStatus, Map<String, Integer> passenger, String bookingStatus) {
		User = user;
		this.travelDay = travelDay;
		travelStart = travelStartStation;
		travelEnd = travelEndStation;
		this.passengers = passenger;
		this.paymentMethod = paymentMethod;
		this.paymentStatus = "Pending";
		this.bookingStatus = "In Progress";
	}
	
	//for guestUser
	public booking(String guestUser, String travelDay, String travelStart, String travelEnd, String paymentMethod, String paymentStatus, Map<String, Integer> passenger, String bookingStatus) {
		this.guestUser = guestUser;
		this.travelDay = travelDay;
		travelStart = travelStartStation;
		travelEnd = travelEndStation;
		this.passengers = passenger;
		this.paymentMethod = paymentMethod;
		this.paymentStatus = "Pending";
		this.bookingStatus = "In Progress";
	}
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		boolean valid = false;
		double userBoolean;
		
		while(!valid) {
			System.out.println("Welcome to Travel Ticket Booking System");
			System.out.println("Please select :\n1.Existing User \n2.Register as new user \n3.Proceed as guest");
			try {
				switch(input.nextInt()) {
				case 1 : 
					System.out.println("Please enter your user name:");
					String userName = input.nextLine();
					//validate the user is in the userlist by readUser;
				case 2 : 
					//addNewUser();
				case 3 : 
					//addGuest();
				default: 
					valid = true;
				}
			}catch(InputMismatchException e) {
				System.out.println("Invalid input! Please enter a valid integer in range of 1 to 3");
				input.nextInt();
			}
		}
		
	}
	
	public static String selectDayandTime() {
		Scanner input = new Scanner(System.in);
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyy");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		
		String validDateString = null;
		String validTimeString = null;
		
		boolean DateRepeat = true;
		boolean TimeRepeat = true;
		
		while (DateRepeat) {
			try {
				System.out.println("Please select the date of travel (DD/MM/YYYY):");
				LocalDate date = LocalDate.parse(input.nextLine(), dateFormatter);
				validDateString = date.format(dateFormatter);
				DateRepeat = false;
			}catch(DateTimeParseException e) {
				System.out.println("Invalid date format! Please enter with DD/MM/YYYY format");
			}
		}
		
		while (TimeRepeat) {
			try {
				System.out.println("Please enter the time of travel (HH:mm)");
				LocalDate time = LocalDate.parse(input.nextLine(), timeFormatter);
				validTimeString = time.format(timeFormatter);
				TimeRepeat = false;
			}catch(DateTimeParseException e) {
				System.out.println("Invalid time! Please use format HH:mm (24-hour)");
			}
		}
		
		
		return validDateString + " " + validTimeString; //return as the travel Day
	}
	
	//left the route and 
	
}
