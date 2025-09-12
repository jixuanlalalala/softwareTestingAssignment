package my.edu.utar;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class FileUtilitiesUnitTest {

	private FileUtilities fu;

	@Before
	public void setUp() {
	    fu = new FileUtilities();
	}
	
	private Object[] getParamsForTestReadAndWrite() {
        return new Object[]{
            new Object[]{ new String[]{"cat", "dog"}, "dummyfile.txt" },
            new Object[]{ new String[]{""}, "dummyfile.txt"},
            new Object[]{ new String[]{}, "dummyfile.txt" } // empty file
        };
    }
	
	@Test
	@Parameters(method = "getParamsForTestReadAndWrite")
	public void testReadAndWrite(String[] input, String filePath) {
		
		// Write test data
        fu.writeStringsToFile(input, filePath);

        // Read back
        String[] stringsFromFile = fu.readStringsFromFile(filePath);
        assertArrayEquals(input, stringsFromFile);
	}
	

	@Test(expected=IllegalArgumentException.class)	
	public void testReadWithError() {
		
		fu.readStringsFromFile("somecrazyfilenamethatdoesnotexist.txt");
	}

}
