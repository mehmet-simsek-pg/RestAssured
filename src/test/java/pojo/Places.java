package pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Places {

    private String placeName;
    private String longitude;
    private String state;
    private String latitude;
    private String stateAbbreviation;

    public String getPlaceName() {
        return placeName;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getState() {
        return state;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getStateAbbreviation() {
        return stateAbbreviation;
    }

    @JsonProperty("place name")
    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @JsonProperty("state abbreviation")
    public void setStateAbbreviation(String stateAbbreviation) {
        this.stateAbbreviation = stateAbbreviation;
    }

    @Override
    public String toString() {
        return "Places{" +
                "placeName='" + placeName + '\'' +
                ", longitude='" + longitude + '\'' +
                ", state='" + state + '\'' +
                ", latitude='" + latitude + '\'' +
                ", stateAbbreviation='" + stateAbbreviation + '\'' +
                '}';
    }
}
