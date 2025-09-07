package my.edu.utar;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class calculateFareTest {
	calculateFare cf = new calculateFare();
	
	@Test
	@Parameters({
		"1.0,2.00",
		"5.0,2.00",
		"6.0,5.00",
		"10.0,5.00",
		"11.0,10.00",
		"15.0,10.00",
		"16.0,15.00",
		"20.0,15.00",
		"21.0,20.00",
		"30.0,20.00"})
	public void testGetBaseRateValidValue(double distance, double ER) {
		double AR = cf.getBaseFare(distance);
		
		assertEquals(ER,AR,0.01);
	}
	
	@Test(expected = IllegalArgumentException.class)
	@Parameters({"0","31.0"})
	public void testGetBaseRateInvalidValue(double distance) {
		cf.getBaseFare(distance);
	}
	
	@Test
	@Parameters({"2.0,1.2,2,2,8.8"})
	public void testCalTotalFare(double distance, double totaldiscount,int flatSurcharge, int qtt,double ER) {
		
		applyDiscountSurcharge adsMock = mock(applyDiscountSurcharge.class);
		routeInfo riMock = mock(routeInfo.class);
		
		doNothing().when(riMock).setDistance(anyString(), anyString());
		when(riMock.getDistance()).thenReturn(distance);
		when(adsMock.getTotalDiscount(anyString(),anyDouble(),any(LocalDateTime.class))).thenReturn(totaldiscount);
		when(adsMock.getFlatSurcharge(any(LocalDateTime.class))).thenReturn(flatSurcharge);
		
		calculateFare cf = new calculateFare(riMock,adsMock);
		
		double AR = cf.calTotalFare("Adult", qtt, LocalDateTime.of(2025, 9, 5, 10, 0), "KL Sentral", "Mid Valley");
		
		assertEquals(ER, AR, 0.001);
		
		
	}
	
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

	        data[i] = new Object[] { passengerType, qtt, dt, start, end, ER };
	    }

	    return data;
	}
	
	@Test
	@Parameters(method="getDataFortestValidCalTotalFare")
	public void testIntegrationForValidCalTotalFare(String passengerType, int qtt, LocalDateTime time, String start, String end,double ER) {
		routeInfo ri = new routeInfo();
	    applyDiscountSurcharge ads = new applyDiscountSurcharge();

	    calculateFare cf = new calculateFare(ri, ads);
	    
	    double AR = cf.calTotalFare(passengerType, qtt, time, start, end);

	    assertEquals(ER, AR, 0.001);
	}
	
	private Object[] parametersForTestIntegrationForInvalidCalTotalFare() {
	    return new Object[] {
	        new Object[] { "Lecturer", 2, LocalDateTime.parse("2025-09-03T22:30"), "Batu Kentonmen", "Rawang" },
	        new Object[] { "Child", -1, LocalDateTime.parse("2025-09-03T22:30"), "Batu Kentonmen", "Rawang" },
	        new Object[] { "Child", 2, LocalDateTime.parse("2025-09-03T22:30"), null, "Rawang" },
	        new Object[] { "Child", 2, LocalDateTime.parse("2025-09-03T22:30"), "Batu Kentonmen", "Sungai Long" },
	        new Object[] { "Child", 2, null, "Batu Kentonmen", "Rawang" }
	    };
	}
	@Test(expected = IllegalArgumentException.class)
	@Parameters
	public void testIntegrationForInvalidCalTotalFare(String passengerType, int qtt, LocalDateTime time, String start, String end) {
		routeInfo ri = new routeInfo();
	    applyDiscountSurcharge ads = new applyDiscountSurcharge();

	    calculateFare cf = new calculateFare(ri, ads);
	    
	    cf.calTotalFare(passengerType, qtt, time, start, end);
	}

}
