package gr.hua.oopii.travelAgency.API.iata;

import com.fasterxml.jackson.annotation.*;

public class Request {
    private String lang;
    private String currency;
    private long time;
    private String id;
    private String server;
    private String host;
    private long pid;
    private Key key;
    private Params params;
    private long version;
    private String method;
    private Client client;

    @JsonProperty("lang")
    public String getLang() { return lang; }
    @JsonProperty("lang")
    public void setLang(String value) { this.lang = value; }

    @JsonProperty("currency")
    public String getCurrency() { return currency; }
    @JsonProperty("currency")
    public void setCurrency(String value) { this.currency = value; }

    @JsonProperty("time")
    public long getTime() { return time; }
    @JsonProperty("time")
    public void setTime(long value) { this.time = value; }

    @JsonProperty("id")
    public String getID() { return id; }
    @JsonProperty("id")
    public void setID(String value) { this.id = value; }

    @JsonProperty("server")
    public String getServer() { return server; }
    @JsonProperty("server")
    public void setServer(String value) { this.server = value; }

    @JsonProperty("host")
    public String getHost() { return host; }
    @JsonProperty("host")
    public void setHost(String value) { this.host = value; }

    @JsonProperty("pid")
    public long getPID() { return pid; }
    @JsonProperty("pid")
    public void setPID(long value) { this.pid = value; }

    @JsonProperty("key")
    public Key getKey() { return key; }
    @JsonProperty("key")
    public void setKey(Key value) { this.key = value; }

    @JsonProperty("params")
    public Params getParams() { return params; }
    @JsonProperty("params")
    public void setParams(Params value) { this.params = value; }

    @JsonProperty("version")
    public long getVersion() { return version; }
    @JsonProperty("version")
    public void setVersion(long value) { this.version = value; }

    @JsonProperty("method")
    public String getMethod() { return method; }
    @JsonProperty("method")
    public void setMethod(String value) { this.method = value; }

    @JsonProperty("client")
    public Client getClient() { return client; }
    @JsonProperty("client")
    public void setClient(Client value) { this.client = value; }
}
