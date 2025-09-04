
package my.edu.utar;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * AddNewUser class for the Travel Ticket Booking system.
 * Adds a new user with name, ID, email, and phone number to a text file.
 */
public class AddNewUser {

    FileUtilities fu;
    ArrayList<String> userRecords;
    
    public AddNewUser(FileUtilities fu) {
		this.fu = fu;
		userRecords = new ArrayList<String>();
	}
    
    public AddNewUser() {
    	fu = new FileUtilities();
    	userRecords = new ArrayList<String>();
    }

    public void addUser(User user, String filePath) {
    	if (user == null) {
    		throw new IllegalArgumentException("Error ! Invalid User !");
        }
    	if (user.getName() == null || user.getName().trim() =="" || user.getId() == null || 
                user.getEmail() == null || user.getPhoneNumber() == null) {
    		throw new IllegalArgumentException("Error ! User info not complete !");
    	}
    	
//    	// append new  User To Original User String
		String[] existing  = fu.readStringsFromFile(filePath);
		// Append to existing
        String[] updated = Arrays.copyOf(existing, existing.length + 1);
        updated[existing.length] = user.toString();
        fu.writeStringsToFile(updated, filePath);
	
	}
}