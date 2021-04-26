package pl.piasta.astroweatherextended.model.base;

import com.google.gson.annotations.SerializedName;

public class MainData {

    @SerializedName("temp")
    private Double mTemperature;
    @SerializedName("pressure")
    private Double mPressure;
    @SerializedName("humidity")
    private Double mHumidity;

    public Double getTemperature() {
        return mTemperature;
    }

    public Double getPressure() {
        return mPressure;
    }

    public Double getHumidity() {
        return mHumidity;
    }
}
