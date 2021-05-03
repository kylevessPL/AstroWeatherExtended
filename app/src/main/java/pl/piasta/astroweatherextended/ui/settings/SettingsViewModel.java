package pl.piasta.astroweatherextended.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import pl.piasta.astroweatherextended.model.GeocodingResponse;
import pl.piasta.astroweatherextended.repository.GeocodingRepository;
import pl.piasta.astroweatherextended.util.AppUtils;
import pl.piasta.astroweatherextended.util.GlobalVariables;
import pl.piasta.astroweatherextended.util.SingleLiveEvent;

public class SettingsViewModel extends ViewModel {

    private final GeocodingRepository mGeocodingRepository = new GeocodingRepository();

    private final SingleLiveEvent<String> mToastMessage = new SingleLiveEvent<>();

    public SingleLiveEvent<String> getToastMessage() {
        return mToastMessage;
    }

    public void fetchCoordinatesData(String town) {
        if (!GlobalVariables.sIsNetworkConnected) {
            mToastMessage.setValue("No Internet connection");
            return;
        }
        town = AppUtils.stripAccents(town);
        mGeocodingRepository.fetchGeocodingData(town, GlobalVariables.API_KEY);
    }

    public void fetchReverseCoordinatesData(double latitude, double longtitude) {
        if (!GlobalVariables.sIsNetworkConnected) {
            mToastMessage.setValue("No Internet connection");
            return;
        }
        mGeocodingRepository.fetchReverseGeocodingData(latitude, longtitude, GlobalVariables.API_KEY);
    }

    public LiveData<GeocodingResponse> getGeocodingResponse() {
        return mGeocodingRepository.getGeocodingResponse();
    }

    public LiveData<GeocodingResponse> getReverseGeocodingResponse() {
        return mGeocodingRepository.getReverseGeocodingResponse();
    }
}
