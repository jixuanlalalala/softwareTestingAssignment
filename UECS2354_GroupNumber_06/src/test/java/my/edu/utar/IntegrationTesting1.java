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

	private Object[] getDataToTestUser() {

		return new Object[] {
			// new user, 3 senior citizens, from Bangsar to KL Sentral, using Credit Card
			new Object[] { "new user", "2", "Jane", "jj@jj.cc", "01234567890", "Jane", "2025-09-30T06:45",
					"Bangsar", "KL Sentral", new int[] { 0, 3, 0, 0 }, "Credit Card", 3.6, 3.78 },
			
			// existing user, 2 adults, from Bangsar to KL Sentral, using E-wallet
			new Object[] { "existing user", "2", "", "", "", "Jane", "2025-09-30T06:45", 
					"Bangsar", "KL Sentral", new int[] { 2, 0, 0, 0 }, "E-wallet", 4.8, 4.8 }
		};
	}

	@Test
	@Parameters(method = "getDataToTestUser")
	public void testExistingUserCreateBooking(String userType, String id, String name, String email, String phoneNumber,
			String expectedName, String DayTime, String start, String end, int[] qty, String payment,
			double expectedTotalFare, double expectedFareAfterPaymentDiscount) {

		booking bk = new booking();

		bk.setUser(userType, id, name, email, phoneNumber);
		String actualUserName = bk.getUser().getName();
		// testing get data from readUser.java
		assertEquals(expectedName, actualUserName);

		bk.setDayTime(DayTime);
		bk.setStartStation(start);
		bk.setEndStation(end);
		bk.setPassengerTypeAndQty(qty);
		bk.setPaymentMethod(payment);

		double actualTotalFare = bk.getTotalFare();
		//testing correct after applying discount/surcharge
		assertEquals(expectedTotalFare,actualTotalFare,0.01); 
		
		
		double actualFareAfterPaymentDiscount = bk.getFinalFare();
		//testing correct fare after applying payment method discount surcharge
		assertEquals(expectedFareAfterPaymentDiscount, actualFareAfterPaymentDiscount, 0.01);

		bk.confirmAndPay();
		String actualPaymentStatus = bk.getPaymentStatus();
		String expectedPaymentStatus = "Completed";
		// testing can proceed payment if it is a valid payment method
		assertEquals(expectedPaymentStatus, actualPaymentStatus);

		boolean actualEmailReceipt = bk.printReceipt();
		// testing can print receipt
		assertTrue(actualEmailReceipt);
	}

	
	
	
	
	private Object[] getDataToTestGuest() {

		return new Object[] {
			// guest, 1 student, from Bangsar to KL Sentral, using Online Banking
			new Object[] {"guest", "", "Joe", "joe@gg.gg", "014785236985", "Joe", "2025-09-30T06:45", 
					"Bangsar", "KL Sentral", new int[] { 0, 0, 0, 1 }, "Online Banking", 1.68, 1.60 }
		};
	}

	@Test
	@Parameters(method = "getDataToTestGuest")
	public void testGuestCreateBooking(String userType, String id, String name, String email, String phoneNumber,
			String expectedName, String DayTime, String start, String end, int[] qty, String payment,
			double expectedTotalFare, double expectedFareAfterPaymentDiscount) {
		booking bk = new booking();

		bk.setUser(userType, id, name, email, phoneNumber);
		String actualUserName = bk.getGuestName();
		// testing guest can create booking
		assertEquals(expectedName, actualUserName);

		bk.setDayTime(DayTime);
		bk.setStartStation(start);
		bk.setEndStation(end);
		bk.setPassengerTypeAndQty(qty);
		bk.setPaymentMethod(payment);

		double actualTotalFare = bk.getTotalFare();
		// testing correct after applying discount/surcharge
		assertEquals(expectedTotalFare, actualTotalFare, 0.01);

		double actualFareAfterPaymentDiscount = bk.getFinalFare();
		// testing correct fare after applying payment method discount surcharge
		assertEquals(expectedFareAfterPaymentDiscount, actualFareAfterPaymentDiscount, 0.01);

		bk.confirmAndPay();
		String actualPaymentStatus = bk.getPaymentStatus();
		String expectedPaymentStatus = "Completed";
		// testing can proceed payment if it is a valid payment method
		assertEquals(expectedPaymentStatus, actualPaymentStatus);

		boolean actualEmailReceipt = bk.printReceipt();
		// testing can print receipt
		assertTrue(actualEmailReceipt);
	}

}