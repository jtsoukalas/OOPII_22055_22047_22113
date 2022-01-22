package gr.hua.oopii.travelAgency.API.iata;

import com.fasterxml.jackson.annotation.*;

public class Geo {
    private String countryCode;
    private String country;
    private String continent;
    private double lat;
    private double lng;
    private String timezone;

    @JsonProperty("country_code")
    public String getCountryCode() { return countryCode; }
    @JsonProperty("country_code")
    public void setCountryCode(String value) { this.countryCode = value; }

    @JsonProperty("country")
    public String getCountry() { return country; }
    @JsonProperty("country")
    public void setCountry(String value) { this.country = value; }

    @JsonProperty("continent")
    public String getContinent() { return continent; }
    @JsonProperty("continent")
    public void setContinent(String value) { this.continent = value; }

    @JsonProperty("lat")
    public double getLat() { return lat; }
    @JsonProperty("lat")
    public void setLat(double value) { this.lat = value; }

    @JsonProperty("lng")
    public double getLng() { return lng; }
    @JsonProperty("lng")
    public void setLng(double value) { this.lng = value; }

    @JsonProperty("timezone")
    public String getTimezone() { return timezone; }
    @JsonProperty("timezone")
    public void setTimezone(String value) { this.timezone = value; }
}
