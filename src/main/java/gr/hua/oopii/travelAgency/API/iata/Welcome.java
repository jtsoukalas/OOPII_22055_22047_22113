package gr.hua.oopii.travelAgency.API.iata;

import com.fasterxml.jackson.annotation.*;

public class Welcome {
    private Request request;
    private Response response;
    private String terms;

    @JsonProperty("request")
    public Request getRequest() { return request; }
    @JsonProperty("request")
    public void setRequest(Request value) { this.request = value; }

    @JsonProperty("response")
    public Response getResponse() { return response; }
    @JsonProperty("response")
    public void setResponse(Response value) { this.response = value; }

    @JsonProperty("terms")
    public String getTerms() { return terms; }
    @JsonProperty("terms")
    public void setTerms(String value) { this.terms = value; }
}
