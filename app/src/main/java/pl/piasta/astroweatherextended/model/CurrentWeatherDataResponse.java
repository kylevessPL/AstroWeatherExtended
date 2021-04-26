package pl.piasta.astroweatherextended.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import pl.piasta.astroweatherextended.model.base.MainData;
import pl.piasta.astroweatherextended.model.base.WeatherData;
import pl.piasta.astroweatherextended.model.base.WindData;

public class CurrentWeatherDataResponse {

    @SerializedName("weather")
    private List<WeatherData> mWeatherDataList;
    @SerializedName("main")
    private MainData mMainData;
    @SerializedName("wind")
    private WindData mWindData;

    public List<WeatherData> getWeatherDataList() {
        return mWeatherDataList;
    }

    public MainData getMainData() {
        return mMainData;
    }

    public WindData getWindData() {
        return mWindData;
    }
}
