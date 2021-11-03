package firstDeliverable.exception;

import java.io.Serial;

public class WikipediaNoArticleException extends Exception {

	@Serial
	private static final long serialVersionUID = 1L;
	static int numExceptions =0;
	private final String cityName;
	
	public WikipediaNoArticleException(String in_cityName) {
		numExceptions++;
		this.cityName=in_cityName;
	}
	
	public String getMessage() {
		return "There is not any wikipedia article with title "+cityName+".";
	}
}
