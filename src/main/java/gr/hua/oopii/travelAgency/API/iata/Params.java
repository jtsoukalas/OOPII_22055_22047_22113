package gr.hua.oopii.travelAgency.API.iata;

import com.fasterxml.jackson.annotation.*;

public class Params {
    private String lat;
    private String lng;
    private String distance;
    private String lang;

    @JsonProperty("lat")
    public String getLat() { return lat; }
    @JsonProperty("lat")
    public void setLat(String value) { this.lat = value; }

    @JsonProperty("lng")
    public String getLng() { return lng; }
    @JsonProperty("lng")
    public void setLng(String value) { this.lng = value; }

    @JsonProperty("distance")
    public String getDistance() { return distance; }
    @JsonProperty("distance")
    public void setDistance(String value) { this.distance = value; }

    @JsonProperty("lang")
    public String getLang() { return lang; }
    @JsonProperty("lang")
    public void setLang(String value) { this.lang = value; }
}
