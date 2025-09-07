package my.edu.utar;


import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class AddGuestUnitTest {

	String filePath = "test_guest.txt";
	
	FileUtilities mockFu = mock(FileUtilities.class);
	
	private Object[] getParamsForTestAddGuestValid() {
		
		return new Object[]{
	        // Test Case 1: Standard valid guest with 10-digit phone
	        new Object[]{
	            "John Doe",                         // name
	            "john.doe@email.com",               // email
	            "1234567890",                       // phoneNumber (10 digits)
	            new String[]{"John Doe|john.doe@email.com|1234567890"} // Expected Result (ER)
	        },
	        // Test Case 3: Valid guest with 12-digit phone
	        new Object[]{
	            "Alice Chan",                       // name
	            "alice@company.com",                // email
	            "123456789012",                     // phoneNumber (12 digits)
	            new String[]{"Alice Chan|alice@company.com|123456789012"} // ER
	        }
	        // You can add more valid test cases here
	    };	
	}
	
	@Test
	@Parameters(method ="getParamsForTestAddGuestValid")
    public void testAddGuestValid(String name, String email, String phoneNumber, String[] ER) {
       
		// Stub read to return empty array
        when(mockFu.readStringsFromFile(anyString())).thenReturn(new String[]{});

        AddGuest agMock = new AddGuest(mockFu);
        agMock.addGuest(name, email, phoneNumber, filePath);

        // Verify writeStringsToFile called with expected array
        verify(mockFu).writeStringsToFile(ER, filePath);
    }
	
	
	
	private Object[] getParamsForTestAddGuestInvalid() {
		
		return new Object[]{
            new Object[]{null, "john@mail.com", "12345678901"},   // null name
            new Object[]{"", "john@mail.com", "12345678901"},     // empty name
            new Object[]{"John", null, "12345678901"},            // null email
            new Object[]{"John", "", "12345678901"},              // empty email
            new Object[]{"John", "invalid-email", "12345678901"},              // email not contain @
            new Object[]{"John", "johnmail.com", "12345678901"},  // no @ in email
            new Object[]{"John", "john@mail.com", null},         // null phone
            new Object[]{"John", "john@mail.com", "123-456-789"},         // phone non digit
            new Object[]{"John", "john@mail.com", ""},           // empty phone
            new Object[]{"John", "john@mail.com", "123456789"},        // 9-digit phone
            new Object[]{"John", "john@mail.com", "1234567890123"} // 13-digit phone
        };
	}
	
	
	@Test (expected=IllegalArgumentException.class)
	@Parameters(method = "getParamsForTestAddGuestInvalid")
    public void testAddGuestInvalid(String name, String email, String phoneNumber) {
        
		// Stub read to return empty array
        when(mockFu.readStringsFromFile(anyString())).thenReturn(new String[]{});

        AddGuest agMock = new AddGuest(mockFu);
        agMock.addGuest(name, email, phoneNumber, filePath);

    }

}
