package my.edu.utar;

import java.util.Arrays;

/**
 * AddGuest class for the Travel Ticket Booking system.
 * Adds guest details (name, email, and phone number) to a text file.
 */
public class AddGuest {
	
	FileUtilities fu;
	
	public AddGuest(FileUtilities fu) {
		this.fu = fu;
	}
    
    public AddGuest() {
    	fu = new FileUtilities();
    }
	
	public void addGuest(String name, String email, String phoneNumber, String filePath){
		if (name == null || name.trim().isEmpty()) {
			throw new IllegalArgumentException("Invalid name !");
		}
		
		if (email == null || email.isEmpty() || !email.contains("@")) {
			throw new IllegalArgumentException("Invalids Email !");
		}
		
		if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
			throw new IllegalArgumentException("Invalid Phone Number !");
		}
		if (!(phoneNumber.trim().length() >= 10 && phoneNumber.trim().length() <= 12)) {
			throw new IllegalArgumentException("Invalid Phone Number length !");
		}
		if (!phoneNumber.matches("\\d+")) { // Checks if the string contains only digits
		    throw new IllegalArgumentException("Invalid Phone Number format ! Must be all digits.");
		}
		
		String stringToWrite = name + "|" + email + "|" + phoneNumber;
		
		// append new  User To Original User String
		String[] existing  = fu.readStringsFromFile(filePath);
		// Append to existing
        String[] updated = Arrays.copyOf(existing, existing.length + 1);
        updated[existing.length] = stringToWrite;
		
        fu.writeStringsToFile(updated, filePath);
        
	}	
}
