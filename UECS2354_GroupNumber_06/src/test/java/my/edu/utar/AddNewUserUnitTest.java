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
public class AddNewUserUnitTest {
	
	String filePath = "user.txt";

	private Object[] getParamsForTestAddUserValid() {
		return new Object[]{
		        new Object[]{
		            new User("U001","John", "john@mail.com", "0123456789"),
		            new String[]{},
		            new String[]{"U001|John|john@mail.com|0123456789"}
		        },
		        new Object[]{
		            new User("U002", "Bob", "bob@mail.com", "0222222222"),
		            new String[]{"U000|Alice|alice@mail.com|0111111111"},
		            new String[]{
		                "U000|Alice|alice@mail.com|0111111111",
		                "U002|Bob|bob@mail.com|0222222222"
		            }
		        },
		        new Object[]{
		            new User("U002", "Bob", "bob@mail.com", "0222222222"),
		            new String[]{
		                "U000|Alice|alice@mail.com|0111111111",
		                "U001|Eve|eve@mail.com|0333333333"
		            },
		            new String[]{
		                "U000|Alice|alice@mail.com|0111111111",
		                "U001|Eve|eve@mail.com|0333333333",
		                "U002|Bob|bob@mail.com|0222222222"
		            }
		        }
		    };
	}
	@Test
	@Parameters(method = "getParamsForTestAddUserValid")
    public void testAddUserValid(User user,String[] existing,  String[] ER){
        // Mock FileUtil
		FileUtilities mockFu = mock(FileUtilities.class);
        

        // Stub read to return empty array
        when(mockFu.readStringsFromFile(filePath)).thenReturn(existing);

        // Create AddUser with mock
        AddNewUser anuMock = new AddNewUser(mockFu);

        // Call addUser
        
        anuMock.addUser(user, filePath);

        // Verify writeStringsToFile called with expected array
        verify(mockFu).writeStringsToFile(ER, filePath);
    }
	
	
	private Object[] getParamsForTestAddUserInvalid() {
		return new Object[]{
		        new Object[]{ null, new String[]{} },
		        new Object[]{ new User("U003", null, "invalid@mail.com", "01234"), new String[]{} },
		        new Object[]{ new User("U003", "", "invalid@mail.com", "01234"), new String[]{} },
		        new Object[]{ new User(null, "Charlie", "charlie@mail.com", "0456789"), new String[]{} },
		        new Object[]{ new User("U004", "Charlie", null, "0456789"), new String[]{} },
		        new Object[]{ new User("U005", "Charlie", "charlie@mail.com", null), new String[]{} }
		    };
	}
	
	@Test (expected=IllegalArgumentException.class)
	@Parameters(method = "getParamsForTestAddUserInvalid")
    public void testAddUserInvalid(User user, String[] ER){
        // Mock FileUtil
		FileUtilities mockFu = mock(FileUtilities.class);
        

        // Stub read to return empty array
        when(mockFu.readStringsFromFile(filePath)).thenReturn(new String[]{});

        // Create AddUser with mock
        AddNewUser anuMock = new AddNewUser(mockFu);

        // Call addUser
        
        anuMock.addUser(user, filePath);

    }
}
