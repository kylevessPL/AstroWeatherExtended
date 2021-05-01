package pl.piasta.astroweatherextended.model.base;

import com.google.gson.annotations.SerializedName;

public class LocalNamesData {

    @SerializedName("pl")
    String polishName;

    public String getPolishName() {
        return polishName;
    }
}
