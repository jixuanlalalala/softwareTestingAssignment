package my.edu.utar;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class ReadUserIntegrationTest {

	private String filePath = "test_user.txt";
	
	FileUtilities fu = new FileUtilities();
	ReadUser ru = new ReadUser(fu);
	
	
	// Valid Test
	private Object[] getParamsForTestReadUserValid() {
		return new Object[]{
		        // TC-R-3: File is empty
		        new Object[]{
		            "123", // userID (valid)
		            new String[]{}, // existing file content (empty)
		            null // Expected Result: null
		        },
		        // TC-R-4: Record does not exist
		        new Object[]{
		            "999", // userID (valid, not found)
		            new String[]{"1|John|a@b.com|555", "2|Jane|j@e.com|666"}, // existing file content
		            null // Expected Result: null
		        },
		        // TC-R-5: Record exists
		        new Object[]{
		            "2", // userID (valid, found)
		            new String[]{"1|John|a@b.com|555", "2|Jane|j@e.com|666"}, // existing file content
		            new User("2", "Jane", "j@e.com", "666") // Expected Result: User object
		        }
		    };

    }
	
	@Test
	@Parameters(method = "getParamsForTestReadUserValid")
    public void testReadUserValid(String userID, String[] existing, User ER){
        
		fu.writeStringsToFile(existing, filePath);

        // Call addUser
        User AR = ru.readUser(userID,filePath);
        
        // Use a helper assertion or check for null
        if (ER == null) {
            assertNull(AR);
        } else {
            assertNotNull(AR);
            assertEquals(ER.getId(), AR.getId());
            assertEquals(ER.getName(), AR.getName());
            assertEquals(ER.getEmail(), AR.getEmail());
            assertEquals(ER.getPhoneNumber(), AR.getPhoneNumber());
        }
    }
	
	// Invalid test
	private Object[] getParamsForTestReadUserInvalid() {
		return new Object[]{
		        // TC-R-1: userID is null
		        new Object[]{
		            null, // userID (invalid)
		            new String[]{"1|John|a@b.com|555"}, // existing file content
		        },
		        // TC-R-2: userID is empty
		        new Object[]{
		            "", // userID (invalid)
		            new String[]{"1|John|a@b.com|555"}, // existing file content
		        }
		    };
    }
	
	@Test (expected=IllegalArgumentException.class)
	@Parameters(method = "getParamsForTestReadUserInvalid")
    public void testReadUserInvalid(String userID, String[] existing){
		
		fu.writeStringsToFile(existing, filePath);
		
        ru.readUser(userID, filePath);
    }

}

