
package gr.hua.oopii.travelAgency.API.covidRestrictions;

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
    "date",
    "isRequired",
    "referenceLink",
    "acceptedCertificates",
    "qualifiedVaccines",
    "policy"
})
@Generated("jsonschema2pojo")
public class DiseaseVaccination {

    @JsonProperty("date")
    private String date;
    @JsonProperty("isRequired")
    private String isRequired;
    @JsonProperty("referenceLink")
    private String referenceLink;
    @JsonProperty("acceptedCertificates")
    private List<String> acceptedCertificates = null;
    @JsonProperty("qualifiedVaccines")
    private List<String> qualifiedVaccines = null;
    @JsonProperty("policy")
    private String policy;
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

    @JsonProperty("isRequired")
    public String getIsRequired() {
        return isRequired;
    }

    @JsonProperty("isRequired")
    public void setIsRequired(String isRequired) {
        this.isRequired = isRequired;
    }

    @JsonProperty("referenceLink")
    public String getReferenceLink() {
        return referenceLink;
    }

    @JsonProperty("referenceLink")
    public void setReferenceLink(String referenceLink) {
        this.referenceLink = referenceLink;
    }

    @JsonProperty("acceptedCertificates")
    public List<String> getAcceptedCertificates() {
        return acceptedCertificates;
    }

    @JsonProperty("acceptedCertificates")
    public void setAcceptedCertificates(List<String> acceptedCertificates) {
        this.acceptedCertificates = acceptedCertificates;
    }

    @JsonProperty("qualifiedVaccines")
    public List<String> getQualifiedVaccines() {
        return qualifiedVaccines;
    }

    @JsonProperty("qualifiedVaccines")
    public void setQualifiedVaccines(List<String> qualifiedVaccines) {
        this.qualifiedVaccines = qualifiedVaccines;
    }

    @JsonProperty("policy")
    public String getPolicy() {
        return policy;
    }

    @JsonProperty("policy")
    public void setPolicy(String policy) {
        this.policy = policy;
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
        sb.append(DiseaseVaccination.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("date");
        sb.append('=');
        sb.append(((this.date == null)?"<null>":this.date));
        sb.append(',');
        sb.append("isRequired");
        sb.append('=');
        sb.append(((this.isRequired == null)?"<null>":this.isRequired));
        sb.append(',');
        sb.append("referenceLink");
        sb.append('=');
        sb.append(((this.referenceLink == null)?"<null>":this.referenceLink));
        sb.append(',');
        sb.append("acceptedCertificates");
        sb.append('=');
        sb.append(((this.acceptedCertificates == null)?"<null>":this.acceptedCertificates));
        sb.append(',');
        sb.append("qualifiedVaccines");
        sb.append('=');
        sb.append(((this.qualifiedVaccines == null)?"<null>":this.qualifiedVaccines));
        sb.append(',');
        sb.append("policy");
        sb.append('=');
        sb.append(((this.policy == null)?"<null>":this.policy));
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
