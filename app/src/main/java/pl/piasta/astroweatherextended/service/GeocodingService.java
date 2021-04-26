package pl.piasta.astroweatherextended.service;

import pl.piasta.astroweatherextended.model.CoordinatesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeocodingService {

    @GET("geo/1.0/direct?limit=1")
    Call<CoordinatesResponse> getCoordinates(
            @Query("q") String query,
            @Query("appid") String apiKey
    );
}
