
package my.edu.utar;

import java.util.Arrays;

/**
 * AddNewUser class for the Travel Ticket Booking system.
 * Adds a new user with name, ID, email, and phone number to a text file.
 */
public class AddNewUser {

    FileUtilities fu;
    
    public AddNewUser(FileUtilities fu) {
		this.fu = fu;
	}

    public void addUser(User user, String filePath) {
    	if (user == null) 
    		throw new IllegalArgumentException("Error ! Invalid User !");
    	
    	if (user.getId() == null || user.getId().trim().isEmpty()) 
			throw new IllegalArgumentException("id cannot be empty or null");
    	
    	if (user.getName() == null || user.getName().trim().isEmpty()) 
			throw new IllegalArgumentException("name cannot be empty or null");
    	
		if ( user.getEmail() == null ||  user.getEmail().isEmpty()) 
			throw new IllegalArgumentException("email cannot be empty or null");
		
		if (!user.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) 
			throw new IllegalArgumentException("Invalids Email !");
		
		if (user.getPhoneNumber() == null || user.getPhoneNumber().trim().isEmpty()) 
			throw new IllegalArgumentException("phoneNumber cannot be empty or null");
		
		if(!user.getPhoneNumber().matches("\\d{10,12}") )
			throw new IllegalArgumentException("Invalid phone number format");
		
    	
    	// append new  User To Original User String
		String[] existing  = fu.readStringsFromFile(filePath);
		// Append to existing
        String[] updated = Arrays.copyOf(existing, existing.length + 1);
        updated[existing.length] = user.toString();
        
        fu.writeStringsToFile(updated, filePath);
	}
}