package pl.piasta.astroweatherextended.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import pl.piasta.astroweatherextended.model.CoordinatesResponse;
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

    public LiveData<CoordinatesResponse> getCoordinates(String query, String key) {
        final MutableLiveData<CoordinatesResponse> data = new MutableLiveData<>();
        mWeatherService.getCoordinates(query, key)
                .enqueue(new Callback<CoordinatesResponse>() {

                    @Override
                    public void onResponse(
                            @NonNull Call<CoordinatesResponse> call,
                            @NonNull Response<CoordinatesResponse> response) {
                        if (response.body() != null) {
                            data.setValue(response.body());
                        } else {
                            data.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<CoordinatesResponse> call,
                            @NonNull Throwable t
                    ) {
                        data.setValue(null);
                    }
                });
        return data;
    }
}
