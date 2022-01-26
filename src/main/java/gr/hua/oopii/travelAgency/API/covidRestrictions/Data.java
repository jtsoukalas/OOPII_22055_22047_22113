
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
    "area",
    "summary",
    "diseaseRiskLevel",
    "diseaseInfection",
    "diseaseCases",
    "hotspots",
    "dataSources",
    "areaRestrictions",
    "areaAccessRestriction",
    "areaPolicy",
    "relatedArea",
    "areaVaccinated",
    "type"
})
@Generated("jsonschema2pojo")
public class Data {

    @JsonProperty("area")
    private Area area;
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("diseaseRiskLevel")
    private String diseaseRiskLevel;
    @JsonProperty("diseaseInfection")
    private DiseaseInfection diseaseInfection;
    @JsonProperty("diseaseCases")
    private DiseaseCases diseaseCases;
    @JsonProperty("hotspots")
    private String hotspots;
    @JsonProperty("dataSources")
    private DataSources dataSources;
    @JsonProperty("areaRestrictions")
    private List<AreaRestriction> areaRestrictions = null;
    @JsonProperty("areaAccessRestriction")
    private AreaAccessRestriction areaAccessRestriction;
    @JsonProperty("areaPolicy")
    private AreaPolicy areaPolicy;
    @JsonProperty("relatedArea")
    private List<RelatedArea> relatedArea = null;
    @JsonProperty("areaVaccinated")
    private List<AreaVaccinated> areaVaccinated = null;
    @JsonProperty("type")
    private String type;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("area")
    public Area getArea() {
        return area;
    }

    @JsonProperty("area")
    public void setArea(Area area) {
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

    @JsonProperty("diseaseInfection")
    public DiseaseInfection getDiseaseInfection() {
        return diseaseInfection;
    }

    @JsonProperty("diseaseInfection")
    public void setDiseaseInfection(DiseaseInfection diseaseInfection) {
        this.diseaseInfection = diseaseInfection;
    }

    @JsonProperty("diseaseCases")
    public DiseaseCases getDiseaseCases() {
        return diseaseCases;
    }

    @JsonProperty("diseaseCases")
    public void setDiseaseCases(DiseaseCases diseaseCases) {
        this.diseaseCases = diseaseCases;
    }

    @JsonProperty("hotspots")
    public String getHotspots() {
        return hotspots;
    }

    @JsonProperty("hotspots")
    public void setHotspots(String hotspots) {
        this.hotspots = hotspots;
    }

    @JsonProperty("dataSources")
    public DataSources getDataSources() {
        return dataSources;
    }

    @JsonProperty("dataSources")
    public void setDataSources(DataSources dataSources) {
        this.dataSources = dataSources;
    }

    @JsonProperty("areaRestrictions")
    public List<AreaRestriction> getAreaRestrictions() {
        return areaRestrictions;
    }

    @JsonProperty("areaRestrictions")
    public void setAreaRestrictions(List<AreaRestriction> areaRestrictions) {
        this.areaRestrictions = areaRestrictions;
    }

    @JsonProperty("areaAccessRestriction")
    public AreaAccessRestriction getAreaAccessRestriction() {
        return areaAccessRestriction;
    }

    @JsonProperty("areaAccessRestriction")
    public void setAreaAccessRestriction(AreaAccessRestriction areaAccessRestriction) {
        this.areaAccessRestriction = areaAccessRestriction;
    }

    @JsonProperty("areaPolicy")
    public AreaPolicy getAreaPolicy() {
        return areaPolicy;
    }

    @JsonProperty("areaPolicy")
    public void setAreaPolicy(AreaPolicy areaPolicy) {
        this.areaPolicy = areaPolicy;
    }

    @JsonProperty("relatedArea")
    public List<RelatedArea> getRelatedArea() {
        return relatedArea;
    }

    @JsonProperty("relatedArea")
    public void setRelatedArea(List<RelatedArea> relatedArea) {
        this.relatedArea = relatedArea;
    }

    @JsonProperty("areaVaccinated")
    public List<AreaVaccinated> getAreaVaccinated() {
        return areaVaccinated;
    }

    @JsonProperty("areaVaccinated")
    public void setAreaVaccinated(List<AreaVaccinated> areaVaccinated) {
        this.areaVaccinated = areaVaccinated;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
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
        sb.append(Data.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        sb.append("diseaseInfection");
        sb.append('=');
        sb.append(((this.diseaseInfection == null)?"<null>":this.diseaseInfection));
        sb.append(',');
        sb.append("diseaseCases");
        sb.append('=');
        sb.append(((this.diseaseCases == null)?"<null>":this.diseaseCases));
        sb.append(',');
        sb.append("hotspots");
        sb.append('=');
        sb.append(((this.hotspots == null)?"<null>":this.hotspots));
        sb.append(',');
        sb.append("dataSources");
        sb.append('=');
        sb.append(((this.dataSources == null)?"<null>":this.dataSources));
        sb.append(',');
        sb.append("areaRestrictions");
        sb.append('=');
        sb.append(((this.areaRestrictions == null)?"<null>":this.areaRestrictions));
        sb.append(',');
        sb.append("areaAccessRestriction");
        sb.append('=');
        sb.append(((this.areaAccessRestriction == null)?"<null>":this.areaAccessRestriction));
        sb.append(',');
        sb.append("areaPolicy");
        sb.append('=');
        sb.append(((this.areaPolicy == null)?"<null>":this.areaPolicy));
        sb.append(',');
        sb.append("relatedArea");
        sb.append('=');
        sb.append(((this.relatedArea == null)?"<null>":this.relatedArea));
        sb.append(',');
        sb.append("areaVaccinated");
        sb.append('=');
        sb.append(((this.areaVaccinated == null)?"<null>":this.areaVaccinated));
        sb.append(',');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
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
