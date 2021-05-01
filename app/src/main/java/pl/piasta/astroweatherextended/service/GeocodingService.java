package pl.piasta.astroweatherextended.service;

import pl.piasta.astroweatherextended.model.GeocodingResponse;
import pl.piasta.astroweatherextended.model.ReverseGeocodingResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeocodingService {

    @GET("geo/1.0/direct?limit=1")
    Call<GeocodingResponse> getCoordinates(
            @Query("q") String query,
            @Query("appid") String apiKey
    );

    @GET("geo/1.0/reverse?limit=1")
    Call<ReverseGeocodingResponse> getTown(
            @Query("lat") Double latitude,
            @Query("lon") Double longtitude,
            @Query("appid") String apiKey
    );
}
