package pl.piasta.astroweatherextended.ui.base;

import com.google.gson.annotations.SerializedName;

public enum MeasurementUnit {

    @SerializedName("metric")
    METRIC("°C", "m/s"),
    @SerializedName("imperial")
    IMPERIAL("°F", "mph"),
    @SerializedName("standard")
    STANDARD("K", "m/s");

    private static final String sHumidityUnit = "%";
    private static final String sPressureUnit = "hPa";
    private static final String sWindDirectionUnit = "°";
    private final String mTemperatureUnit;
    private final String mWindSpeedUnit;

    MeasurementUnit(String temperatureUnit, String windSpeedUnit) {
        this.mTemperatureUnit = temperatureUnit;
        this.mWindSpeedUnit = windSpeedUnit;
    }

    public String getHumidityUnit() {
        return sHumidityUnit;
    }

    public String getPressureUnit() {
        return sPressureUnit;
    }

    public String getWindDirectionUnit() {
        return sWindDirectionUnit;
    }

    public String getTemperatureUnit() {
        return mTemperatureUnit;
    }

    public String getWindSpeedUnit() {
        return mWindSpeedUnit;
    }
}
