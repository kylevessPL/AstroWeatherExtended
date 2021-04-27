package pl.piasta.astroweatherextended.model.base;

import com.google.gson.annotations.SerializedName;

public class WeatherData {

    @SerializedName("description")
    private String mDescription;
    @SerializedName("icon")
    private String mIcon;

    public String getDescription() {
        return mDescription;
    }

    public String getIcon() {
        return mIcon;
    }
}
