package pl.piasta.astroweatherextended.ui.main;

import com.google.gson.annotations.SerializedName;

public enum TemperatureUnit {

    @SerializedName("metric")
    CELSIUS,
    @SerializedName("imperial")
    FAHRENHEIT,
    @SerializedName("standard")
    KELVIN
}
