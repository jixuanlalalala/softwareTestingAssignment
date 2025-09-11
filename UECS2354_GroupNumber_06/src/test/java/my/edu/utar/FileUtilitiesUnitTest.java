package my.edu.utar;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class FileUtilitiesUnitTest {

	String filePath = "dummyfile.txt";
	
	FileUtilities fu = new FileUtilities();
	
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
		
		// Write test data
        fu.writeStringsToFile(input, filePath);

        // Read back
        String[] stringsFromFile = fu.readStringsFromFile(filePath);
        assertArrayEquals(input, stringsFromFile);
	}
	
	
	private Object[] getParamsForTestReadWithError() {
        return new Object[]{
            new Object[]{"somecrazyfilenamethatdoesnotexist.txt"}
        };
    }

	@Test(expected=IllegalArgumentException.class)	
	@Parameters(method = "getParamsForTestReadWithError")
	public void testReadWithError(String invalidFilePath) {
		
		fu.readStringsFromFile(invalidFilePath);
	}

}
