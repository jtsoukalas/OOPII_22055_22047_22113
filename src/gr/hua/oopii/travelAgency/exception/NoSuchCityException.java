package gr.hua.oopii.travelAgency.exception;

import java.io.Serial;

/**
 * The exceptions are used when there is not a city with this "name"
 * @since secondDeliverable
 * @version 0
 * @author
 */

public class NoSuchCityException extends Exception {

	@Serial
	private static final long serialVersionUID = 1L;
	static int numExceptions =0;
	private final String cityName;
	private final String causeAPI;

	/**
	 *
	 * @param in_cityName -String (name of the city that was the input)
	 * @param causeAPI -String (source API that caused the error)
	 */
	public NoSuchCityException(String in_cityName, String causeAPI) {
		numExceptions++;
		this.cityName=in_cityName;
		this.causeAPI =causeAPI;
	}

	/**
	 *
	 * @return a message that there is not such a city
	 */
	public String getMessage() {
		return cityName+" not found at "+causeAPI+".";
	}

	/**
	 * getter
	 * @return city name that was the input
	 */
	public String getCityName() {
		return cityName;
	}
}
