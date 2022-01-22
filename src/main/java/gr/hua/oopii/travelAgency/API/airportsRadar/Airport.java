
package gr.hua.oopii.travelAgency.API.airportsRadar;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "icao_code",
    "country_code",
    "iata_code",
    "lng",
    "city",
    "timezone",
    "name",
    "city_code",
    "slug",
    "lat",
    "popularity",
    "distance"
})
@Generated("jsonschema2pojo")
public class Airport {

    @JsonProperty("icao_code")
    private String icaoCode;
    @JsonProperty("country_code")
    private String countryCode;
    @JsonProperty("iata_code")
    private String iataCode;
    @JsonProperty("lng")
    private Double lng;
    @JsonProperty("city")
    private String city;
    @JsonProperty("timezone")
    private String timezone;
    @JsonProperty("name")
    private String name;
    @JsonProperty("city_code")
    private String cityCode;
    @JsonProperty("slug")
    private String slug;
    @JsonProperty("lat")
    private Double lat;
    @JsonProperty("popularity")
    private Integer popularity;
    @JsonProperty("distance")
    private Double distance;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("icao_code")
    public String getIcaoCode() {
        return icaoCode;
    }

    @JsonProperty("icao_code")
    public void setIcaoCode(String icaoCode) {
        this.icaoCode = icaoCode;
    }

    @JsonProperty("country_code")
    public String getCountryCode() {
        return countryCode;
    }

    @JsonProperty("country_code")
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @JsonProperty("iata_code")
    public String getIataCode() {
        return iataCode;
    }

    @JsonProperty("iata_code")
    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    @JsonProperty("lng")
    public Double getLng() {
        return lng;
    }

    @JsonProperty("lng")
    public void setLng(Double lng) {
        this.lng = lng;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("timezone")
    public String getTimezone() {
        return timezone;
    }

    @JsonProperty("timezone")
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("city_code")
    public String getCityCode() {
        return cityCode;
    }

    @JsonProperty("city_code")
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    @JsonProperty("slug")
    public String getSlug() {
        return slug;
    }

    @JsonProperty("slug")
    public void setSlug(String slug) {
        this.slug = slug;
    }

    @JsonProperty("lat")
    public Double getLat() {
        return lat;
    }

    @JsonProperty("lat")
    public void setLat(Double lat) {
        this.lat = lat;
    }

    @JsonProperty("popularity")
    public Integer getPopularity() {
        return popularity;
    }

    @JsonProperty("popularity")
    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    @JsonProperty("distance")
    public Double getDistance() {
        return distance;
    }

    @JsonProperty("distance")
    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Airport.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("icaoCode");
        sb.append('=');
        sb.append(((this.icaoCode == null)?"<null>":this.icaoCode));
        sb.append(',');
        sb.append("countryCode");
        sb.append('=');
        sb.append(((this.countryCode == null)?"<null>":this.countryCode));
        sb.append(',');
        sb.append("iataCode");
        sb.append('=');
        sb.append(((this.iataCode == null)?"<null>":this.iataCode));
        sb.append(',');
        sb.append("lng");
        sb.append('=');
        sb.append(((this.lng == null)?"<null>":this.lng));
        sb.append(',');
        sb.append("city");
        sb.append('=');
        sb.append(((this.city == null)?"<null>":this.city));
        sb.append(',');
        sb.append("timezone");
        sb.append('=');
        sb.append(((this.timezone == null)?"<null>":this.timezone));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("cityCode");
        sb.append('=');
        sb.append(((this.cityCode == null)?"<null>":this.cityCode));
        sb.append(',');
        sb.append("slug");
        sb.append('=');
        sb.append(((this.slug == null)?"<null>":this.slug));
        sb.append(',');
        sb.append("lat");
        sb.append('=');
        sb.append(((this.lat == null)?"<null>":this.lat));
        sb.append(',');
        sb.append("popularity");
        sb.append('=');
        sb.append(((this.popularity == null)?"<null>":this.popularity));
        sb.append(',');
        sb.append("distance");
        sb.append('=');
        sb.append(((this.distance == null)?"<null>":this.distance));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
