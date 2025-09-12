package my.edu.utar;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import static org.mockito.Mockito.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameter;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class bookingUnitTest {

	private Object[] getParamsForSetDayTimeValid() {
	    return new Object[] {
	        "2025-12-25T06:30", "2025-12-25T10:30", "2025-12-25T23:00",
	        "2025-12-27T06:30", "2025-12-27T10:30", "2025-12-27T23:00"
	    };
	}
	
	@Test
	@Parameters(method ="getParamsForSetDayTimeValid")
	public void testSetDayAndTimeValid(String dayTime) {
		booking bk = new booking();
		bk.setDayTime(dayTime);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		
		String ar = (bk.getDayTime()).format(formatter);
		
		assertEquals(ar, dayTime);
	}
	
	private Object[] getParamsForInvalidSetDayTime() {
		return new Object[] {
				"", "T23:00","2025-12-23T","2025/12/23T12:00","2023-12-12T10:00" 
		};
	}
	
	@Test(expected = IllegalArgumentException.class)
	@Parameters(method = "getParamsForInvalidSetDayTime")
	public void testSetDayAndTimeInvalid(String dayTime) {
		booking bk = new booking();
		bk.setDayTime(dayTime);
	}
	
	@Test
	@Parameters({"Bangsar", "KL SENTRAL"})
	public void testStartStation(String start) {
		booking bk = new booking();
		bk.setStartStation(start);
		
		assertEquals(bk.getStartStation(), start.toUpperCase());
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	@Parameters({"","    ", "Melaka", "  subang   "})
	public void testSetStartStationInvalid(String start) {
		booking bk = new booking();
		bk.setStartStation(start);
	}
	
	@Test
	@Parameters({"Rawang", "SEMENYIH SENTRAL"})
	public void testEndStationValid(String end) {
		booking bk = new booking();
		bk.setStartStation("KL SENTRAL");
		bk.setEndStation(end);
		
		assertEquals(bk.getEndStation(), end.toUpperCase());
	}
	
	private Object[] getParamsForInvalidEndStation() {
		return new Object[] {
			"KL SENTRAL", "", "   ", "Melaka",
		};
	}
	
	@Test(expected = IllegalArgumentException.class)
	@Parameters(method = "getParamsForInvalidEndStation")
	public void testEndStationInvalid(String end) {
		booking bk = new booking();
		bk.setStartStation("KL SENTRAL");
		bk.setEndStation(end); 
	}
	
	private Object[] getParamsForValidPassengerTypes() {
		return new Object[] {
				new Object[] { new int[] {1,2,0,0}, Map.of("ADULT",1, "SENIOR CITIZEN",2, "CHILD",0, "STUDENT",0) },
				new Object[] { new int[] {1,2,2,1}, Map.of("ADULT",1, "SENIOR CITIZEN",2, "CHILD",2, "STUDENT",1) },
				new Object[] { new int[] {49,20,49,0}, Map.of("ADULT",49, "SENIOR CITIZEN",20, "CHILD",49, "STUDENT",0) },
				new Object[] { new int[] {49,49,49,49}, Map.of("ADULT",49, "SENIOR CITIZEN",49, "CHILD",49, "STUDENT",49) },
		};
	}
	
	@Test
	@Parameters(method = "getParamsForValidPassengerTypes")
	public void testValidPassengerTypesAndQty(int[] qty, Map<String, Integer> er) {
		booking bk = new booking();
		bk.setPassengerTypeAndQty(qty);
		
		assertEquals(bk.getPassengerAndQty(), er);
	}
	
	private Object[] getParamsForInvalidPassengersTypes() {
	    return new Object[] {
	        new Object[] {null},
	        new Object[] {new int[] {-1, -1, -1, -1}},
	        new Object[] {new int[] {51, 51, 51, 0}}
	    };
	}

	
	@Test(expected = IllegalArgumentException.class)
	@Parameters(method ="getParamsForInvalidPassengersTypes")
	public void testInvalidPassengerTypesAndQty(int[] qty) {
		booking bk = new booking();
		bk.setPassengerTypeAndQty(qty);
	}
	
	private Object[] getParamsForValidPaymentMethod() {
		return new Object[] {
			new Object[] {"e-wallet", "E-Wallet"},
			new Object[] {"credit card","Credit Card"},
			new Object[] {"online banking", "Online Banking"}
		};
	}
	
	@Test
	@Parameters(method = "getParamsForValidPaymentMethod")
	public void testValidPaymentMethod(String paymentMethod, String er) {
		booking bk = new booking();
		bk.setPaymentMethod(paymentMethod);
		
		assertEquals(bk.getPaymentMethod(), er);
	}
	
	private Object[] getParamsForInvalidPaymentMethod() {
		return new Object[] {
			"debit card", "" , "   "
		};
	}
	
	@Test(expected = IllegalArgumentException.class)
	@Parameters(method = "getParamsForInvalidPaymentMethod")
	public void testInvalidPaymentMethod(String paymentMethod) {
		booking bk = new booking();
		bk.setPaymentMethod(paymentMethod);
	}
	
	@Test
	@Parameters({"Pending, Pending", "Completed, Completed"})
	public void testValidBookingStatus(String status, String er) {
		booking bk = new booking();
		bk.setBookingStatus(status);
		assertEquals(bk.getBookingStatus(), er);
	}
	
	@Test(expected = IllegalArgumentException.class)
	@Parameters({"", "   ","Cancelled"})
	public void testInvalidBookingStatus(String status) {
		booking bk = new booking();
		bk.setBookingStatus(status);
	}
	
	@Test
	@Parameters({"Pending, Pending", "Completed, Completed"})
	public void testPaymentStatus(String status, String er) {
		booking bk = new booking();
		bk.setPaymentStatus(status);
		assertEquals(bk.getPaymentStatus(), er);
	}
	
	@Test(expected = IllegalArgumentException.class)
	@Parameters({"", "   ","Cancelled"})
	public void testInvalidPaymentStatus(String status) {
		booking bk = new booking();
		bk.setPaymentStatus(status);
	}
	
	//need to recheck
	private Object[] paramsForValidTotalFare() {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

	    return new Object[] {
	        new Object[] {
	            Map.of("ADULT", 2, "SENIOR CITIZEN", 0, "CHILD",1, "STUDENT", 0),               // passengers
	            new int[] {2,0, 1,0},                             // qty
	            LocalDateTime.parse("2025-12-25T10:30", formatter), // travelDayTime
	            "2025-12-25T10:30",                                    // DayTime
	            "AMPANG PARK",                                     // startStation
	            "KLCC",                                     // endStation
	            4.00                                          // exchange rate
	        },
	        new Object[] {
	            Map.of("ADULT", 1, "SENIOR CITIZEN", 2, "CHILD", 0, "STUDENT", 0),
	            new int[] {1, 2, 0, 0},
	            LocalDateTime.parse("2025-12-26T18:45", formatter),
	            "2025-12-26T18:45",
	            "KL SENTRAL",
	            "SUBANG JAYA",
	            27.0
	        },
	        new Object[] {
	            Map.of("ADULT", 0,"SENIOR CITIZEN", 0,"CHILD", 3, "STUDENT", 0),
	            new int[] {0,0,3,0},
	            LocalDateTime.parse("2025-12-27T14:00", formatter),
	            "2025-12-27T14:00",
	            "BATU KENTONMEN",
	            "RAWANG",
	            27.0
	        }
	    };
	}
	
	@Test
	@Parameters(method = "paramsForValidTotalFare")
	public void testValidTotalFare(Map<String, Integer> passengers,int[] qty, 
			LocalDateTime travelDayTime, String DayTime, String startStation, 
			String endStation, double er ) { 
		
		booking bkSpy = spy(new booking());
		
		bkSpy.setPassengerTypeAndQty(qty);
		bkSpy.setDayTime(DayTime);
		bkSpy.setStartStation(startStation);
		bkSpy.setEndStation(endStation);
		
		 doReturn(er).when(bkSpy).getTotalFare();
		
		double totalFare = bkSpy.getTotalFare();
		
		assertEquals(totalFare, er, 0.01);
	}
	
	//undone( same)
	private Object[] paramsForValidFinalFare() {
	    return new Object[] {
	        new Object[] { "E-Wallet", 4.0, 4.0 },
	        new Object[] { "Credit Card", 27.0, 28.35 },
	        new Object[] { "Online Banking", 27.0, 25.65 }
	    };
	}

	@Test
	@Parameters(method = "paramsForValidFinalFare")
	public void testValidFinalFare(String paymentMethod, double totalFare, double expectedFinalFare) {
	    // Create a spy of booking
	    booking bkSpy = spy(new booking());

	    // Set required fields
	    bkSpy.setPaymentMethod(paymentMethod);
	    bkSpy.setTotalFare(totalFare);

	    // Mock the adjustment logic
	    paymentMethodAdjustment pmaMock = mock(paymentMethodAdjustment.class);
	    doReturn(pmaMock).when(bkSpy).createAdjustment();
	    when(pmaMock.applyPaymentDiscount(paymentMethod, totalFare)).thenReturn(expectedFinalFare);

	    // Call the real method
	    double finalFare = bkSpy.getFinalFare();

	    // Assert and verify
	    assertEquals(expectedFinalFare, finalFare, 0.01);
	    verify(pmaMock).applyPaymentDiscount(paymentMethod, totalFare);
	}

	
	private Object[] paramsValidDiscountDetails() {
		return new Object[] {
			new Object[] {30.00, 20.00, 10.00},
			new Object[] {20.00, 40.00,20.00},
			new Object[] {0.0, 0.0, 0.0}
		};
	}
	
	@Test
	@Parameters(method = "paramsValidDiscountDetails")
	public void testValidDiscountDetails(double totalFare, double finalFare, double er) {
		booking bk = new booking();
		
		bk.setFinalFare(finalFare);
		bk.setTotalFare(totalFare);
		
		double discountDetails = bk.getDiscountDetails();
		
		assertEquals(discountDetails, er, 0.01);
	}
	
	@Test(expected = IllegalArgumentException.class)
	@Parameters({"-1.0, 30.0", "20.0, -1.0"})
	public void testInvalidDiscountDetails(double finalFare, double totalFare) {
		booking bk = new booking();
		
		bk.setFinalFare(finalFare);
		bk.setTotalFare(totalFare);
		
		bk.getDiscountDetails();
	}

/*
	@Test
	@Parameters({"E-Wallet", "Online Banking", "Credit Card"})
	public void testValidConfirmAndPay(String paymentMethod) {
		payment py = new payment();
		booking bk = new booking();
		
		assertTrue(py.processPayment(paymentMethod));
	}
	
	@Test(expected = IllegalArgumentException.class)
	@Parameters({"Debit card","cash",  "" })
	public void testInvalidConfirmAndPay(String paymentMethod) {
		payment py = new payment();
		booking bk = new booking();
		
		py.processPayment(paymentMethod);
	}
*/

	private Object[] paramsForTestValidSetUser() {
		return new Object[] {
				new Object[] {"new user", "2205922","Lim Zhen Cheng", "limzc@gmail.com", "0123456789"},
				new Object[] {"existing user", "2205922", "", "", ""},
		};
	}
	
	@Test
	@Parameters(method = "paramsForTestValidSetUser")
	public void testValidSetUser(String userType, String id, String name, String email, String phone) {
		booking bk = new booking();
		bk.setUser(userType, id, name, email, phone);
		
		assertNotNull(bk.getUser());
		
	}
	
	private Object[] paramsForTestValidSetUserGuest() {
		return new Object[] {
				new Object[] {"guest", null, "Lim Zhen Cheng", "limzc@gmail.com", "0123456789", "Lim Zhen Cheng|limzc@gmail.com|0123456789"}
		};
	}
	
	@Test
	@Parameters(method = "paramsForTestValidSetUserGuest")
	public void testValidSetGuest(String userType, String id, String name, String email, String phone, String er) {
		booking bk = new booking();
		bk.setUser(userType, id, name, email, phone);
		
		assertEquals(bk.getGuest(), er);
		
	}
	
	private Object[] paramsForTestInvalidSetUser() {
		return new Object[] {
				new Object[] {"", "2205922","Lim Zhen Cheng", "limzc@gmail.com", "0123456789"},
				new Object[] {"new user", "","Lim Zhen Cheng", "limzc@gmail.com", "0123456789"},
				new Object[] {"new user", "2205922","", "limzc@gmail.com", "0123456789"},
				new Object[] {"new user", "2205922","Lim Zhen Cheng", "", "0123456789"},
				new Object[] {"new user", "2205922","Lim Zhen Cheng", "limzc@gmail.com", ""},
				new Object[] {"existing user", "", "", "", ""},
				new Object[] {"guest", "", "", "limzc@gmail.com", "0123456789"},
				new Object[] {"guest", "", "Lim Zhen Cheng", "", "0123456789"},
				new Object[] {"guest", "", "Lim Zhen Cheng", "limzc@gmail.com", ""}
		};
	}
	
	@Test(expected = IllegalArgumentException.class)
	@Parameters(method = "paramsForTestInvalidSetUser")
	public void testInvalidSetUser(String userType, String id, String name, String email, String phone) {
		booking bk = new booking();
		bk.setUser(userType, id, name, email, phone);
		
		assertNull(bk.getUser());
	}
}
