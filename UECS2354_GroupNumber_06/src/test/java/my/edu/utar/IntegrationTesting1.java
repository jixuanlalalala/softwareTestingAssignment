package my.edu.utar;

import static org.junit.Assert.*;
import junitparams.JUnitParamsRunner;
import static org.mockito.Mockito.*;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.Ignore;


@RunWith(JUnitParamsRunner.class)

public class IntegrationTesting1 {

	
	@Test
	public void testExistingUserCreateBooking() {
		
		booking bk = new booking();
		
		bk.setUser("existing user","1","","","");
		String actualUserName = bk.getUser().getName();
		//testing get data from readUser.java
		assertEquals("John", actualUserName); 
		
		
		bk.setDayTime("2025-09-12T06:45");
		bk.setStartStation("Bangsar");
		bk.setEndStation("KL Sentral");
		bk.setPassengerTypeAndQty(new int[] {2,0,0,0});
		bk.setPaymentMethod("E-wallet");
		
		double actualTotalFare = bk.getTotalFare();
		double expectedTotalFare = 4.8;
		//testing correct after applying discount/surcharge
		assertEquals(expectedTotalFare,actualTotalFare,0.01); 
		
		
		double actualFareAfterPaymentDiscount = bk.getFinalFare();
		double expectedFareAfterPaymentDiscount = 4.8;
		//testing correct fare after applying payment method discount surcharge
		assertEquals(expectedFareAfterPaymentDiscount, actualFareAfterPaymentDiscount, 0.01);
		
		
		boolean actualPaymentResult = bk.confirmAndPay();
		//testing can proceed payment if it is a valid payment method
		assertTrue(actualPaymentResult);
		
		boolean actualEmailReceipt = bk.printReceipt();
		//testing can print receipt
		assertTrue(actualEmailReceipt);
		
	
	}

}
