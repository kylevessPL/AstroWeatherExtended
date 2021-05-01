package pl.piasta.astroweatherextended.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import pl.piasta.astroweatherextended.model.GeocodingResponse;
import pl.piasta.astroweatherextended.model.ReverseGeocodingResponse;
import pl.piasta.astroweatherextended.repository.retrofit.RetrofitRequest;
import pl.piasta.astroweatherextended.service.GeocodingService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GeocodingRepository {

    private final GeocodingService mWeatherService;

    public GeocodingRepository() {
        mWeatherService = RetrofitRequest.getRetrofitInstance().create(GeocodingService.class);
    }

    public LiveData<GeocodingResponse> getCoordinates(String query, String key) {
        final MutableLiveData<GeocodingResponse> data = new MutableLiveData<>();
        mWeatherService.getCoordinates(query, key)
                .enqueue(new Callback<GeocodingResponse>() {

                    @Override
                    public void onResponse(
                            @NonNull Call<GeocodingResponse> call,
                            @NonNull Response<GeocodingResponse> response) {
                        if (response.body() != null) {
                            data.setValue(response.body());
                        } else {
                            data.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<GeocodingResponse> call,
                            @NonNull Throwable t
                    ) {
                        data.setValue(null);
                    }
                });
        return data;
    }

    public LiveData<ReverseGeocodingResponse> getTown(Double latitude, Double longtitude, String key) {
        final MutableLiveData<ReverseGeocodingResponse> data = new MutableLiveData<>();
        mWeatherService.getTown(latitude, longtitude, key)
                .enqueue(new Callback<ReverseGeocodingResponse>() {

                    @Override
                    public void onResponse(
                            @NonNull Call<ReverseGeocodingResponse> call,
                            @NonNull Response<ReverseGeocodingResponse> response) {
                        if (response.body() != null) {
                            data.setValue(response.body());
                        } else {
                            data.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<ReverseGeocodingResponse> call,
                            @NonNull Throwable t
                    ) {
                        data.setValue(null);
                    }
                });
        return data;
    }
}
