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
public class AddNewUserIntegrationTest {
	
	String filePath = "test_user.txt";
	
	FileUtilities fu = new FileUtilities();
	AddNewUser anu = new AddNewUser(fu);

	private Object[] getParamsForTestAddUserValid() {

	    return new Object[]{
	        new Object[]{
	            new User("1", "John Doe", "john.doe@email.com", "1234567890"),
	            "1|John Doe|john.doe@email.com|1234567890"
	        },
	        new Object[]{
	            new User("3", "Alice", "alice@company.com", "123456789012"), 
	            "3|Alice|alice@company.com|123456789012" 
	        }
	    };
	}
	@Test
	@Parameters(method = "getParamsForTestAddUserValid")
    public void testAddUserValid(User user, String ER){
		

        anu.addUser(user, filePath);

        String[] result= fu.readStringsFromFile(filePath);
        assertEquals(ER, result[result.length-1]);
    }
	
	
	private Object[] getParamsForTestAddUserInvalid() {
		return new Object[]{
	        // Test Case 1: User is null
	        new Object[]{null}, 
	        // Test Case 2: User with null ID
	        new Object[]{new User(null, "John", "a@b.com", "1234567890") },
	        // Test Case 3: User with empty ID
	        new Object[]{new User("", "John", "a@b.com", "1234567890") },
	        
	        // Test Case 4: User with null name
	        new Object[]{new User("1", null, "a@b.com", "1234567890") },
	        // Test Case 5: User with empty name
	        new Object[]{new User("1", "", "a@b.com", "1234567890") },
	        
	        // Test Case 6: User with null email
	        new Object[]{new  User("1", "John", null, "1234567890") },
	        // Test Case 7: User with empty email
	        new Object[]{new User("1", "John", "", "1234567890") },
	        // Test Case 8: User with invalid email format (no @)
	        new Object[]{new User("1", "John", "invalid-email", "1234567890") },
	        
	        // Test Case 9: User with null phone
	        new Object[]{new User("1", "John", "a@b.com", null) },
	        // Test Case 10: User with phone containing non-digits
	        new Object[]{new User("1", "John", "a@b.com", "123-456-789") },
	        // Test Case 11: User with phone is empty
	        new Object[]{new User("1", "John", "a@b.com", "") },
	        // Test Case 12: User with phone length 9 (invalid)
	        new Object[]{new User("1", "John", "a@b.com", "123456789") },
	        // Test Case 15: User with phone length 13 (invalid)
	        new Object[]{new User("1", "John", "a@b.com", "1234567890123") }
	    };
	}
	
	@Test (expected=IllegalArgumentException.class)
	@Parameters(method = "getParamsForTestAddUserInvalid")
    public void testAddUserInvalid(User user){
		
		
        anu.addUser(user, filePath);

    }
}
