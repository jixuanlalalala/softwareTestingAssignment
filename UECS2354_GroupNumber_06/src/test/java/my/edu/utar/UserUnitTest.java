package my.edu.utar;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameter;
import static org.junit.Assert.*;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class UserUnitTest {
	
	private Object[] getParamsForTestInvalidGetterAndSetter() {
		return new Object[] {
				new Object[] {null, "Lim Zhen Cheng", "limzc@gmail.com", "0123456789"},
				new Object[] {"   ", "Lim Zhen Cheng", "limzc@gmail.com", "0123456789"},
				new Object[] {"2205922", null, "limzc@gmail.com", "0123456789"},
				new Object[] {"2205922",  "Lim Zhen Cheng", null, "0123456789"},
				new Object[] {"2205922", "Lim Zhen Cheng", "limzc@gmail.com", null},
				new Object[] {"2205922", "Lim Zhen Cheng", "invalidEmail", "0123456789"},
				new Object[] {"2205522","Lim Zhen Cheng", "1234@gmail.com", "012345"}, 
				new Object[] {"2205922", "Lim Zhen Cheng", "123456@gmail.com", "01234567890123"},
				new Object[] {"2202592", "Lim Zhen Cheng", "123456@gmail.com", "abcdefghijk"}
		};
	}
	
	
	private Object[] getParamsForTestValidGetterAndSetter() {
		return new Object[] {
				new Object[] {"2208899", "Ken Song", "test@gmail.com", "01234567890" },
				new Object[] {"2205922", "Mickey", "test@gmail.com", "01234567890"},
				new Object[] {"2205922", "Ken Song", "tested@gmail.com", "01234567890"},
				new Object[] {"2205922", "Ken Song", "test@gmail.com", "02255336677"}
		};
	}
	//test for getter and setters
	@Test
	@Parameters(method = "getParamsForTestValidGetterAndSetter")
	public void testUserValidGetterAndSetter(String id, String name, String email, String phone) {
		User user = new User("2205922", "Ken Song", "test@gmail.com", "01234567890");
		
		user.setId(id);
		user.setName(name);
		user.setEmail(email);
		user.setPhoneNumber(phone);
		
		String arId = user.getId();
		String arName = user.getName();
		String arEmail = user.getEmail();
		String arPhone = user.getPhoneNumber();
		
		assertEquals(arId, id);
		assertEquals(arName, name);
		assertEquals(arEmail, email);
		assertEquals(arPhone, phone);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	@Parameters(method = "getParamsForTestInvalidGetterAndSetter")
	public void testUserInvalidGetterAndSetter(String id, String name, String email, String phone) {
		User user = new User("2205922", "Ken Song", "test@gmail.com", "01234567890");
		
		user.setId(id);
		user.setName(name);
		user.setEmail(email);
		user.setPhoneNumber(phone);
		
		user.getId();
		user.getName();
		user.getEmail();
		user.getPhoneNumber();
	}
	
}
