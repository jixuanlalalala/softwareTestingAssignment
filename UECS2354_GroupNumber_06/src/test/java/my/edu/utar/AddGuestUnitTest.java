package my.edu.utar;


import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class AddGuestUnitTest {

	String filePath = "guest.txt";
	
	private Object[] getParamsForTestAddGuestValid() {
		
		return new Object[]{
            new Object[]{
                "John", "john@mail.com", "0123456789",
                new String[]{},
                new String[]{"John|john@mail.com|0123456789"}
            },
            new Object[]{
                "Bob", "bob@mail.com", "0222222222",
                new String[]{"Alice|alice@mail.com|0111111111"},
                new String[]{
                    "Alice|alice@mail.com|0111111111",
                    "Bob|bob@mail.com|0222222222"
                }
            }
        };		
	}
	
	@Test
	@Parameters(method ="getParamsForTestAddGuestValid")
    public void testAddGuestValid(String name, String email, String phoneNumber, String[] existing, String[] ER) {
        // Mock FileUtil
		FileUtilities mockFu = mock(FileUtilities.class);
        

        // Stub read to return empty array
        when(mockFu.readStringsFromFile(filePath)).thenReturn(existing);

        // Create AddUser with mock
        AddGuest agMock = new AddGuest(mockFu);

        // Call addUser
        agMock.addGuest(name, email, phoneNumber, filePath);

        // Verify writeStringsToFile called with expected array
        verify(mockFu).writeStringsToFile(ER, filePath);
    }
	
	
	
	private Object[] getParamsForTestAddGuestInvalid() {
		
		return new Object[]{
            new Object[]{null, "john@mail.com", "0123456789"},   // null name
            new Object[]{"", "john@mail.com", "0123456789"},     // empty name
            new Object[]{"John", null, "0123456789"},            // null email
            new Object[]{"John", "", "0123456789"},              // empty email
            new Object[]{"John", "johnmail.com", "0123456789"},  // no @ in email
            new Object[]{"John", "john@mail.com", null},         // null phone
            new Object[]{"John", "john@mail.com", ""},           // empty phone
            new Object[]{"John", "john@mail.com", "123"},        // too short
            new Object[]{"John", "john@mail.com", "01234567890123"} // too long
        };
	}
	
	
	@Test (expected=IllegalArgumentException.class)
	@Parameters(method = "getParamsForTestAddGuestInvalid")
    public void testAddGuestInvalid(String name, String email, String phoneNumber) {
        // Mock FileUtil
		FileUtilities mockFu = mock(FileUtilities.class);
        

        // Stub read to return empty array
        when(mockFu.readStringsFromFile(filePath)).thenReturn(new String[]{});

        // Create AddUser with mock
        AddGuest agMock = new AddGuest(mockFu);

        // Call addUser
        agMock.addGuest(name, email, phoneNumber, filePath);

    }

}
