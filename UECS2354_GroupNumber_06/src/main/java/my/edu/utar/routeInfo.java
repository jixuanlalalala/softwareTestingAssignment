package my.edu.utar;

public class routeInfo{

	private String[][] routes = {
		    {"KL Sentral", "Mid Valley"},
		    {"KL Sentral", "Subang Jaya"},
		    {"Subang Jaya", "Shah Alam"},
		    {"Bangsar", "KL Sentral"},
		    {"KL Sentral", "Kepong Sentral"},
		    {"Sentul Timur", "Titiwangsa"},
		    {"Titiwangsa", "Ampang Park"},
		    {"Ampang Park", "KLCC"},
		    {"KLCC", "Masjid Jamek"},
		    {"Masjid Jamek", "Bandaraya"},
		    {"Bandaraya", "Batu Kentonmen"},
		    {"Batu Kentonmen", "Rawang"},
		    {"Rawang", "Sungai Buloh"},
		    {"Sungai Buloh", "Kepong Sentral"},
		    {"Serdang", "Kajang"},
		    {"Kajang", "Semenyih Sentral"},
		    {"Gombak", "Taman Melati"},
		    {"Taman Melati", "Wangsa Maju"},
		    {"Wangsa Maju", "Setiawangsa"},
		    {"Setiawangsa", "KL Sentral"}
		};
	 
	 private double[] distances = {
		        5.0, 18.0, 7.0, 2.0, 12.5,
		        3.5, 4.0, 1.0, 3.0, 1.5,
		        10.0, 26.0, 12.0, 9.5, 10.0,
		        6.0, 4.0, 2.0, 4.5, 16.0
	};

	
	public double getDistance(String start, String end) { 
		if (start == null || end == null) {
	        throw new IllegalArgumentException("Start or end station cannot be null.");
	    }
			
		//Make sure startStation is not same with endStation
		if (start.equals(end)) {
			throw new IllegalArgumentException("Start and end station cannot be the same. " );
		}
		
		for (int i = 0; i< routes.length; i++) {
	        String from = routes[i][0];
	        String to = routes[i][1];
	        double distance = distances[i];
	        //forward direction
	        if(from.equals(start) && to.equals(end)) {
	        	return distance;
	        }
			
			//reverse direction
			else if(from.equals(end) && to.equals(start)) {
				return distance;
			}
		}
		
		//if the start station and end station is not match
		throw new IllegalArgumentException("No route found between " + start + " and " + end);
	}
	
}
