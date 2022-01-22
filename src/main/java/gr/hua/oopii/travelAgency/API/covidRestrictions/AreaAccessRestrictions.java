
package gr.hua.oopii.travelAgency.API.covidRestrictions;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "transportation",
    "declarartionDocumentations",
    "entry",
    "diseaseTesting",
    "tracingApplication",
    "quarantineModality",
    "masks",
    "exit",
    "otherRestrictions"
})
@Generated("jsonschema2pojo")
public class AreaAccessRestrictions {

    @JsonProperty("transportation")
    private Transportation transportation;
    @JsonProperty("declarartionDocumentations")
    private DeclarartionDocumentations declarartionDocumentations;
    @JsonProperty("entry")
    private Entry entry;
    @JsonProperty("diseaseTesting")
    private DiseaseTesting diseaseTesting;
    @JsonProperty("tracingApplication")
    private TracingApplication tracingApplication;
    @JsonProperty("quarantineModality")
    private QuarantineModality quarantineModality;
    @JsonProperty("masks")
    private Masks masks;
    @JsonProperty("exit")
    private Exit exit;
    @JsonProperty("otherRestrictions")
    private OtherRestrictions otherRestrictions;

    @JsonProperty("transportation")
    public Transportation getTransportation() {
        return transportation;
    }

    @JsonProperty("transportation")
    public void setTransportation(Transportation transportation) {
        this.transportation = transportation;
    }

    @JsonProperty("declarartionDocumentations")
    public DeclarartionDocumentations getDeclarartionDocumentations() {
        return declarartionDocumentations;
    }

    @JsonProperty("declarartionDocumentations")
    public void setDeclarartionDocumentations(DeclarartionDocumentations declarartionDocumentations) {
        this.declarartionDocumentations = declarartionDocumentations;
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

    @JsonProperty("masks")
    public Masks getMasks() {
        return masks;
    }

    @JsonProperty("masks")
    public void setMasks(Masks masks) {
        this.masks = masks;
    }

    @JsonProperty("exit")
    public Exit getExit() {
        return exit;
    }

    @JsonProperty("exit")
    public void setExit(Exit exit) {
        this.exit = exit;
    }

    @JsonProperty("otherRestrictions")
    public OtherRestrictions getOtherRestrictions() {
        return otherRestrictions;
    }

    @JsonProperty("otherRestrictions")
    public void setOtherRestrictions(OtherRestrictions otherRestrictions) {
        this.otherRestrictions = otherRestrictions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(AreaAccessRestrictions.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("transportation");
        sb.append('=');
        sb.append(((this.transportation == null)?"<null>":this.transportation));
        sb.append(',');
        sb.append("declarartionDocumentations");
        sb.append('=');
        sb.append(((this.declarartionDocumentations == null)?"<null>":this.declarartionDocumentations));
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
        sb.append("masks");
        sb.append('=');
        sb.append(((this.masks == null)?"<null>":this.masks));
        sb.append(',');
        sb.append("exit");
        sb.append('=');
        sb.append(((this.exit == null)?"<null>":this.exit));
        sb.append(',');
        sb.append("otherRestrictions");
        sb.append('=');
        sb.append(((this.otherRestrictions == null)?"<null>":this.otherRestrictions));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
