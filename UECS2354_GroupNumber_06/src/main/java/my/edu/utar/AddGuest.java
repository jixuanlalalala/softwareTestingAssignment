package my.edu.utar;

import java.io.*;
import java.util.ArrayList;

/**
 * AddGuest class for the Travel Ticket Booking system.
 * Adds guest details (name, email, and phone number) to a text file.
 */
public class AddGuest {
	
	FileUtilities fu;
	ArrayList<String> guestRecords;
	
	public AddGuest(FileUtilities fu) {
		this.fu = fu;
		guestRecords = new ArrayList<String>();
	}
    
    public AddGuest() {
    	fu = new FileUtilities();
    	guestRecords = new ArrayList<String>();
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
		
		if (!(phoneNumber.length() >= 10 && phoneNumber.length() <= 12)) {
			throw new IllegalArgumentException("Invalid Phone Number length !");
		}
		
		String stringToWrite = name + "|" + email + "|" + phoneNumber;
		
		// append new  User To Original User String
		String[] stringRead = fu.readStringsFromFile(filePath);
		for (String s : stringRead) {
			guestRecords.add(s);
		}
		guestRecords.add(stringToWrite);
		
		// Arraylist to String
		String[] strArray = new String[guestRecords.size()];
		strArray = guestRecords.toArray(strArray);
		
		
		fu.writeStringsToFile(strArray, filePath);
	}	
}
