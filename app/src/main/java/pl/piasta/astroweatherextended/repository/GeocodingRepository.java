package pl.piasta.astroweatherextended.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import pl.piasta.astroweatherextended.model.GeocodingResponse;
import pl.piasta.astroweatherextended.repository.retrofit.RetrofitRequest;
import pl.piasta.astroweatherextended.service.GeocodingService;
import pl.piasta.astroweatherextended.util.SingleLiveEvent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GeocodingRepository {

    private final GeocodingService mWeatherService;

    private final SingleLiveEvent<GeocodingResponse> mGeocodingResponse;
    private final SingleLiveEvent<GeocodingResponse> mReverseGeocodingResponse;

    public GeocodingRepository() {
        mWeatherService = RetrofitRequest.getRetrofitInstance().create(GeocodingService.class);
        mGeocodingResponse = new SingleLiveEvent<>();
        mReverseGeocodingResponse = new SingleLiveEvent<>();
    }

    public void fetchGeocodingData(String query, String key) {
        mWeatherService.getCoordinates(query, key)
                .enqueue(new Callback<List<GeocodingResponse>>() {

                    @Override
                    public void onResponse(
                            @NonNull Call<List<GeocodingResponse>> call,
                            @NonNull Response<List<GeocodingResponse>> response) {
                        if (response.body() != null && !response.body().isEmpty()) {
                            mGeocodingResponse.setValue(response.body().get(0));
                        } else {
                            mGeocodingResponse.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<List<GeocodingResponse>> call,
                            @NonNull Throwable t
                    ) {
                        mGeocodingResponse.setValue(null);
                    }
                });
    }

    public void fetchReverseGeocodingData(Double latitude, Double longtitude, String key) {
        mWeatherService.getTown(latitude, longtitude, key)
                .enqueue(new Callback<List<GeocodingResponse>>() {

                    @Override
                    public void onResponse(
                            @NonNull Call<List<GeocodingResponse>> call,
                            @NonNull Response<List<GeocodingResponse>> response) {
                        if (response.body() != null && !response.body().isEmpty()) {
                            mReverseGeocodingResponse.setValue(response.body().get(0));
                        } else {
                            mReverseGeocodingResponse.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<List<GeocodingResponse>> call,
                            @NonNull Throwable t
                    ) {
                        mReverseGeocodingResponse.setValue(null);
                    }
                });
    }

    public LiveData<GeocodingResponse> getGeocodingResponse() {
        return mGeocodingResponse;
    }

    public LiveData<GeocodingResponse> getReverseGeocodingResponse() {
        return mReverseGeocodingResponse;
    }
}
