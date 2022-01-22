
package gr.hua.oopii.travelAgency.API.covidRestrictions;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "rate",
    "level",
    "date",
    "infectionMapLink"
})
@Generated("jsonschema2pojo")
public class DiseaseInfection {

    @JsonProperty("rate")
    private String rate;
    @JsonProperty("level")
    private String level;
    @JsonProperty("date")
    private String date;
    @JsonProperty("infectionMapLink")
    private String infectionMapLink;

    @JsonProperty("rate")
    public String getRate() {
        return rate;
    }

    @JsonProperty("rate")
    public void setRate(String rate) {
        this.rate = rate;
    }

    @JsonProperty("level")
    public String getLevel() {
        return level;
    }

    @JsonProperty("level")
    public void setLevel(String level) {
        this.level = level;
    }

    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
    }

    @JsonProperty("infectionMapLink")
    public String getInfectionMapLink() {
        return infectionMapLink;
    }

    @JsonProperty("infectionMapLink")
    public void setInfectionMapLink(String infectionMapLink) {
        this.infectionMapLink = infectionMapLink;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(DiseaseInfection.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("rate");
        sb.append('=');
        sb.append(((this.rate == null)?"<null>":this.rate));
        sb.append(',');
        sb.append("level");
        sb.append('=');
        sb.append(((this.level == null)?"<null>":this.level));
        sb.append(',');
        sb.append("date");
        sb.append('=');
        sb.append(((this.date == null)?"<null>":this.date));
        sb.append(',');
        sb.append("infectionMapLink");
        sb.append('=');
        sb.append(((this.infectionMapLink == null)?"<null>":this.infectionMapLink));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
