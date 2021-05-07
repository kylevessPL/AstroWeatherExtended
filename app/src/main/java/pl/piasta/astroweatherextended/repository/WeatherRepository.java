package pl.piasta.astroweatherextended.repository;

import android.util.Log;

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

    private static final String TAG = WeatherRepository.class.getSimpleName();

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
                            Log.i(TAG, "Fetch current weather response success");
                            mCurrentWeatherDataResponse.setValue(response.body());
                        } else {
                            Log.i(TAG, "Fetch current weather response failure");
                            mCurrentWeatherDataResponse.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<CurrentWeatherDataResponse> call,
                            @NonNull Throwable t
                    ) {
                        Log.i(TAG, "Fetch current weather response failure");
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
                            Log.i(TAG, "Fetch daily forecast response success");
                            mDailyForecastResponse.setValue(response.body());
                        } else {
                            Log.i(TAG, "Fetch daily forecast response failure");
                            mDailyForecastResponse.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<DailyForecastResponse> call,
                            @NonNull Throwable t
                    ) {
                        Log.i(TAG, "Fetch daily forecast response failure");
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
