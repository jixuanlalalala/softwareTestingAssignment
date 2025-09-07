package my.edu.utar;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

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

}
