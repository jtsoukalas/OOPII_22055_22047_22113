package gr.hua.oopii.travelAgency.API.iata;

import com.fasterxml.jackson.annotation.*;

public class Connection {
    private String type;
    private long ispCode;
    private String ispName;

    @JsonProperty("type")
    public String getType() { return type; }
    @JsonProperty("type")
    public void setType(String value) { this.type = value; }

    @JsonProperty("isp_code")
    public long getISPCode() { return ispCode; }
    @JsonProperty("isp_code")
    public void setISPCode(long value) { this.ispCode = value; }

    @JsonProperty("isp_name")
    public String getISPName() { return ispName; }
    @JsonProperty("isp_name")
    public void setISPName(String value) { this.ispName = value; }
}
