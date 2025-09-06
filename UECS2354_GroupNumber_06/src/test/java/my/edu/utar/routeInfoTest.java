package my.edu.utar;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameter;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class routeInfoTest {

	routeInfo ri = new routeInfo();
	
	//Valid start station and end station
	@Test
	@Parameters({"KL Sentral,Mid Valley, 5.0", "Mid Valley, KL Sentral, 5.0"})
	
	public void testSetDistanceValidStation(String start, String end, double ER) {
		ri.setDistance(start, end);
		
		double AR = ri.getDistance();
		
		assertEquals(ER , AR , 0.01);
	}
	
	//Invalid start station and end station
	@Test(expected = IllegalArgumentException.class)
	@Parameters({"KL Sentral,KL Sentral","KL Sentral, Sungai Long","Sungai Long, Mid Valley", "null,null"})
	
	public void testSetDistanceInvalidStation(String start, String end) {
		
		ri.setDistance(start, end);

	}

}
