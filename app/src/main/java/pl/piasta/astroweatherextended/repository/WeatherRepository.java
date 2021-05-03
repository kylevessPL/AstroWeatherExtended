package pl.piasta.astroweatherextended.repository;

import androidx.annotation.NonNull;

import pl.piasta.astroweatherextended.model.CurrentWeatherDataResponse;
import pl.piasta.astroweatherextended.model.DailyForecastResponse;
import pl.piasta.astroweatherextended.repository.retrofit.RetrofitRequest;
import pl.piasta.astroweatherextended.service.WeatherService;
import pl.piasta.astroweatherextended.ui.base.MeasurementUnit;
import pl.piasta.astroweatherextended.util.SingleLiveEvent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepository {

    private final WeatherService mWeatherService;

    private final SingleLiveEvent<CurrentWeatherDataResponse> mCurrentWeatherDataResponse;
    private final SingleLiveEvent<DailyForecastResponse> mDailyForecastResponse;

    public WeatherRepository() {
        mWeatherService = RetrofitRequest.getRetrofitInstance().create(WeatherService.class);
        mCurrentWeatherDataResponse = new SingleLiveEvent<>();
        mDailyForecastResponse = new SingleLiveEvent<>();
    }

    public void fetchCurrentWeatherData(String query, MeasurementUnit unit, String apiKey) {
        mWeatherService.getCurrentWeatherData(query, unit, apiKey)
                .enqueue(new Callback<CurrentWeatherDataResponse>() {

                    @Override
                    public void onResponse(
                            @NonNull Call<CurrentWeatherDataResponse> call,
                            @NonNull Response<CurrentWeatherDataResponse> response
                    ) {
                        if (response.body() != null) {
                            mCurrentWeatherDataResponse.setValue(response.body());
                        } else {
                            mCurrentWeatherDataResponse.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<CurrentWeatherDataResponse> call,
                            @NonNull Throwable t
                    ) {
                        mCurrentWeatherDataResponse.setValue(null);
                    }
                });
    }

    public void fetchDailyForecast(String query, MeasurementUnit unit, String apiKey) {
        mWeatherService.getDailyForecast(query, unit, apiKey)
                .enqueue(new Callback<DailyForecastResponse>() {

                    @Override
                    public void onResponse(
                            @NonNull Call<DailyForecastResponse> call,
                            @NonNull Response<DailyForecastResponse> response
                    ) {
                        if (response.body() != null) {
                            mDailyForecastResponse.setValue(response.body());
                        } else {
                            mDailyForecastResponse.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<DailyForecastResponse> call,
                            @NonNull Throwable t
                    ) {
                        mDailyForecastResponse.setValue(null);
                    }
                });
    }

    public SingleLiveEvent<CurrentWeatherDataResponse> getCurrentWeatherDataResponse() {
        return mCurrentWeatherDataResponse;
    }

    public SingleLiveEvent<DailyForecastResponse> getDailyForecastResponse() {
        return mDailyForecastResponse;
    }
}
