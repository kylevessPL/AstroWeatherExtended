package pl.piasta.astroweatherextended.ui.base;

import com.google.gson.annotations.SerializedName;

public enum MeasurementUnit {

    @SerializedName("metric")
    METRIC("°C", "m/s"),
    @SerializedName("imperial")
    IMPERIAL("°F", "mph"),
    @SerializedName("standard")
    STANDARD("K", "m/s");

    private final String mTemperatureUnit;
    private final String mWindUnit;

    MeasurementUnit(String temperatureUnit, String windUnit) {
        this.mTemperatureUnit = temperatureUnit;
        this.mWindUnit = windUnit;
    }

    public String getTemperatureUnit() {
        return mTemperatureUnit;
    }

    public String getWindUnit() {
        return mWindUnit;
    }
}
