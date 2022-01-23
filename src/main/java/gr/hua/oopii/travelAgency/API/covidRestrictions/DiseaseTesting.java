
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
    "isRequired",
    "when",
    "requirement",
    "rules",
    "testType",
    "minimumAge",
    "validityPeriod"
})
@Generated("jsonschema2pojo")
public class DiseaseTesting {

    @JsonProperty("date")
    private String date;
    @JsonProperty("text")
    private String text;
    @JsonProperty("isRequired")
    private String isRequired;
    @JsonProperty("when")
    private String when;
    @JsonProperty("requirement")
    private String requirement;
    @JsonProperty("rules")
    private String rules;
    @JsonProperty("testType")
    private String testType;
    @JsonProperty("minimumAge")
    private Integer minimumAge;
    @JsonProperty("validityPeriod")
    private ValidityPeriod validityPeriod;
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

    @JsonProperty("isRequired")
    public String getIsRequired() {
        return isRequired;
    }

    @JsonProperty("isRequired")
    public void setIsRequired(String isRequired) {
        this.isRequired = isRequired;
    }

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

    @JsonProperty("rules")
    public String getRules() {
        return rules;
    }

    @JsonProperty("rules")
    public void setRules(String rules) {
        this.rules = rules;
    }

    @JsonProperty("testType")
    public String getTestType() {
        return testType;
    }

    @JsonProperty("testType")
    public void setTestType(String testType) {
        this.testType = testType;
    }

    @JsonProperty("minimumAge")
    public Integer getMinimumAge() {
        return minimumAge;
    }

    @JsonProperty("minimumAge")
    public void setMinimumAge(Integer minimumAge) {
        this.minimumAge = minimumAge;
    }

    @JsonProperty("validityPeriod")
    public ValidityPeriod getValidityPeriod() {
        return validityPeriod;
    }

    @JsonProperty("validityPeriod")
    public void setValidityPeriod(ValidityPeriod validityPeriod) {
        this.validityPeriod = validityPeriod;
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
        sb.append(DiseaseTesting.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("date");
        sb.append('=');
        sb.append(((this.date == null)?"<null>":this.date));
        sb.append(',');
        sb.append("text");
        sb.append('=');
        sb.append(((this.text == null)?"<null>":this.text));
        sb.append(',');
        sb.append("isRequired");
        sb.append('=');
        sb.append(((this.isRequired == null)?"<null>":this.isRequired));
        sb.append(',');
        sb.append("when");
        sb.append('=');
        sb.append(((this.when == null)?"<null>":this.when));
        sb.append(',');
        sb.append("requirement");
        sb.append('=');
        sb.append(((this.requirement == null)?"<null>":this.requirement));
        sb.append(',');
        sb.append("rules");
        sb.append('=');
        sb.append(((this.rules == null)?"<null>":this.rules));
        sb.append(',');
        sb.append("testType");
        sb.append('=');
        sb.append(((this.testType == null)?"<null>":this.testType));
        sb.append(',');
        sb.append("minimumAge");
        sb.append('=');
        sb.append(((this.minimumAge == null)?"<null>":this.minimumAge));
        sb.append(',');
        sb.append("validityPeriod");
        sb.append('=');
        sb.append(((this.validityPeriod == null)?"<null>":this.validityPeriod));
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
