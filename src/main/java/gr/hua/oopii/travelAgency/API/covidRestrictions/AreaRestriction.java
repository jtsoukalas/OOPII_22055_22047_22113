
package gr.hua.oopii.travelAgency.API.covidRestrictions;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "text",
    "date",
    "restrictionType"
})
@Generated("jsonschema2pojo")
public class AreaRestriction {

    @JsonProperty("text")
    private String text;
    @JsonProperty("date")
    private String date;
    @JsonProperty("restrictionType")
    private String restrictionType;

    @JsonProperty("text")
    public String getText() {
        return text;
    }

    @JsonProperty("text")
    public void setText(String text) {
        this.text = text;
    }

    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
    }

    @JsonProperty("restrictionType")
    public String getRestrictionType() {
        return restrictionType;
    }

    @JsonProperty("restrictionType")
    public void setRestrictionType(String restrictionType) {
        this.restrictionType = restrictionType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(AreaRestriction.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("text");
        sb.append('=');
        sb.append(((this.text == null)?"<null>":this.text));
        sb.append(',');
        sb.append("date");
        sb.append('=');
        sb.append(((this.date == null)?"<null>":this.date));
        sb.append(',');
        sb.append("restrictionType");
        sb.append('=');
        sb.append(((this.restrictionType == null)?"<null>":this.restrictionType));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
