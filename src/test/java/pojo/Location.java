package pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Location {

    private String postCode;
    private String country;
    private String countryAbbreviation;
    private List<Places> places;

    public String getPostCode() {
        return postCode;
    }

    public String getCountry() {
        return country;
    }

    public String getCountryAbbreviation() {
        return countryAbbreviation;
    }

    public List<Places> getPlaces() {
        return places;
    }

    @JsonProperty("post code")
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("country abbreviation")
    public void setCountryAbbreviation(String countryAbbreviation) {
        this.countryAbbreviation = countryAbbreviation;
    }

    public void setPlaces(List<Places> places) {
        this.places = places;
    }

    @Override
    public String toString() {
        return "Location{" +
                "postCode='" + postCode + '\'' +
                ", country='" + country + '\'' +
                ", countryAbbreviation='" + countryAbbreviation + '\'' +
                ", places=" + places +
                '}';
    }
}
