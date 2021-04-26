package pl.piasta.astroweatherextended.service;

import pl.piasta.astroweatherextended.model.CurrentWeatherDataResponse;
import pl.piasta.astroweatherextended.model.DailyForecastResponse;
import pl.piasta.astroweatherextended.ui.base.MeasurementUnit;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("data/2.5/weather")
    Call<CurrentWeatherDataResponse> getCurrentWeatherData(
            @Query("q") String query,
            @Query("units") MeasurementUnit unit,
            @Query("appid") String apiKey
    );

    @GET("data/2.5/forecast/daily")
    Call<DailyForecastResponse> getDailyForecast(
            @Query("q") String query,
            @Query("units") MeasurementUnit unit,
            @Query("appid") String apiKey
    );
}
