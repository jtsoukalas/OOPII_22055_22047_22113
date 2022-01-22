
package gr.hua.oopii.travelAgency.API.covidRestrictions;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "area",
    "summary",
    "diseaseRiskLevel"
})
@Generated("jsonschema2pojo")
public class SubArea {

    @JsonProperty("area")
    private Area__1 area;
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("diseaseRiskLevel")
    private String diseaseRiskLevel;

    @JsonProperty("area")
    public Area__1 getArea() {
        return area;
    }

    @JsonProperty("area")
    public void setArea(Area__1 area) {
        this.area = area;
    }

    @JsonProperty("summary")
    public String getSummary() {
        return summary;
    }

    @JsonProperty("summary")
    public void setSummary(String summary) {
        this.summary = summary;
    }

    @JsonProperty("diseaseRiskLevel")
    public String getDiseaseRiskLevel() {
        return diseaseRiskLevel;
    }

    @JsonProperty("diseaseRiskLevel")
    public void setDiseaseRiskLevel(String diseaseRiskLevel) {
        this.diseaseRiskLevel = diseaseRiskLevel;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SubArea.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("area");
        sb.append('=');
        sb.append(((this.area == null)?"<null>":this.area));
        sb.append(',');
        sb.append("summary");
        sb.append('=');
        sb.append(((this.summary == null)?"<null>":this.summary));
        sb.append(',');
        sb.append("diseaseRiskLevel");
        sb.append('=');
        sb.append(((this.diseaseRiskLevel == null)?"<null>":this.diseaseRiskLevel));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
