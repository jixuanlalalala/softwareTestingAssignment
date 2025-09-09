package my.edu.utar;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.time.LocalDateTime;

import static org.junit.Assert.*;
import junitparams.JUnitParamsRunner;
import static org.mockito.Mockito.*;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Ignore;

@RunWith(JUnitParamsRunner.class)

public class applyDiscountSurchargeUnitTest {
	
	applyDiscountSurcharge ads = new applyDiscountSurcharge();

	/**
	 * Testing valid test data for getPassengerTypeDiscount()
	 * @param type include Adult, Senior Citizen, Student, Child.
	 * @param distance
	 */
	@Test
	@Parameters({
		"Adult,3,1.0",
		"Adult,15,1.0",
		"Senior Citizen,3,0.5",
		"Senior Citizen,15,0.5",
		"Student,3,0.7",
		"Student,15,0.7",
		"Child,3,0.0",
		"Child,15,0.5"
	})
	public void testValidGetPassengerTypeDiscount(String type, double distance, double result) {
		double AR = ads.getPassengerTypeDiscount(type,distance);
		double ER = result;
		assertEquals(ER,AR,0.01);
	}

	/**
	 * Testing invalid test data for getPassengerTypeDiscount()
	 * @param type - other than valid type will throw exception
	 * @param distance - negative value will throw exception
	 */
	@Test(expected=IllegalArgumentException.class)
	@Parameters({
		"Other,10",
		"Adult,-5",
		"Senior Citizen,-5",
		"Student,-5",
		"Child,-5"
	})
	public void testInvalidGetPassengerTypeDiscount(String type, double distance) {
		ads.getPassengerTypeDiscount(type, distance);
	}
	
	
	
	/* *
	 * Reading test data from testdata_timeDateDiscount.txt for testing valid values
	 * */
	static ArrayList<String[]> linesreaddiscount;
	
	static {
		Scanner input;
		linesreaddiscount = new ArrayList<>();
		String filename = "testdata_timeDateDiscount.txt";
		input = null;
		
		try {
			input = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found " + filename);
			System.exit(0);
		}
		
		while (input.hasNextLine()) {
			String singleline = input.nextLine();
			String[] tokens = singleline.split(",");
			linesreaddiscount.add(tokens);
		}
		
	}
	
	private Object[] getDateFortestValidGetTimeDateDiscount() {
		Object[] data = new Object[linesreaddiscount.size()][];
		for (int i =0; i<linesreaddiscount.size(); i++) {
			String[] tokens = linesreaddiscount.get(i);
			
			LocalDateTime dt = LocalDateTime.parse(tokens[0].trim());
			double discount = Double.parseDouble(tokens[1].trim());
			
			data[i] = new Object[] {dt,discount};
		}
		return data;
	}
	
	@Test
	@Parameters(method="getDateFortestValidGetTimeDateDiscount")
	public void testValidGetTimeDateDiscount(LocalDateTime ticketDateTime, double discount) {
		double AR = ads.getTimeDateDiscount(ticketDateTime);
		double ER = discount;
		assertEquals(ER,AR,0.01);
	}
	
	//Test what if passing null value into getTimeDateDiscount
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidGetTimeDateDiscount() {
		ads.getTimeDateDiscount(null);
	}
	
	
	
	/* *
	 * Reading test data from testdata_nightTimeFlatSurcharge.txt for testing valid values
	 * */
	static ArrayList<String[]> linesreadsurcharge;
	
	static {
		Scanner input;
		linesreadsurcharge = new ArrayList<>();
		String filename = "testdata_nightTimeFlatSurcharge.txt";
		input = null;
		
		try {
			input = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found " + filename);
			System.exit(0);
		}
		
		while (input.hasNextLine()) {
			String singleline = input.nextLine();
			String[] tokens = singleline.split(",");
			linesreadsurcharge.add(tokens);
		}
		
	}
	
	private Object[][] getDateFortestValidGetFlatSurcharge() {
		Object[][] data = new Object[linesreadsurcharge.size()][];
		for (int i =0; i<linesreadsurcharge.size(); i++) {
			String[] tokens = linesreadsurcharge.get(i);
			
			LocalDateTime dt = LocalDateTime.parse(tokens[0].trim());
			int surcharge = Integer.parseInt(tokens[1].trim());
			
			data[i] = new Object[] {dt, surcharge};
		}
		return data;
	}
	
	@Test
	@Parameters(method="getDateFortestValidGetFlatSurcharge")
	public void testValidGetFlatSurcharge(LocalDateTime ticketDateTime, int surcharge) {
		int AR = ads.getFlatSurcharge(ticketDateTime);
		int ER = surcharge;
		assertEquals(ER,AR,0.01);
	}
	
	//Test what if passing null value into getTimeDateDiscount
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidGetFlatSurcharge() {
		ads.getFlatSurcharge(null);
	}
	
	/* *
	 * Reading test data from testdata_getTotalDiscount.txt for testing valid values
	 * */
	static ArrayList<String[]> linesreadtotaldiscount;
	
	static {
		Scanner input;
		linesreadtotaldiscount = new ArrayList<>();
		String filename = "testdata_getTotalDiscount.txt";
		input = null;
		
		try {
			input = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found " + filename);
			System.exit(0);
		}
		
		while (input.hasNextLine()) {
			String singleline = input.nextLine();
			String[] tokens = singleline.split(",");
			linesreadtotaldiscount.add(tokens);
		}
		
	}
	
	private Object[] getDateFortestGetTotalDiscount() {
		Object [] data = new Object[linesreadtotaldiscount.size()][];
		for (int i =0; i<linesreadtotaldiscount.size(); i++) {
			String[] tokens = linesreadtotaldiscount.get(i);
			
			double distance = Double.parseDouble(tokens[1].trim());
			LocalDateTime dt = LocalDateTime.parse(tokens[2].trim());
			double totaldiscount = Double.parseDouble(tokens[3].trim());
			
			data[i] = new Object[] {tokens[0].trim(), distance, dt, totaldiscount};
		}
		return data;
	}
	
	@Test
	@Parameters(method="getDateFortestGetTotalDiscount")
	public void testGetTotalDiscount(String passengerType, double distance, LocalDateTime ticketDateTime, double totaldiscount) {
		double AR = ads.getTotalDiscount(passengerType, distance, ticketDateTime);
		double ER = totaldiscount;
		assertEquals(ER,AR,0.01);
	}
	
	
	
	
	
	
	
	

}
