package my.edu.utar;

import static org.junit.Assert.*;
import junitparams.JUnitParamsRunner;
import static org.mockito.Mockito.*;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Ignore;

@RunWith(JUnitParamsRunner.class)

public class paymentUnitTest {

	
	@Test
	@Parameters({
		"E-Wallet",
		"Credit Card",
		"Online Banking",
	})
	public void testSuccessfulProcessPayment(String paymentMethod) {
		payment py = new payment();
		boolean result = py.processPayment(paymentMethod);
		
		assertTrue(result);
	}
	
	@Test
	public void testUnsuccessfulProcessPayment() {
		payment py = new payment();
		boolean result = py.processPayment("Cash");
		
		assertFalse(result);
	}

}
