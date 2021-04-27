package pl.piasta.astroweatherextended.model.base;

import com.google.gson.annotations.SerializedName;

public class WindData {

    @SerializedName("speed")
    private Double mSpeed;
    @SerializedName("deg")
    private Double mDirection;

    public Double getSpeed() {
        return mSpeed;
    }

    public Double getDirection() {
        return mDirection;
    }

}
