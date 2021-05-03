package pl.piasta.astroweatherextended.model;

import com.google.gson.annotations.SerializedName;

import pl.piasta.astroweatherextended.model.base.LocalNamesData;

public class GeocodingResponse {

    @SerializedName("name")
    private String mTown;
    @SerializedName("local_names")
    private LocalNamesData mLocalNamesData;

    @SerializedName("lat")
    private Double mLatitude;
    @SerializedName("lon")
    private Double mLongtitude;
    @SerializedName("country")
    private String mCountryCode;

    public String getTown() {
        return mTown;
    }

    public LocalNamesData getLocalNamesData() {
        return mLocalNamesData;
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

    public void setLatitude(final Double latitude) {
        mLatitude = latitude;
    }

    public void setLongtitude(final Double longtitude) {
        mLongtitude = longtitude;
    }
}
