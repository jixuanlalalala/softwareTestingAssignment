package my.edu.utar;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class calculateFareIntegrationTest {

	/* *
	 * Reading test data from testdata_calTotalFareForIntegrationTest.txt for testing valid values
	 * */
	static ArrayList<String[]> linesreadsurcharge;
	
	static {
		Scanner input;
		linesreadsurcharge = new ArrayList<>();
		String filename = "testdata_calTotalFareForIntegrationTest.txt";
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
	
	// valid test
	
	private Object[][] getDataFortestValidCalTotalFare() {
	    Object[][] data = new Object[linesreadsurcharge.size()][];

	    for (int i = 0; i < linesreadsurcharge.size(); i++) {
	        String[] tokens = linesreadsurcharge.get(i);

	        String passengerType = tokens[0].trim();
	        int qtt = Integer.parseInt(tokens[1].trim());
	        LocalDateTime dt = LocalDateTime.parse(tokens[2].trim());
	        String start = tokens[3].trim();
	        String end = tokens[4].trim();
	        double ER = Double.parseDouble(tokens[5].trim());
	        
	        // Build passengerMap
	        Map<String, Integer> passengerMap = new HashMap<>();
	        passengerMap.put(passengerType, qtt);

	        data[i] = new Object[] { passengerMap, dt, start, end, ER };
	    }

	    return data;
	}
	
	@Test
	@Parameters(method="getDataFortestValidCalTotalFare")
	public void testIntegrationForValidCalTotalFare(Map<String, Integer> passengerMap, LocalDateTime time, String start, String end,double ER) {
		routeInfo ri = new routeInfo();
	    applyDiscountSurcharge ads = new applyDiscountSurcharge();

	    calculateFare cf = new calculateFare(ri, ads);
	    
	    double AR = cf.calTotalFare(passengerMap, time, start, end);

	    assertEquals(ER, AR, 0.001);
	}
	
	
	// Invalid test
	
	private Object[] parametersForTestIntegrationForInvalidCalTotalFare() {
	    return new Object[] {
	    		new Object[] { Map.of("Adult", -1), LocalDateTime.parse("2025-09-03T22:30"), "Batu Kentonmen", "Rawang" }
	    };
	}
	@Test (expected = IllegalArgumentException.class)
	@Parameters (method="parametersForTestIntegrationForInvalidCalTotalFare")
	public void testIntegrationForInvalidCalTotalFare(Map<String, Integer> passengerMap, LocalDateTime time, String start, String end) {
		routeInfo ri = new routeInfo();
	    applyDiscountSurcharge ads = new applyDiscountSurcharge();

	    calculateFare cf = new calculateFare(ri, ads);
	    
	    cf.calTotalFare(passengerMap, time, start, end);
	}
}
