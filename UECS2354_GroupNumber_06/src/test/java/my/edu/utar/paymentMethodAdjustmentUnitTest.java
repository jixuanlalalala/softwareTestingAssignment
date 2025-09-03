package my.edu.utar;

import static org.junit.Assert.*;
import junitparams.JUnitParamsRunner;
import static org.mockito.Mockito.*;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Ignore;


@RunWith(JUnitParamsRunner.class)
public class paymentMethodAdjustmentUnitTest {
	
	paymentMethodAdjustment pma = new paymentMethodAdjustment();

	@Test
	@Parameters({
		"e-Wallet,0,0",
		"e-Wallet,15,15",
		"Credit Card,0,0",
		"Credit Card,15,15.75",
		"Online Banking,0,0",
		"Online Banking,15,14.25"
	})
	public void testValidApplyPaymentDiscount(String paymentType, double fare, double afterFare) {
		double AR = pma.applyPaymentDiscount(paymentType, fare);
		double ER = afterFare;
		assertEquals(ER,AR,0.001);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidPaymentMethodApplyPaymentDiscount() {
		pma.applyPaymentDiscount("OTHER", 15);
	}

}
