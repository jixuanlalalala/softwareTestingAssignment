package my.edu.utar;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class calculateFareTest {
	
	applyDiscountSurcharge adsMock;
    routeInfo riMock;
    calculateFare cf;
	
	@Before
	public void setupForAllTests() {
		adsMock = mock(applyDiscountSurcharge.class);
		riMock = mock(routeInfo.class);	
		cf = new calculateFare(riMock, adsMock);
	}
	

	
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
	
	
	
	// ---------- test valid calculate Total Fare ---------- 
	
	private Object[] paramsForTestCalTotalFareValid() {
	    return new Object[] {
	        new Object[] { Map.of("Adult", 2), LocalDateTime.parse("2025-09-03T06:45"), "Bangsar", "KL Sentral", 2.0, 1.20, 0, 4.80 },
	        new Object[] { Map.of("Adult", 2), LocalDateTime.parse("2025-09-03T18:30"), "Subang Jaya", "Shah Alam", 8.0, 1.20, 0, 12.00 },
	        new Object[] { Map.of("Adult", 2), LocalDateTime.parse("2025-09-03T22:45"), "KL Sentral", "Kepong Sentral", 13.0, 1.00, 2, 24.00 },
	        new Object[] { Map.of("Adult", 2), LocalDateTime.parse("2025-09-03T14:00"), "KL Sentral", "Subang Jaya",18.0, 1.00, 0, 30.00 },
	        new Object[] { Map.of("Adult", 2), LocalDateTime.parse("2025-09-06T14:00"), "Batu Kentonmen", "Rawang",25.0, 0.90, 0, 36.00 },
	        
	        new Object[] { Map.of("Senior", 3), LocalDateTime.parse("2025-09-03T06:45"), "Bangsar", "KL Sentral",3.0, 0.60, 0, 3.60 },
	        new Object[] { Map.of("Senior", 3), LocalDateTime.parse("2025-09-03T18:30"), "Subang Jaya", "Shah Alam",8.0, 0.60, 0, 9.00 },
	        new Object[] { Map.of("Senior", 3), LocalDateTime.parse("2025-09-03T22:45"), "KL Sentral", "Kepong Sentral",13.0, 0.50, 2, 21.00 },
	        new Object[] { Map.of("Senior", 3), LocalDateTime.parse("2025-09-03T22:45"), "KL Sentral", "Subang Jaya",18.0, 0.50, 0, 22.50 },
	        new Object[] { Map.of("Senior", 3), LocalDateTime.parse("2025-09-06T14:00"), "Batu Kentonmen", "Rawang",25.0, 0.45, 0, 27.00 },
	        
	        new Object[] { Map.of("Student", 1), LocalDateTime.parse("2025-09-06T06:45"), "Bangsar", "KL Sentral",3.0, 0.84, 0, 1.68 },
	        new Object[] { Map.of("Student", 1), LocalDateTime.parse("2025-09-03T18:30"), "Subang Jaya", "Shah Alam",8.0, 0.84, 0, 4.20 },
	        new Object[] { Map.of("Student", 1), LocalDateTime.parse("2025-09-04T22:45"), "KL Sentral", "Kepong Sentral",13.0, 0.70, 2, 9.00 },
	        new Object[] { Map.of("Student", 1), LocalDateTime.parse("2025-09-04T14:00"), "KL Sentral", "Subang Jaya",18.0, 0.70, 0, 10.50 },
	        new Object[] { Map.of("Student", 1), LocalDateTime.parse("2025-09-06T22:30"), "Batu Kentonmen", "Rawang", 25.0, 0.63, 0, 12.60 },
	        
	        new Object[] { Map.of("Child", 2), LocalDateTime.parse("2025-09-03T07:30"), "Bangsar", "KL Sentral", 3.0, 0.00, 0, 0.00 },
	        new Object[] { Map.of("Child", 2), LocalDateTime.parse("2025-09-03T18:30"), "Subang Jaya", "Shah Alam", 8.0, 0.60, 0, 6.00 },
	        new Object[] { Map.of("Child", 2), LocalDateTime.parse("2025-09-03T22:45"), "KL Sentral", "Kepong Sentral",13.0, 0.50, 2, 14.00 },
	        new Object[] { Map.of("Child", 2), LocalDateTime.parse("2025-09-03T14:00"), "KL Sentral", "Subang Jaya", 18.0, 0.50, 0, 15.00 },
	        new Object[] { Map.of("Child", 2), LocalDateTime.parse("2025-09-06T11:45"), "Batu Kentonmen", "Rawang", 25.0, 0.45, 0, 18.00 },
	    };
	}
	
	@Test
	@Parameters(method = "paramsForTestCalTotalFareValid")
    public void testCalTotalFareValid(Map<String, Integer> passengerMap, LocalDateTime time, String startStation, String endStation, 
    									double distance,double discountMultiplier, int flatSurcharge, double expectedFare) {
       
        // Mock behavior for routeInfo and applyDiscountSurcharge
        doNothing().when(riMock).setDistance(anyString(), anyString());
        when(riMock.getDistance()).thenReturn(distance);
        when(adsMock.getTotalDiscount(anyString(), anyDouble(), any(LocalDateTime.class))).thenReturn(discountMultiplier);
        when(adsMock.getFlatSurcharge(any(LocalDateTime.class))).thenReturn(flatSurcharge);

        // Calculate the actual fare
        double actualFare = cf.calTotalFare(passengerMap, time, startStation, endStation);

        assertEquals(expectedFare, actualFare, 0.001);
    }
	
	
	// ---------- test invalid quantity ---------- 
	
	private Object[] paramsForTestCalTotalFareInvalidQuantity() {
		return new Object[] {
				new Object[] { Map.of("Adult", -1), LocalDateTime.parse("2025-09-03T22:30"), "Batu Kentonmen", "Rawang", 3.0, 1.20, 0},
		};
	}
	@Test (expected = IllegalArgumentException.class)
	@Parameters(method = "paramsForTestCalTotalFareInvalidQuantity")
    public void testCalTotalFareInvalidQuantity(Map<String, Integer> passengerMap, LocalDateTime time, String startStation, String endStation, 
    									double distance,double discountMultiplier, int flatSurcharge) {
        
        doNothing().when(riMock).setDistance(anyString(), anyString());
        when(riMock.getDistance()).thenReturn(distance);
        when(adsMock.getTotalDiscount(anyString(), anyDouble(), any(LocalDateTime.class))).thenReturn(discountMultiplier);
        when(adsMock.getFlatSurcharge(any(LocalDateTime.class))).thenReturn(flatSurcharge);
        
        cf.calTotalFare(passengerMap, time, startStation, endStation);
    }
}