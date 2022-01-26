package gr.hua.oopii.travelAgency.API.iata;

import com.fasterxml.jackson.annotation.*;

public class Airport {
    private String name;
    private String iataCode;
    private String icaoCode;
    private double lat;
    private double lng;
    private String countryCode;
    private long popularity;
    private double distance;

    @JsonProperty("name")
    public String getName() { return name; }
    @JsonProperty("name")
    public void setName(String value) { this.name = value; }

    @JsonProperty("iata_code")
    public String getIataCode() { return iataCode; }
    @JsonProperty("iata_code")
    public void setIataCode(String value) { this.iataCode = value; }

    @JsonProperty("icao_code")
    public String getIcaoCode() { return icaoCode; }
    @JsonProperty("icao_code")
    public void setIcaoCode(String value) { this.icaoCode = value; }

    @JsonProperty("lat")
    public double getLat() { return lat; }
    @JsonProperty("lat")
    public void setLat(double value) { this.lat = value; }

    @JsonProperty("lng")
    public double getLng() { return lng; }
    @JsonProperty("lng")
    public void setLng(double value) { this.lng = value; }

    @JsonProperty("country_code")
    public String getCountryCode() { return countryCode; }
    @JsonProperty("country_code")
    public void setCountryCode(String value) { this.countryCode = value; }

    @JsonProperty("popularity")
    public long getPopularity() { return popularity; }
    @JsonProperty("popularity")
    public void setPopularity(long value) { this.popularity = value; }

    @JsonProperty("distance")
    public double getDistance() { return distance; }
    @JsonProperty("distance")
    public void setDistance(double value) { this.distance = value; }
}
