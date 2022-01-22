
package gr.hua.oopii.travelAgency.API.covidRestrictions;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "text",
    "documentRequired",
    "travelDocumentLink",
    "healthDocumentLink",
    "date"
})
@Generated("jsonschema2pojo")
public class DeclarartionDocumentations {

    @JsonProperty("text")
    private String text;
    @JsonProperty("documentRequired")
    private String documentRequired;
    @JsonProperty("travelDocumentLink")
    private String travelDocumentLink;
    @JsonProperty("healthDocumentLink")
    private String healthDocumentLink;
    @JsonProperty("date")
    private String date;

    @JsonProperty("text")
    public String getText() {
        return text;
    }

    @JsonProperty("text")
    public void setText(String text) {
        this.text = text;
    }

    @JsonProperty("documentRequired")
    public String getDocumentRequired() {
        return documentRequired;
    }

    @JsonProperty("documentRequired")
    public void setDocumentRequired(String documentRequired) {
        this.documentRequired = documentRequired;
    }

    @JsonProperty("travelDocumentLink")
    public String getTravelDocumentLink() {
        return travelDocumentLink;
    }

    @JsonProperty("travelDocumentLink")
    public void setTravelDocumentLink(String travelDocumentLink) {
        this.travelDocumentLink = travelDocumentLink;
    }

    @JsonProperty("healthDocumentLink")
    public String getHealthDocumentLink() {
        return healthDocumentLink;
    }

    @JsonProperty("healthDocumentLink")
    public void setHealthDocumentLink(String healthDocumentLink) {
        this.healthDocumentLink = healthDocumentLink;
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
        sb.append(DeclarartionDocumentations.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("text");
        sb.append('=');
        sb.append(((this.text == null)?"<null>":this.text));
        sb.append(',');
        sb.append("documentRequired");
        sb.append('=');
        sb.append(((this.documentRequired == null)?"<null>":this.documentRequired));
        sb.append(',');
        sb.append("travelDocumentLink");
        sb.append('=');
        sb.append(((this.travelDocumentLink == null)?"<null>":this.travelDocumentLink));
        sb.append(',');
        sb.append("healthDocumentLink");
        sb.append('=');
        sb.append(((this.healthDocumentLink == null)?"<null>":this.healthDocumentLink));
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
