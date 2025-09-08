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
    private String bookingStatus;
    private String paymentStatus;
    
    // Utilities
    private Scanner input = new Scanner(System.in);
    private FileUtilities fu = new FileUtilities();  
    private AddNewUser anu = new AddNewUser(fu);
    private AddGuest ag = new AddGuest();
    private ReadUser ru = new ReadUser();
    
    // Station lists
    private static final List<String> FROM_STATIONS = Arrays.asList(
        "KL Sentral", "Subang Jaya", "Bangsar", "Sentul Timur", "Titiwangsa",
        "Ampang Park", "KLCC", "Masjid Jamek", "Bandaraya", "Batu Kentonmen",
        "Rawang", "Sungai Buloh", "Serdang", "Kajang", "Gombak", 
        "Taman Melati", "Wangsa Maju", "Setiawangsa"
    );
    
    private static final List<String> TO_STATIONS = Arrays.asList(
        "Mid Valley", "Subang Jaya", "Shah Alam", "KL Sentral", "Kepong Sentral",
        "Titiwangsa", "Ampang Park", "KLCC", "Masjid Jamek", "Bandaraya",
        "Batu Kentonmen", "Rawang", "Sungai Buloh", "Kajang", "Semenyih Sentral",
        "Taman Melati", "Wangsa Maju", "Setiawangsa"
    );
    
    private static final List<String> PASSENGER_TYPES = Arrays.asList(
        "Adult", "Senior Citizen", "Child", "Student"
    );
    
    // Constructors
    public booking() {
        this.bookingStatus = "Pending";
        this.paymentStatus = "Pending";
    }
    
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
    public void selectUser() {
        System.out.println("Please select:");
        System.out.println("1. Existing user");
        System.out.println("2. Register as new user");
        System.out.println("3. Proceed as guest");
        System.out.print("Please enter 1 to 3: ");
        
        int choice = getValidIntegerInput(1, 3);
        
        switch(choice) {
            case 1:
                handleExistingUser();
                break;
            case 2:
                handleNewUser();
                break;
            case 3:
                handleGuest();
                break;
        }
    }
    
    private void handleExistingUser() {
        System.out.println("\n\tEXISTING USER");
        System.out.print("Please enter your userID: ");
        String userid = input.nextLine().trim();
        
        if (userid.isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be empty");
        }
        
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
    }
    
    private void handleNewUser() {
        System.out.println("\n\tREGISTER AS NEW USER");
        System.out.print("Please enter your ID: ");
        String userid = input.nextLine().trim();
        System.out.print("Please enter your name: ");
        String name = input.nextLine().trim();
        System.out.print("Please enter your email: ");
        String email = input.nextLine().trim();
        System.out.print("Please enter your phone number: ");
        String phone = input.nextLine().trim();
        
        // Validate inputs
        validateUserInput(userid, name, email, phone);
        
        try {
            this.user = new User(userid, name, email, phone);
            anu.addUser(this.user, "user.txt");
            System.out.println("User registered successfully!");
        } catch (Exception e) {
            throw new RuntimeException("Error creating new user: " + e.getMessage());
        }
    }
    
    private void handleGuest() {
        System.out.println("\n\tPROCEED AS GUEST");
        System.out.print("Please enter your name: ");
        String name = input.nextLine().trim();
        System.out.print("Please enter your email: ");
        String email = input.nextLine().trim();
        System.out.print("Please enter your phone number: ");
        String phone = input.nextLine().trim();
        
        // Validate inputs
        validateGuestInput(name, email, phone);
        
        try {
            this.guestName = name;
            this.guestEmail = email;
            this.guestPhone = phone;
            ag.addGuest(name, email, phone, "guest.txt");
            System.out.println("Guest details saved successfully!");
        } catch (Exception e) {
            throw new RuntimeException("Error saving guest data: " + e.getMessage());
        }
    }
    
    // Travel date and time
    public LocalDateTime setDayTime() {
        System.out.print("Enter travel date (yyyy-MM-dd): ");
        String date = input.nextLine().trim();
        System.out.print("Enter travel time (HH:mm): ");
        String time = input.nextLine().trim();
        
        String dateTime = date + " " + time;
        
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime travelTime = LocalDateTime.parse(dateTime, formatter);
            
            // Validate that the date is not in the past
            if (travelTime.isBefore(LocalDateTime.now())) {
                throw new IllegalArgumentException("Travel date and time cannot be in the past");
            }
            
            this.travelDayTime = travelTime;
            return travelTime;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date/time format. Please use yyyy-MM-dd HH:mm");
        }
    }
    
    // Station selection
    public String setStartStation() {
        System.out.println("\nAvailable start stations:");
        for (int i = 0; i < FROM_STATIONS.size(); i++) {
            System.out.println((i + 1) + ". " + FROM_STATIONS.get(i));
        }
        
        System.out.print("Please select your start station (1-" + FROM_STATIONS.size() + "): ");
        int choice = getValidIntegerInput(1, FROM_STATIONS.size());
        
        //retrive the start station from the list
        this.startStation = FROM_STATIONS.get(choice - 1);
        return this.startStation;
    }
    
    public String setEndStation() {
        System.out.println("\nAvailable end stations:");
        List<String> validEndStations = new ArrayList<>();
        
        for (String station : TO_STATIONS) {
            if (!station.equals(this.startStation)) {
                validEndStations.add(station);
            }
        }
        
        for (int i = 0; i < validEndStations.size(); i++) {
            System.out.println((i + 1) + ". " + validEndStations.get(i));
        }
        
        System.out.print("Please select your end station (1-" + validEndStations.size() + "): ");
        int choice = getValidIntegerInput(1, validEndStations.size());
        
        this.endStation = validEndStations.get(choice - 1);
        return this.endStation;
    }
    
    // Passenger types and quantities
    public Map<String, Integer> setPassengerTypeAndQty() {
        System.out.println("\nPlease enter quantity for each passenger type:");
        this.passengerTypes.clear();
        
        int totalPassengers = 0;
        for (String type : PASSENGER_TYPES) {
            System.out.print(type + " (0 or more): ");
            int qty = getValidIntegerInput(0, 50); // reasonable upper limit
            this.passengerTypes.put(type, qty);
            totalPassengers += qty;
        }
        
        if (totalPassengers == 0) {
            throw new IllegalArgumentException("At least one passenger is required");
        }
        
        return new HashMap<>(this.passengerTypes);
    }
    
    // Payment method selection
    public String setPaymentMethod() {
        System.out.println("\nPayment methods:");
        System.out.println("1. E-wallet");
        System.out.println("2. Credit Card");
        System.out.println("3. Online Banking");
        System.out.print("Please enter payment method (1-3): ");
        
        int choice = getValidIntegerInput(1, 3);
        
        switch(choice) {
            case 1: 
                this.paymentMethod = "E-wallet";
                break;
            case 2:
                this.paymentMethod = "Credit Card";
                break;
            case 3: 
                this.paymentMethod = "Online Banking";
                break;
        }
        return this.paymentMethod;
    }
    
    // booking confirmation
    public void setBookingStatus() {
        System.out.print("Do you confirm to make this booking? (Y/N): ");
        String choice = input.nextLine().trim().toUpperCase();
        
        if (choice.equals("Y") || choice.equals("YES")) {
            this.bookingStatus = "Confirmed";
        } else {
            this.bookingStatus = "Cancelled";
        }
    }
    
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
}
