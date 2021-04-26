package pl.piasta.astroweatherextended.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import pl.piasta.astroweatherextended.model.base.DayData;

public class DailyForecastResponse {

    @SerializedName("list")
    private List<DayData> mDayDataList;

    public List<DayData> getDayDataList() {
        return mDayDataList;
    }
}
