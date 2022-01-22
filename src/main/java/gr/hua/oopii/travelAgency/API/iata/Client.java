package gr.hua.oopii.travelAgency.API.iata;

import com.fasterxml.jackson.annotation.*;

public class Client {
    private String ip;
    private Geo geo;
    private Connection connection;
    private Agent device;
    private Agent agent;
    private Karma karma;

    @JsonProperty("ip")
    public String getIP() { return ip; }
    @JsonProperty("ip")
    public void setIP(String value) { this.ip = value; }

    @JsonProperty("geo")
    public Geo getGeo() { return geo; }
    @JsonProperty("geo")
    public void setGeo(Geo value) { this.geo = value; }

    @JsonProperty("connection")
    public Connection getConnection() { return connection; }
    @JsonProperty("connection")
    public void setConnection(Connection value) { this.connection = value; }

    @JsonProperty("device")
    public Agent getDevice() { return device; }
    @JsonProperty("device")
    public void setDevice(Agent value) { this.device = value; }

    @JsonProperty("agent")
    public Agent getAgent() { return agent; }
    @JsonProperty("agent")
    public void setAgent(Agent value) { this.agent = value; }

    @JsonProperty("karma")
    public Karma getKarma() { return karma; }
    @JsonProperty("karma")
    public void setKarma(Karma value) { this.karma = value; }
}
