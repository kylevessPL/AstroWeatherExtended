package pl.piasta.astroweatherextended.model;

import com.google.gson.annotations.SerializedName;

public class GeocodingResponse {

    @SerializedName("name")
    private String mTown;
    @SerializedName("lat")
    private Double mLatitude;
    @SerializedName("lon")
    private Double mLongtitude;
    @SerializedName("country")
    private String mCountryCode;

    public String getTown() {
        return mTown;
    }

    public Double getLatitude() {
        return mLatitude;
    }

    public Double getLongtitude() {
        return mLongtitude;
    }

    public String getCountryCode() {
        return mCountryCode;
    }
}
