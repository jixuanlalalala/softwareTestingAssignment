package my.edu.utar;

import java.io.*;
import java.util.Scanner;

/**
 * ReadUser class for the Travel Ticket Booking system.
 * Reads user details from a text file based on user ID.
 */
public class ReadUser {
	
	FileUtilities fu;
    
    public ReadUser(FileUtilities fu) {
		this.fu = fu;
	}
    
    public ReadUser() {
    	fu = new FileUtilities();
    }
    
    
    public User readUser(String userID, String filePath) {
    	
    	if (userID == null || userID.trim().isEmpty())
			throw new IllegalArgumentException("userID cannot be null !");
    	
    	String[] existingUser = fu.readStringsFromFile(filePath);
    	
    	// get user by id
		User user = null;
    	for (String s : existingUser) {
    		String[] parts = s.split("\\|");
			if (parts.length == 4 && parts[0].equals(userID)) {
				user = new User();
	            user.setId(parts[0]);
	            user.setName(parts[1]);
	            user.setEmail(parts[2]);
	            user.setPhoneNumber(parts[3]);
			}
    	}
    	return user;
    }
    	

}
