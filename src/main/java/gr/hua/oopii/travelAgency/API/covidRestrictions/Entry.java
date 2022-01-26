
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
    "text",
    "ban",
    "throughDate",
    "rules",
    "exemptions",
    "bannedArea",
    "borderBan"
})
@Generated("jsonschema2pojo")
public class Entry {

    @JsonProperty("date")
    private String date;
    @JsonProperty("text")
    private String text;
    @JsonProperty("ban")
    private String ban;
    @JsonProperty("throughDate")
    private String throughDate;
    @JsonProperty("rules")
    private String rules;
    @JsonProperty("exemptions")
    private String exemptions;
    @JsonProperty("bannedArea")
    private List<BannedArea> bannedArea = null;
    @JsonProperty("borderBan")
    private List<BorderBan> borderBan = null;
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

    @JsonProperty("ban")
    public String getBan() {
        return ban;
    }

    @JsonProperty("ban")
    public void setBan(String ban) {
        this.ban = ban;
    }

    @JsonProperty("throughDate")
    public String getThroughDate() {
        return throughDate;
    }

    @JsonProperty("throughDate")
    public void setThroughDate(String throughDate) {
        this.throughDate = throughDate;
    }

    @JsonProperty("rules")
    public String getRules() {
        return rules;
    }

    @JsonProperty("rules")
    public void setRules(String rules) {
        this.rules = rules;
    }

    @JsonProperty("exemptions")
    public String getExemptions() {
        return exemptions;
    }

    @JsonProperty("exemptions")
    public void setExemptions(String exemptions) {
        this.exemptions = exemptions;
    }

    @JsonProperty("bannedArea")
    public List<BannedArea> getBannedArea() {
        return bannedArea;
    }

    @JsonProperty("bannedArea")
    public void setBannedArea(List<BannedArea> bannedArea) {
        this.bannedArea = bannedArea;
    }

    @JsonProperty("borderBan")
    public List<BorderBan> getBorderBan() {
        return borderBan;
    }

    @JsonProperty("borderBan")
    public void setBorderBan(List<BorderBan> borderBan) {
        this.borderBan = borderBan;
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
        sb.append(Entry.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("date");
        sb.append('=');
        sb.append(((this.date == null)?"<null>":this.date));
        sb.append(',');
        sb.append("text");
        sb.append('=');
        sb.append(((this.text == null)?"<null>":this.text));
        sb.append(',');
        sb.append("ban");
        sb.append('=');
        sb.append(((this.ban == null)?"<null>":this.ban));
        sb.append(',');
        sb.append("throughDate");
        sb.append('=');
        sb.append(((this.throughDate == null)?"<null>":this.throughDate));
        sb.append(',');
        sb.append("rules");
        sb.append('=');
        sb.append(((this.rules == null)?"<null>":this.rules));
        sb.append(',');
        sb.append("exemptions");
        sb.append('=');
        sb.append(((this.exemptions == null)?"<null>":this.exemptions));
        sb.append(',');
        sb.append("bannedArea");
        sb.append('=');
        sb.append(((this.bannedArea == null)?"<null>":this.bannedArea));
        sb.append(',');
        sb.append("borderBan");
        sb.append('=');
        sb.append(((this.borderBan == null)?"<null>":this.borderBan));
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
