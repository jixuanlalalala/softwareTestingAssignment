package my.edu.utar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class booking {
    private User user;
    private String guestName;
    private String guestEmail; 
    private String guestPhone;
    private LocalDateTime travelDayTime;
    private String startStation;
    private String endStation;
    private Map<String, Integer> passengerTypes = new HashMap<>();
    private String paymentMethod;
    
    private double totalFare;
    private String discountDetails;
    private double finalFare;
    private String bookingStatus; //"completed" / "pending" 
    private String paymentStatus; //"completed" / "pending" 
    
    // Utilities
    private FileUtilities fu = new FileUtilities();  
    
    // Station lists
    private static final List<String> FROM_STATIONS = Arrays.asList(
		"KL SENTRAL", "SUBANG JAYA", "BANGSAR", "SENTUL TIMUR", "TITIWANGSA",
		"AMPANG PARK", "KLCC", "MASJID JAMEK", "BANDARAYA", "BATU KENTONMEN",
		"RAWANG", "SUNGAI BULOH", "SERDANG", "KAJANG", "GOMBAK",
		"TAMAN MELATI", "WANGSA MAJU", "SETIAWANGSA"
    );
    
    private static final List<String> TO_STATIONS = Arrays.asList(
		"MID VALLEY", "SUBANG JAYA", "SHAH ALAM", "KL SENTRAL", "KEPONG SENTRAL",
		"TITIWANGSA", "AMPANG PARK", "KLCC", "MASJID JAMEK", "BANDARAYA",
		"BATU KENTONMEN", "RAWANG", "SUNGAI BULOH", "KAJANG", "SEMENYIH SENTRAL",
		"TAMAN MELATI", "WANGSA MAJU", "SETIAWANGSA"
    );
    
    private static final List<String> PASSENGER_TYPES = Arrays.asList(
    	"ADULT", "SENIOR CITIZEN", "CHILD", "STUDENT"
    );
    
    // Constructors
    public booking() {
        this.bookingStatus = "Pending";
        this.paymentStatus = "Pending";
    }
    
    //user constructor
    public booking(User user, LocalDateTime travelDayTime, String startStation, 
                   String endStation, Map<String, Integer> passengerTypes, String paymentMethod) {
        this.user = user;
        this.travelDayTime = travelDayTime;
        this.startStation = startStation;
        this.endStation = endStation;
        this.passengerTypes = new HashMap<>(passengerTypes);
        this.paymentMethod = paymentMethod;
        this.bookingStatus = "Pending";
        this.paymentStatus = "Pending";
    }
    
    // Guest constructor
    public booking(String guestName, String guestEmail, String guestPhone,
                   LocalDateTime travelDayTime, String startStation, String endStation,
                   Map<String, Integer> passengerTypes, String paymentMethod) {
        this.guestName = guestName;
        this.guestEmail = guestEmail;
        this.guestPhone = guestPhone;
        this.travelDayTime = travelDayTime;
        this.startStation = startStation;
        this.endStation = endStation;
        this.passengerTypes = new HashMap<>(passengerTypes);
        this.paymentMethod = paymentMethod;
        this.bookingStatus = "Pending";
        this.paymentStatus = "Pending";
    }
    
    // User selection methods
    public void setUser(String userType, String id, String name, String email, String phoneNumber) {
        switch(userType) {
            case "existing user":
                handleExistingUser(id);
                break;
            case "new user":
                handleNewUser(id, name, email, phoneNumber);
                break;
            case "guest":
                handleGuest(name, email, phoneNumber);
                break;
        }
    }
    
    private void handleExistingUser(String userid) {
        
        if (userid== null || userid.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be empty");
        }
        
        ReadUser ru = new ReadUser();
        this.user = ru.readUser(userid, "user.txt");
    }
	
        /*
        try {
        	// the filename will change later on
            this.user = ru.readUser(userid, "user.txt");
            if (this.user == null) {
                throw new IllegalArgumentException("User not found");
            }
            System.out.println("Welcome back, " + this.user.getName() + "!");
        } catch (Exception e) {
            throw new RuntimeException("Error reading user data: " + e.getMessage());
        }
        */
    
    
    private void handleNewUser(String userid, String name, String email, String phone) {
    	
    	if(userid== null || userid.trim().isEmpty())
			throw new IllegalArgumentException("id cannot be empty or null");
		
		if(name== null || name.trim().isEmpty())
			throw new IllegalArgumentException("name cannot be empty or null");

		if(email== null || email.trim().isEmpty())
			throw new IllegalArgumentException("email cannot be empty or null");
		
		if(!email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$"))
			throw new IllegalArgumentException("Invalid email format") ;
		
		if(phone== null || phone.trim().isEmpty())
			throw new IllegalArgumentException("phoneNumber cannot be empty or null");
		
		if(!phone.matches("\\d{10,12}") )
			throw new IllegalArgumentException("Invalid phone number format");
    	
    	User user = new User(userid, name, email, phone);
    	this.user = user;
        AddNewUser anu = new AddNewUser(fu);
        anu.addUser(user, "user.txt");
        
    }
    
    private void handleGuest(String name, String email, String phone) {
        
        // Validate inputs
    	if(name== null || name.trim().isEmpty())
			throw new IllegalArgumentException("name cannot be empty or null");

		if(email== null || email.trim().isEmpty())
			throw new IllegalArgumentException("email cannot be empty or null");
		
		if(!email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$"))
			throw new IllegalArgumentException("Invalid email format") ;
		
		if(phone== null || phone.trim().isEmpty())
			throw new IllegalArgumentException("phoneNumber cannot be empty or null");
		
		if(!phone.matches("\\d{10,12}") )
			throw new IllegalArgumentException("Invalid phone number format");
    	
        
        this.guestName = name;
        this.guestEmail = email;
        this.guestPhone = phone;
        AddGuest ag = new AddGuest();
        ag.addGuest(name, email, phone, "guest.txt");
    }
    
    // Travel date and time
    public void setDayTime(String DayTime) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    	LocalDateTime travelTime = LocalDateTime.parse(DayTime, formatter);

        if(DayTime == null|| DayTime.trim().isEmpty())
        	throw new IllegalArgumentException("Travel date and time cannot be null");
    	
    	if(travelTime.format(formatter) != null)
    		throw new IllegalArgumentException("Date-time must be in format yyyy-MM-dd HH:mm");
        
        if(travelTime.isBefore(LocalDateTime.now()))
        	throw new IllegalArgumentException("Travel date and time cannot be in the past");
        
        this.travelDayTime = travelTime;
    }
    
    // Station selection
    public void setStartStation(String start) {
    	start = start.toUpperCase();
    	
    	if(start == null || start.trim().isEmpty() )
    		throw new IllegalArgumentException("Start station is required");
    	
    	if(!FROM_STATIONS.contains(start.trim()))
    		throw new IllegalArgumentException("Start station is invalid");
    	
        this.startStation = start;
    }
    
    public void setEndStation(String end) {
        end = end.toUpperCase();
    	
    	if(end == null || end.trim().isEmpty())
    		throw new IllegalArgumentException("End station is required");
    	
    	if(!TO_STATIONS.contains(end.trim()))
    		throw new IllegalArgumentException("End station is invalid");
    	
    	if(end != this.startStation) 
    		throw new IllegalArgumentException("End station cannot be same with Start station");
        
        this.endStation = end;
    }
    
    // Passenger types and quantities
    public void setPassengerTypeAndQty(int[] qty) {
        
        int totalPassengers = 0;
        int i = 0;
        
        if (qty == null || qty.length == 0 )
        	throw new IllegalArgumentException("no passenger in the pruchase");
        
        for(int item: qty) {
        	if(item < 0 || item> 50)
        		throw new IllegalArgumentException("Invalid amount of tickets for each passenger types.");
        }
        
        for (String type : PASSENGER_TYPES) {
            this.passengerTypes.put(type, qty[i++]);
            totalPassengers += qty[i];
        }
        
        if (totalPassengers == 0) {
            throw new IllegalArgumentException("At least one passenger is required");
        }
        
    }
    
    // Payment method selection
    public void setPaymentMethod(String payment) {
        
    	if(payment == null || payment.trim().isEmpty() )
    		throw new IllegalArgumentException("Empty payment method");
    	
    	if(payment.trim() !="E-wallet" || payment.trim() != "Credit Card" || payment.trim() != "Online Banking")
    		throw new IllegalArgumentException("Invalid payment method");
    	
        
        switch(payment) {
            case "E-wallet": 
                this.paymentMethod = "E-wallet";
                break;
            case "Credit Card":
                this.paymentMethod = "Credit Card";
                break;
            case "Online Banking": 
                this.paymentMethod = "Online Banking";
                break;
        }
        setPaymentStatus("Completed");
    }
    
    //payment confirmation
    public void setPaymentStatus(String status) {
    	
    	if(status == null || status.trim().isEmpty())
    		throw new IllegalArgumentException("status cannot be empty");
    	
    	if(!status.equals("Pending") && !status.equals("Completed"))
    		throw new IllegalArgumentException("Invalid status");
    	
    	this.paymentStatus = status;
    }
    
    // booking confirmation
    public void setBookingStatus(String status) {
        
        if (status == null || status.trim().isEmpty()) 
            throw new IllegalArgumentException("status cannot be empty");
        
        if(!status.equals("Pending") && !status.equals("Completed"))
        	throw new IllegalArgumentException("Invalid status");
        
        this.bookingStatus = status;
    }
    
    //total fare
    public double getTotalFare() {
    	routeInfo ri = new routeInfo();
    	ri.setDistance(this.startStation, this.endStation);
    	double distance = ri.getDistance();
    	
    	calculateFare cf = new calculateFare();
    	this.totalFare = cf.calTotalFare(this.passengerTypes, this.travelDayTime, this.startStation, this.endStation);
    	
    	return this.totalFare;
    }
    
    //show fare after discount
    public double getFareDiscount() {
    	paymentMethodAdjustment pma = new paymentMethodAdjustment();
    	pma.applyPaymentDiscount(this.paymentMethod, this.totalFare);
    	
    	return this.totalFare;
    }
    
    //show the curremt BookingStatus
    public String getBookingStatus() {
    	return this.bookingStatus;
    }
    
    //show the current paymentStatus
    public String getPaymentStatus() {
    	return this.paymentStatus;
    }
    
    // Process payment
    public boolean confirmAndPay() {
    	payment py = new payment();
    	return py.processPayment(paymentMethod);
    }
    
    
    /*
    // Process complete booking
    public void processBooking() {
        try {
            // Get route info and calculate fare
            routeInfo ri = new routeInfo();
            ri.setDistance(this.startStation, this.endStation);
            double distance = ri.getDistance();
            
            calculateFare cf = new calculateFare();
            this.totalFare = cf.calTotalFare(this.passengerTypes, this.travelDayTime, 
                                                 this.startStation, this.endStation);
            
            // Apply payment method adjustment
            paymentMethodAdjustment pma = new paymentMethodAdjustment();
            this.finalFare = pma.applyPaymentDiscount(this.paymentMethod, this.totalFare);
            
            // Display booking summary
            displayBookingSummary();
            
            // Get final confirmation
            setBookingStatus();
            
            if (this.bookingStatus.equals("Confirmed")) {
                this.paymentStatus = "Processing";
                System.out.println("Booking confirmed! Processing payment...");
            } else {
                System.out.println("Booking cancelled.");
            }
            
        } catch (Exception e) {
            this.bookingStatus = "Failed";
            throw new RuntimeException("Error processing booking: " + e.getMessage());
        }
    }
    
    /*
    
    // Display booking summary
    public void displayBookingSummary() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("BOOKING SUMMARY");
        System.out.println("=".repeat(50));
        
        if (user != null) {
            System.out.println("User: " + user.getName() + " (" + user.getId() + ")");
        } else {
            System.out.println("Guest: " + guestName);
        }
        
        System.out.println("Travel: " + startStation + " → " + endStation);
        System.out.println("Date/Time: " + travelDayTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        System.out.println("Passengers: " + passengerTypes);
        System.out.println("Payment Method: " + paymentMethod);
        System.out.printf("Total Fare: RM%.2f%n", totalFare);
        System.out.printf("Final Fare: RM%.2f%n", finalFare);
        System.out.println("=".repeat(50));
    }
    
    // Utility methods
    private int getValidIntegerInput(int min, int max) {
        while (true) {
            try {
                int value = Integer.parseInt(input.nextLine().trim());
                if (value < min || value > max) {
                    System.out.print("Please enter a number between " + min + " and " + max + ": ");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
    /*
    
    private void validateUserInput(String userid, String name, String email, String phone) {
        if (userid.isEmpty() || name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            throw new IllegalArgumentException("All user fields are required");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
    
    private void validateGuestInput(String name, String email, String phone) {
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            throw new IllegalArgumentException("All guest fields are required");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
    
    
    // Main method for testing
    public static void main(String[] args) {
        try {
            booking booking = new booking();
            
            System.out.println("Welcome to Travel Ticket Booking System");
            System.out.println("=======================================");
            
            // Step 1: Select user type
            booking.selectUser();
            
            // Step 2: Set travel details
            booking.setDayTime();
            booking.setStartStation();
            booking.setEndStation();
            booking.setPassengerTypeAndQty();
            booking.setPaymentMethod();
            
            // Step 3: Process booking
            booking.processBooking();
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    */
}
