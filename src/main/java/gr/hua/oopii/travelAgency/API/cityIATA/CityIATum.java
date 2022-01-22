
package gr.hua.oopii.travelAgency.API.cityIATA;

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
    "name",
    "city_code",
    "un_locode",
    "lat",
    "lng",
    "alt",
    "timezone",
    "country_code",
    "population",
    "names",
    "wikipedia",
    "slug"
})
@Generated("jsonschema2pojo")
public class CityIATum {

    @JsonProperty("name")
    private String name;
    @JsonProperty("city_code")
    private String cityCode;
    @JsonProperty("un_locode")
    private String unLocode;
    @JsonProperty("lat")
    private Double lat;
    @JsonProperty("lng")
    private Double lng;
    @JsonProperty("alt")
    private Integer alt;
    @JsonProperty("timezone")
    private String timezone;
    @JsonProperty("country_code")
    private String countryCode;
    @JsonProperty("population")
    private Integer population;
    @JsonProperty("names")
    private Names names;
    @JsonProperty("wikipedia")
    private String wikipedia;
    @JsonProperty("slug")
    private String slug;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

    @JsonProperty("un_locode")
    public String getUnLocode() {
        return unLocode;
    }

    @JsonProperty("un_locode")
    public void setUnLocode(String unLocode) {
        this.unLocode = unLocode;
    }

    @JsonProperty("lat")
    public Double getLat() {
        return lat;
    }

    @JsonProperty("lat")
    public void setLat(Double lat) {
        this.lat = lat;
    }

    @JsonProperty("lng")
    public Double getLng() {
        return lng;
    }

    @JsonProperty("lng")
    public void setLng(Double lng) {
        this.lng = lng;
    }

    @JsonProperty("alt")
    public Integer getAlt() {
        return alt;
    }

    @JsonProperty("alt")
    public void setAlt(Integer alt) {
        this.alt = alt;
    }

    @JsonProperty("timezone")
    public String getTimezone() {
        return timezone;
    }

    @JsonProperty("timezone")
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @JsonProperty("country_code")
    public String getCountryCode() {
        return countryCode;
    }

    @JsonProperty("country_code")
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @JsonProperty("population")
    public Integer getPopulation() {
        return population;
    }

    @JsonProperty("population")
    public void setPopulation(Integer population) {
        this.population = population;
    }

    @JsonProperty("names")
    public Names getNames() {
        return names;
    }

    @JsonProperty("names")
    public void setNames(Names names) {
        this.names = names;
    }

    @JsonProperty("wikipedia")
    public String getWikipedia() {
        return wikipedia;
    }

    @JsonProperty("wikipedia")
    public void setWikipedia(String wikipedia) {
        this.wikipedia = wikipedia;
    }

    @JsonProperty("slug")
    public String getSlug() {
        return slug;
    }

    @JsonProperty("slug")
    public void setSlug(String slug) {
        this.slug = slug;
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
        sb.append(CityIATum.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("cityCode");
        sb.append('=');
        sb.append(((this.cityCode == null)?"<null>":this.cityCode));
        sb.append(',');
        sb.append("unLocode");
        sb.append('=');
        sb.append(((this.unLocode == null)?"<null>":this.unLocode));
        sb.append(',');
        sb.append("lat");
        sb.append('=');
        sb.append(((this.lat == null)?"<null>":this.lat));
        sb.append(',');
        sb.append("lng");
        sb.append('=');
        sb.append(((this.lng == null)?"<null>":this.lng));
        sb.append(',');
        sb.append("alt");
        sb.append('=');
        sb.append(((this.alt == null)?"<null>":this.alt));
        sb.append(',');
        sb.append("timezone");
        sb.append('=');
        sb.append(((this.timezone == null)?"<null>":this.timezone));
        sb.append(',');
        sb.append("countryCode");
        sb.append('=');
        sb.append(((this.countryCode == null)?"<null>":this.countryCode));
        sb.append(',');
        sb.append("population");
        sb.append('=');
        sb.append(((this.population == null)?"<null>":this.population));
        sb.append(',');
        sb.append("names");
        sb.append('=');
        sb.append(((this.names == null)?"<null>":this.names));
        sb.append(',');
        sb.append("wikipedia");
        sb.append('=');
        sb.append(((this.wikipedia == null)?"<null>":this.wikipedia));
        sb.append(',');
        sb.append("slug");
        sb.append('=');
        sb.append(((this.slug == null)?"<null>":this.slug));
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
