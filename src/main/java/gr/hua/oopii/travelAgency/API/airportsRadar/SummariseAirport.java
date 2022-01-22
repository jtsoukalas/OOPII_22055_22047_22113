package gr.hua.oopii.travelAgency.API.airportsRadar;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SummariseAirport {
    @JsonProperty("country_code")
    private String countryCode;
    @JsonProperty("iata_code")
    private String iataCode;
    @JsonProperty("lng")
    private Double lng;
    @JsonProperty("name")
    private String name;
    @JsonProperty("lat")
    private Double lat;
    @JsonProperty("popularity")
    private Integer popularity;
    @JsonProperty("distance")
    private Double distance;

    public SummariseAirport() {
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
