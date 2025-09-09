package my.edu.utar;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameter;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class routeInfoTest {

	routeInfo ri = new routeInfo();
	
	/* *
	 * Reading test data from testdata_setDistance.txt for testing valid values
	 * */
	static ArrayList<String[]> linesreadsurcharge;
	
	static {
		Scanner input;
		linesreadsurcharge = new ArrayList<>();
		String filename = "testdata_setDistance.txt";
		input = null;
		
		try {
			input = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found " + filename);
			System.exit(0);
		}
		
		while (input.hasNextLine()) {
			String singleline = input.nextLine();
			String[] tokens = singleline.split(",");
			linesreadsurcharge.add(tokens);
		}
		
	}
	
	private Object[][] getDataFortestValidSetDistance() {
	    Object[][] data = new Object[linesreadsurcharge.size()][];
	    
	    for (int i = 0; i < linesreadsurcharge.size(); i++) {
	        String[] tokens = linesreadsurcharge.get(i);

	        String from = tokens[0].trim();
	        String to = tokens[1].trim();
	        double ER = Double.parseDouble(tokens[2].trim());

	        data[i] = new Object[] { from, to, ER };
	    }

	    return data;
	}
	@Test
	@Parameters(method = "getDataFortestValidSetDistance")
	
	public void testSetDistanceValidStation(String start, String end, double ER) {
		ri.setDistance(start, end);
		
		double AR = ri.getDistance();
		
		assertEquals(ER , AR , 0.01);
	}
	
	//Invalid start station and end station
	@Test(expected = IllegalArgumentException.class)
	@Parameters({"KL Sentral,KL Sentral","KL Sentral, Sungai Long","Sungai Long, Mid Valley","KL Sentral,null","null,KL Sentral", "null,null"})
	
	public void testSetDistanceInvalidStation(String start, String end) {
		
		ri.setDistance(start, end);

	}

}
