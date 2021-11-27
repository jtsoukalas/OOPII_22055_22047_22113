package gr.hua.oopii.travelAgency.exception;

import java.io.Serial;

public class NoSuchCityException extends Exception {

	@Serial
	private static final long serialVersionUID = 1L;
	static int numExceptions =0;
	private final String cityName;
	private final String causeAPI;
	
	public NoSuchCityException(String in_cityName, String causeAPI) {
		numExceptions++;
		this.cityName=in_cityName;
		this.causeAPI =causeAPI;
	}
	
	public String getMessage() {
		return cityName+" not found at "+causeAPI+".";
	}

	public String getCityName() {
		return cityName;
	}
}
