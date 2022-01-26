package gr.hua.oopii.travelAgency.API.iata;

import com.fasterxml.jackson.annotation.*;
import java.time.OffsetDateTime;
import java.util.UUID;

public class Key {
    private long id;
    private UUID apiKey;
    private String type;
    private Object trialPrice;
    private Object expired;
    private OffsetDateTime registered;
    private long limitsByHour;
    private long limitsByMinute;
    private long limitsByMonth;
    private long limitsTotal;

    @JsonProperty("id")
    public long getID() { return id; }
    @JsonProperty("id")
    public void setID(long value) { this.id = value; }

    @JsonProperty("api_key")
    public UUID getAPIKey() { return apiKey; }
    @JsonProperty("api_key")
    public void setAPIKey(UUID value) { this.apiKey = value; }

    @JsonProperty("type")
    public String getType() { return type; }
    @JsonProperty("type")
    public void setType(String value) { this.type = value; }

    @JsonProperty("trial_price")
    public Object getTrialPrice() { return trialPrice; }
    @JsonProperty("trial_price")
    public void setTrialPrice(Object value) { this.trialPrice = value; }

    @JsonProperty("expired")
    public Object getExpired() { return expired; }
    @JsonProperty("expired")
    public void setExpired(Object value) { this.expired = value; }

    @JsonProperty("registered")
    public OffsetDateTime getRegistered() { return registered; }
    @JsonProperty("registered")
    public void setRegistered(OffsetDateTime value) { this.registered = value; }

    @JsonProperty("limits_by_hour")
    public long getLimitsByHour() { return limitsByHour; }
    @JsonProperty("limits_by_hour")
    public void setLimitsByHour(long value) { this.limitsByHour = value; }

    @JsonProperty("limits_by_minute")
    public long getLimitsByMinute() { return limitsByMinute; }
    @JsonProperty("limits_by_minute")
    public void setLimitsByMinute(long value) { this.limitsByMinute = value; }

    @JsonProperty("limits_by_month")
    public long getLimitsByMonth() { return limitsByMonth; }
    @JsonProperty("limits_by_month")
    public void setLimitsByMonth(long value) { this.limitsByMonth = value; }

    @JsonProperty("limits_total")
    public long getLimitsTotal() { return limitsTotal; }
    @JsonProperty("limits_total")
    public void setLimitsTotal(long value) { this.limitsTotal = value; }
}
