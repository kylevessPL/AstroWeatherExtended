package pl.piasta.astroweatherextended.service;

import java.util.List;

import pl.piasta.astroweatherextended.model.GeocodingResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeocodingService {

    @GET("geo/1.0/direct?limit=1")
    Call<List<GeocodingResponse>> getCoordinates(
            @Query("q") String query,
            @Query("appid") String apiKey
    );

    @GET("geo/1.0/reverse?limit=1")
    Call<List<GeocodingResponse>> getTown(
            @Query("lat") Double latitude,
            @Query("lon") Double longtitude,
            @Query("appid") String apiKey
    );
}
