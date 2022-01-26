package gr.hua.oopii.travelAgency.API.iata;

import com.fasterxml.jackson.annotation.*;

public class Karma {
    private boolean isBlocked;
    private boolean isCrawler;
    private boolean isBot;
    private boolean isFriend;
    private boolean isRegular;

    @JsonProperty("is_blocked")
    public boolean getIsBlocked() { return isBlocked; }
    @JsonProperty("is_blocked")
    public void setIsBlocked(boolean value) { this.isBlocked = value; }

    @JsonProperty("is_crawler")
    public boolean getIsCrawler() { return isCrawler; }
    @JsonProperty("is_crawler")
    public void setIsCrawler(boolean value) { this.isCrawler = value; }

    @JsonProperty("is_bot")
    public boolean getIsBot() { return isBot; }
    @JsonProperty("is_bot")
    public void setIsBot(boolean value) { this.isBot = value; }

    @JsonProperty("is_friend")
    public boolean getIsFriend() { return isFriend; }
    @JsonProperty("is_friend")
    public void setIsFriend(boolean value) { this.isFriend = value; }

    @JsonProperty("is_regular")
    public boolean getIsRegular() { return isRegular; }
    @JsonProperty("is_regular")
    public void setIsRegular(boolean value) { this.isRegular = value; }
}
