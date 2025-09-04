package my.edu.utar;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class ReadUserTest {

	String filePath = "user.txt";
	
	
	// Valid Test
	
	private Object[] getParamsForTestReadUserValid() {
        return new Object[]{
            new Object[]{
                "U001",
                new String[]{"U001|John|john@mail.com|0123456789"},
                new User("U001", "John", "john@mail.com", "0123456789")
            },
            new Object[]{
                "U002",
                new String[]{
                    "U001|John|john@mail.com|0123456789",
                    "U002|Alice|alice@mail.com|0111111111"
                },
                new User("U002", "Alice", "alice@mail.com", "0111111111")
            }
        };
    }
	
	@Test
	@Parameters(method = "getParamsForTestReadUserValid")
    public void testReadUserValid(String userID, String[] existing, User ER){
        // Mock FileUtil
		FileUtilities mockFu = mock(FileUtilities.class);

        // Stub read to return empty array
        when(mockFu.readStringsFromFile(filePath)).thenReturn(existing);

        // Create AddUser with mock
        ReadUser ruMock = new ReadUser(mockFu);

        // Call addUser
        User AR = ruMock.readUser(userID, filePath);
        
//        assertEquals(AR,ER);
        assertEquals(ER.getId(), AR.getId());
        assertEquals(ER.getName(), AR.getName());
        assertEquals(ER.getEmail(), AR.getEmail());
        assertEquals(ER.getPhoneNumber(), AR.getPhoneNumber());

    }
	
	
	private Object[] getParamsForTestReadUserValidNull() {
        return new Object[]{
            new Object[]{
                "U999", // not found
                new String[]{
                    "U001|John|john@mail.com|0123456789"
                }
//                null // should return null if not found
            }
        };
    }
	@Test
	@Parameters(method = "getParamsForTestReadUserValidNull")
    public void testReadUserValidNull(String userID, String[] existing){
        // Mock FileUtil
		FileUtilities mockFu = mock(FileUtilities.class);

        // Stub read to return empty array
        when(mockFu.readStringsFromFile(anyString())).thenReturn(existing);

        // Create AddUser with mock
        ReadUser ruMock = new ReadUser(mockFu);

        // Call addUser
        User AR = ruMock.readUser(userID, filePath);
        
        assertNull(AR);

    }
	
	
	// Invalid test
	
	private Object[] getParamsForTestReadUserInvalid() {
        return new Object[]{
            new Object[]{null},
            new Object[]{""},
            new Object[]{"   "}
        };
    }
	
	@Test (expected=IllegalArgumentException.class)
	@Parameters(method = "getParamsForTestReadUserInvalid")
    public void testReadUserInvalid(String userID){
        // Mock FileUtil
		FileUtilities mockFu = mock(FileUtilities.class);

        // Stub read to return empty array
        when(mockFu.readStringsFromFile(anyString())).thenReturn(new String[]{});

        // Create AddUser with mock
        ReadUser ruMock = new ReadUser(mockFu);

        // Call addUser
       ruMock.readUser(userID, filePath);
    }

}


// java.lang.AssertionError: expected: my.edu.utar.User<U001|John|john@mail.com|0123456789> but was: my.edu.utar.User<U001|John|john@mail.com|0123456789>
