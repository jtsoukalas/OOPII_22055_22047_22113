
package gr.hua.oopii.travelAgency.API.airportsRadar;

import java.util.HashMap;
import java.util.List;
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
    "airports",
    "cities"
})
@Generated("jsonschema2pojo")
public class AirportsRadar {

    @JsonProperty("airports")
    private List<Airport> airports = null;
    @JsonProperty("cities")
    private List<City> cities = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("airports")
    public List<Airport> getAirports() {
        return airports;
    }

    @JsonProperty("airports")
    public void setAirports(List<Airport> airports) {
        this.airports = airports;
    }

    @JsonProperty("cities")
    public List<City> getCities() {
        return cities;
    }

    @JsonProperty("cities")
    public void setCities(List<City> cities) {
        this.cities = cities;
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
        sb.append(AirportsRadar.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("airports");
        sb.append('=');
        sb.append(((this.airports == null)?"<null>":this.airports));
        sb.append(',');
        sb.append("cities");
        sb.append('=');
        sb.append(((this.cities == null)?"<null>":this.cities));
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
