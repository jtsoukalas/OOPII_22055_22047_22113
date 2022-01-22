
package gr.hua.oopii.travelAgency.API.covidRestrictions;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "areaType",
    "iataCode",
    "geoCode"
})
@Generated("jsonschema2pojo")
public class Area__1 {

    @JsonProperty("name")
    private String name;
    @JsonProperty("areaType")
    private String areaType;
    @JsonProperty("iataCode")
    private String iataCode;
    @JsonProperty("geoCode")
    private GeoCode__1 geoCode;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("areaType")
    public String getAreaType() {
        return areaType;
    }

    @JsonProperty("areaType")
    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    @JsonProperty("iataCode")
    public String getIataCode() {
        return iataCode;
    }

    @JsonProperty("iataCode")
    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    @JsonProperty("geoCode")
    public GeoCode__1 getGeoCode() {
        return geoCode;
    }

    @JsonProperty("geoCode")
    public void setGeoCode(GeoCode__1 geoCode) {
        this.geoCode = geoCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Area__1 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("areaType");
        sb.append('=');
        sb.append(((this.areaType == null)?"<null>":this.areaType));
        sb.append(',');
        sb.append("iataCode");
        sb.append('=');
        sb.append(((this.iataCode == null)?"<null>":this.iataCode));
        sb.append(',');
        sb.append("geoCode");
        sb.append('=');
        sb.append(((this.geoCode == null)?"<null>":this.geoCode));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
