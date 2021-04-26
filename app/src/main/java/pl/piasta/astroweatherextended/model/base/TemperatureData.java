package pl.piasta.astroweatherextended.model.base;

import com.google.gson.annotations.SerializedName;

public class TemperatureData {

    @SerializedName("day")
    private Double mDayTemperature;
    @SerializedName("night")
    private Double mNightTemperature;

    public Double getDayTemperature() {
        return mDayTemperature;
    }

    public Double getNightTemperature() {
        return mNightTemperature;
    }
}
