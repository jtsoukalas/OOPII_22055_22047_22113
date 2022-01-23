
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
    "transportation",
    "declarationDocuments",
    "entry",
    "diseaseTesting",
    "tracingApplication",
    "quarantineModality",
    "mask",
    "exit",
    "otherRestriction",
    "diseaseVaccination"
})
@Generated("jsonschema2pojo")
public class AreaAccessRestriction {

    @JsonProperty("transportation")
    private Transportation transportation;
    @JsonProperty("declarationDocuments")
    private DeclarationDocuments declarationDocuments;
    @JsonProperty("entry")
    private Entry entry;
    @JsonProperty("diseaseTesting")
    private DiseaseTesting diseaseTesting;
    @JsonProperty("tracingApplication")
    private TracingApplication tracingApplication;
    @JsonProperty("quarantineModality")
    private QuarantineModality quarantineModality;
    @JsonProperty("mask")
    private Mask mask;
    @JsonProperty("exit")
    private Exit exit;
    @JsonProperty("otherRestriction")
    private OtherRestriction otherRestriction;
    @JsonProperty("diseaseVaccination")
    private DiseaseVaccination diseaseVaccination;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("transportation")
    public Transportation getTransportation() {
        return transportation;
    }

    @JsonProperty("transportation")
    public void setTransportation(Transportation transportation) {
        this.transportation = transportation;
    }

    @JsonProperty("declarationDocuments")
    public DeclarationDocuments getDeclarationDocuments() {
        return declarationDocuments;
    }

    @JsonProperty("declarationDocuments")
    public void setDeclarationDocuments(DeclarationDocuments declarationDocuments) {
        this.declarationDocuments = declarationDocuments;
    }

    @JsonProperty("entry")
    public Entry getEntry() {
        return entry;
    }

    @JsonProperty("entry")
    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    @JsonProperty("diseaseTesting")
    public DiseaseTesting getDiseaseTesting() {
        return diseaseTesting;
    }

    @JsonProperty("diseaseTesting")
    public void setDiseaseTesting(DiseaseTesting diseaseTesting) {
        this.diseaseTesting = diseaseTesting;
    }

    @JsonProperty("tracingApplication")
    public TracingApplication getTracingApplication() {
        return tracingApplication;
    }

    @JsonProperty("tracingApplication")
    public void setTracingApplication(TracingApplication tracingApplication) {
        this.tracingApplication = tracingApplication;
    }

    @JsonProperty("quarantineModality")
    public QuarantineModality getQuarantineModality() {
        return quarantineModality;
    }

    @JsonProperty("quarantineModality")
    public void setQuarantineModality(QuarantineModality quarantineModality) {
        this.quarantineModality = quarantineModality;
    }

    @JsonProperty("mask")
    public Mask getMask() {
        return mask;
    }

    @JsonProperty("mask")
    public void setMask(Mask mask) {
        this.mask = mask;
    }

    @JsonProperty("exit")
    public Exit getExit() {
        return exit;
    }

    @JsonProperty("exit")
    public void setExit(Exit exit) {
        this.exit = exit;
    }

    @JsonProperty("otherRestriction")
    public OtherRestriction getOtherRestriction() {
        return otherRestriction;
    }

    @JsonProperty("otherRestriction")
    public void setOtherRestriction(OtherRestriction otherRestriction) {
        this.otherRestriction = otherRestriction;
    }

    @JsonProperty("diseaseVaccination")
    public DiseaseVaccination getDiseaseVaccination() {
        return diseaseVaccination;
    }

    @JsonProperty("diseaseVaccination")
    public void setDiseaseVaccination(DiseaseVaccination diseaseVaccination) {
        this.diseaseVaccination = diseaseVaccination;
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
        sb.append(AreaAccessRestriction.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("transportation");
        sb.append('=');
        sb.append(((this.transportation == null)?"<null>":this.transportation));
        sb.append(',');
        sb.append("declarationDocuments");
        sb.append('=');
        sb.append(((this.declarationDocuments == null)?"<null>":this.declarationDocuments));
        sb.append(',');
        sb.append("entry");
        sb.append('=');
        sb.append(((this.entry == null)?"<null>":this.entry));
        sb.append(',');
        sb.append("diseaseTesting");
        sb.append('=');
        sb.append(((this.diseaseTesting == null)?"<null>":this.diseaseTesting));
        sb.append(',');
        sb.append("tracingApplication");
        sb.append('=');
        sb.append(((this.tracingApplication == null)?"<null>":this.tracingApplication));
        sb.append(',');
        sb.append("quarantineModality");
        sb.append('=');
        sb.append(((this.quarantineModality == null)?"<null>":this.quarantineModality));
        sb.append(',');
        sb.append("mask");
        sb.append('=');
        sb.append(((this.mask == null)?"<null>":this.mask));
        sb.append(',');
        sb.append("exit");
        sb.append('=');
        sb.append(((this.exit == null)?"<null>":this.exit));
        sb.append(',');
        sb.append("otherRestriction");
        sb.append('=');
        sb.append(((this.otherRestriction == null)?"<null>":this.otherRestriction));
        sb.append(',');
        sb.append("diseaseVaccination");
        sb.append('=');
        sb.append(((this.diseaseVaccination == null)?"<null>":this.diseaseVaccination));
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
