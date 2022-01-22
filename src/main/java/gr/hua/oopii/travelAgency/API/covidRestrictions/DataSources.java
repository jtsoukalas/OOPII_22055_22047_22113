
package gr.hua.oopii.travelAgency.API.covidRestrictions;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "governmentSiteLink",
    "healthDepartmentSiteLink"
})
@Generated("jsonschema2pojo")
public class DataSources {

    @JsonProperty("governmentSiteLink")
    private String governmentSiteLink;
    @JsonProperty("healthDepartmentSiteLink")
    private String healthDepartmentSiteLink;

    @JsonProperty("governmentSiteLink")
    public String getGovernmentSiteLink() {
        return governmentSiteLink;
    }

    @JsonProperty("governmentSiteLink")
    public void setGovernmentSiteLink(String governmentSiteLink) {
        this.governmentSiteLink = governmentSiteLink;
    }

    @JsonProperty("healthDepartmentSiteLink")
    public String getHealthDepartmentSiteLink() {
        return healthDepartmentSiteLink;
    }

    @JsonProperty("healthDepartmentSiteLink")
    public void setHealthDepartmentSiteLink(String healthDepartmentSiteLink) {
        this.healthDepartmentSiteLink = healthDepartmentSiteLink;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(DataSources.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("governmentSiteLink");
        sb.append('=');
        sb.append(((this.governmentSiteLink == null)?"<null>":this.governmentSiteLink));
        sb.append(',');
        sb.append("healthDepartmentSiteLink");
        sb.append('=');
        sb.append(((this.healthDepartmentSiteLink == null)?"<null>":this.healthDepartmentSiteLink));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
