package my.edu.utar;

import static org.junit.Assert.*;
import junitparams.JUnitParamsRunner;
import static org.mockito.Mockito.*;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Ignore;

@RunWith(JUnitParamsRunner.class)

public class applyDiscountSurchargeTest {
	
	applyDiscountSurcharge ads = new applyDiscountSurcharge();

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

}
