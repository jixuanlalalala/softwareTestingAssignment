package my.edu.utar;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class FileUtilitiesUnitTest {

	String filePath = "dummyfile1.txt";

	
	private Object[] getParamsForTestReadAndWrite() {
        return new Object[]{
            new Object[]{ new String[]{"cat", "dog"} },
            new Object[]{ new String[]{"cat", "dog mouse elephant", "", "rat monkey", "  "} },
            new Object[]{ new String[]{""} },
            new Object[]{ new String[]{} } // empty file
        };
    }
	
	@Test
	@Parameters(method = "getParamsForTestReadAndWrite")
	public void testReadAndWrite(String[] input) {
		
		FileUtilities fu = new FileUtilities();
		
		// Write test data
        fu.writeStringsToFile(input, filePath);

        // Read back
        String[] stringsFromFile = fu.readStringsFromFile(filePath);
		
		assertArrayEquals(stringsFromFile, input);
	}
	
	
	private Object[] getParamsForTestReadWithError() {
        return new Object[]{
            new Object[]{"somecrazyfilenamethatdoesnotexist.txt"},
            new Object[]{"another_non_existent_file.xyz"}
        };
    }

	@Test(expected=IllegalArgumentException.class)	
	@Parameters(method = "getParamsForTestReadWithError")
	public void testReadWithError(String invalidFilePath) {
		FileUtilities fu = new FileUtilities();
		fu.readStringsFromFile(invalidFilePath);
	}

}
