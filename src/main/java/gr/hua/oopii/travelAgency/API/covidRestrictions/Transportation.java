
package gr.hua.oopii.travelAgency.API.covidRestrictions;

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
    "date",
    "text",
    "transportationType",
    "isBanned",
    "throughDate"
})
@Generated("jsonschema2pojo")
public class Transportation {

    @JsonProperty("date")
    private String date;
    @JsonProperty("text")
    private String text;
    @JsonProperty("transportationType")
    private String transportationType;
    @JsonProperty("isBanned")
    private String isBanned;
    @JsonProperty("throughDate")
    private String throughDate;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
    }

    @JsonProperty("text")
    public String getText() {
        return text;
    }

    @JsonProperty("text")
    public void setText(String text) {
        this.text = text;
    }

    @JsonProperty("transportationType")
    public String getTransportationType() {
        return transportationType;
    }

    @JsonProperty("transportationType")
    public void setTransportationType(String transportationType) {
        this.transportationType = transportationType;
    }

    @JsonProperty("isBanned")
    public String getIsBanned() {
        return isBanned;
    }

    @JsonProperty("isBanned")
    public void setIsBanned(String isBanned) {
        this.isBanned = isBanned;
    }

    @JsonProperty("throughDate")
    public String getThroughDate() {
        return throughDate;
    }

    @JsonProperty("throughDate")
    public void setThroughDate(String throughDate) {
        this.throughDate = throughDate;
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
        sb.append(Transportation.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("date");
        sb.append('=');
        sb.append(((this.date == null)?"<null>":this.date));
        sb.append(',');
        sb.append("text");
        sb.append('=');
        sb.append(((this.text == null)?"<null>":this.text));
        sb.append(',');
        sb.append("transportationType");
        sb.append('=');
        sb.append(((this.transportationType == null)?"<null>":this.transportationType));
        sb.append(',');
        sb.append("isBanned");
        sb.append('=');
        sb.append(((this.isBanned == null)?"<null>":this.isBanned));
        sb.append(',');
        sb.append("throughDate");
        sb.append('=');
        sb.append(((this.throughDate == null)?"<null>":this.throughDate));
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
