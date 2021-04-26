package pl.piasta.astroweatherextended.model;

import com.google.gson.annotations.SerializedName;

public class CoordinatesResponse {

    @SerializedName("name")
    private String town;
    @SerializedName("lat")
    private String latitude;
    @SerializedName("lon")
    private String longtitude;
    @SerializedName("country")
    private String countryCode;

    public String getTown() {
        return town;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public String getCountryCode() {
        return countryCode;
    }
}
