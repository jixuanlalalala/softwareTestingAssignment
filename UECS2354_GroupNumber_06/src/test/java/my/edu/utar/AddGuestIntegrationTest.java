package my.edu.utar;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class AddGuestIntegrationTest {

	String filePath = "test_guest.txt";
	
	FileUtilities fu = new FileUtilities();
	AddGuest ag = new AddGuest(fu);
	
	
	
	private Object[] getParamsForTestAddGuestValid() {
		
		return new Object[]{
	        
	        new Object[]{
	            "John Doe",                         // name
	            "john.doe@email.com",               // email
	            "1234567890",                       // phoneNumber (10 digits)
	            "John Doe|john.doe@email.com|1234567890" // Expected Result (ER)
	        },
	        new Object[]{
	            "Alice Chan",                       // name
	            "alice@company.com",                // email
	            "123456789012",                     // phoneNumber (12 digits)
	            "Alice Chan|alice@company.com|123456789012" // ER
	        }
	    };	
	}
	
	@Test
	@Parameters(method ="getParamsForTestAddGuestValid")
    public void testAddGuestValid(String name, String email, String phoneNumber, String ER) {
       
		ag.addGuest(name, email, phoneNumber, filePath);

        String[] result= fu.readStringsFromFile(filePath);
        assertEquals(ER, result[result.length-1]);
    }
	
	
	
	private Object[] getParamsForTestAddGuestInvalid() {
		
		return new Object[]{
            new Object[]{null, "john@mail.com", "12345678901"},    // null name
            new Object[]{"", "john@mail.com", "12345678901"},      // empty name
            new Object[]{"John", null, "12345678901"},             // null email
            new Object[]{"John", "", "12345678901"},               // empty email
            new Object[]{"John", "invalid-email", "12345678901"},  // invalid email format
            new Object[]{"John", "john@mail.com", null},           // null phone
            new Object[]{"John", "john@mail.com", "123-456-789"},  // phone non digit
            new Object[]{"John", "john@mail.com", ""},             // empty phone
            new Object[]{"John", "john@mail.com", "123456789"},    // 9-digit phone
            new Object[]{"John", "john@mail.com", "1234567890123"} // 13-digit phone
        };
	}
	
	
	@Test (expected=IllegalArgumentException.class)
	@Parameters(method = "getParamsForTestAddGuestInvalid")
    public void testAddGuestInvalid(String name, String email, String phoneNumber) {
		
		ag.addGuest(name, email, phoneNumber, filePath);

    }

}
