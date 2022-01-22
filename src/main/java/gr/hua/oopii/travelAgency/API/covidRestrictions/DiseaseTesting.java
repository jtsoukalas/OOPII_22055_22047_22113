
package gr.hua.oopii.travelAgency.API.covidRestrictions;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "when",
    "requirement",
    "text",
    "rules",
    "isRequired",
    "date"
})
@Generated("jsonschema2pojo")
public class DiseaseTesting {

    @JsonProperty("when")
    private String when;
    @JsonProperty("requirement")
    private String requirement;
    @JsonProperty("text")
    private String text;
    @JsonProperty("rules")
    private String rules;
    @JsonProperty("isRequired")
    private String isRequired;
    @JsonProperty("date")
    private String date;

    @JsonProperty("when")
    public String getWhen() {
        return when;
    }

    @JsonProperty("when")
    public void setWhen(String when) {
        this.when = when;
    }

    @JsonProperty("requirement")
    public String getRequirement() {
        return requirement;
    }

    @JsonProperty("requirement")
    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    @JsonProperty("text")
    public String getText() {
        return text;
    }

    @JsonProperty("text")
    public void setText(String text) {
        this.text = text;
    }

    @JsonProperty("rules")
    public String getRules() {
        return rules;
    }

    @JsonProperty("rules")
    public void setRules(String rules) {
        this.rules = rules;
    }

    @JsonProperty("isRequired")
    public String getIsRequired() {
        return isRequired;
    }

    @JsonProperty("isRequired")
    public void setIsRequired(String isRequired) {
        this.isRequired = isRequired;
    }

    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(DiseaseTesting.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("when");
        sb.append('=');
        sb.append(((this.when == null)?"<null>":this.when));
        sb.append(',');
        sb.append("requirement");
        sb.append('=');
        sb.append(((this.requirement == null)?"<null>":this.requirement));
        sb.append(',');
        sb.append("text");
        sb.append('=');
        sb.append(((this.text == null)?"<null>":this.text));
        sb.append(',');
        sb.append("rules");
        sb.append('=');
        sb.append(((this.rules == null)?"<null>":this.rules));
        sb.append(',');
        sb.append("isRequired");
        sb.append('=');
        sb.append(((this.isRequired == null)?"<null>":this.isRequired));
        sb.append(',');
        sb.append("date");
        sb.append('=');
        sb.append(((this.date == null)?"<null>":this.date));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
