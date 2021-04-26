package pl.piasta.astroweatherextended.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import pl.piasta.astroweatherextended.model.CurrentWeatherDataResponse;
import pl.piasta.astroweatherextended.model.DailyForecastResponse;
import pl.piasta.astroweatherextended.repository.retrofit.RetrofitRequest;
import pl.piasta.astroweatherextended.service.WeatherService;
import pl.piasta.astroweatherextended.ui.main.TemperatureUnit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepository {

    private final WeatherService mWeatherService;

    public WeatherRepository() {
        mWeatherService = RetrofitRequest.getRetrofitInstance().create(WeatherService.class);
    }

    public LiveData<CurrentWeatherDataResponse> getCurrentWeatherData(String query, TemperatureUnit unit, String apiKey) {
        final MutableLiveData<CurrentWeatherDataResponse> data = new MutableLiveData<>();
        mWeatherService.getCurrentWeatherData(query, unit, apiKey)
                .enqueue(new Callback<CurrentWeatherDataResponse>() {

                    @Override
                    public void onResponse(
                            @NonNull Call<CurrentWeatherDataResponse> call,
                            @NonNull Response<CurrentWeatherDataResponse> response
                    ) {
                        if (response.body() != null) {
                            data.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<CurrentWeatherDataResponse> call,
                            @NonNull Throwable t
                    ) {
                        data.setValue(null);
                    }
                });
        return data;
    }

    public LiveData<DailyForecastResponse> getDailyForecast(String query, TemperatureUnit unit, String apiKey) {
        final MutableLiveData<DailyForecastResponse> data = new MutableLiveData<>();
        mWeatherService.getDailyForecast(query, unit, apiKey)
                .enqueue(new Callback<DailyForecastResponse>() {

                    @Override
                    public void onResponse(
                            @NonNull Call<DailyForecastResponse> call,
                            @NonNull Response<DailyForecastResponse> response
                    ) {
                        if (response.body() != null) {
                            data.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<DailyForecastResponse> call,
                            @NonNull Throwable t
                    ) {
                        data.setValue(null);
                    }
                });
        return data;
    }
}
