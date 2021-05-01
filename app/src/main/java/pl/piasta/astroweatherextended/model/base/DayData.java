package pl.piasta.astroweatherextended.model.base;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DayData {

    @SerializedName("temp")
    private TemperatureData mTemperatureData;
    @SerializedName("pressure")
    private Double mPressure;
    @SerializedName("humidity")
    private Double mHumidity;
    @SerializedName("weather")
    private List<WeatherData> mWeatherDataList;
    @SerializedName("speed")
    private Double mWindSpeed;
    @SerializedName("deg")
    private Double mWindDirection;

    public TemperatureData getTemperatureData() {
        return mTemperatureData;
    }

    public Double getPressure() {
        return mPressure;
    }

    public Double getHumidity() {
        return mHumidity;
    }

    public List<WeatherData> getWeatherDataList() {
        return mWeatherDataList;
    }

    public Double getWindSpeed() {
        return mWindSpeed;
    }

    public Double getWindDirection() {
        return mWindDirection;
    }

}
