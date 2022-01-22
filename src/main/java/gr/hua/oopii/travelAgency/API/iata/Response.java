package gr.hua.oopii.travelAgency.API.iata;

import com.fasterxml.jackson.annotation.*;

public class Response {
    private Airport[] airports;
    private Airport[] cities;

    @JsonProperty("airports")
    public Airport[] getAirports() { return airports; }
    @JsonProperty("airports")
    public void setAirports(Airport[] value) { this.airports = value; }

    @JsonProperty("cities")
    public Airport[] getCities() { return cities; }
    @JsonProperty("cities")
    public void setCities(Airport[] value) { this.cities = value; }
}
