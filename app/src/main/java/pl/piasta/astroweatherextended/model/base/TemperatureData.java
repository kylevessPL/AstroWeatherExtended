package pl.piasta.astroweatherextended.model.base;

import com.google.gson.annotations.SerializedName;

public class TemperatureData {

    @SerializedName("min")
    private Double mMinTemperature;
    @SerializedName("max")
    private Double mMaxTemperature;

    public Double getMinTemperature() {
        return mMinTemperature;
    }

    public Double getMaxTemperature() {
        return mMaxTemperature;
    }
}
