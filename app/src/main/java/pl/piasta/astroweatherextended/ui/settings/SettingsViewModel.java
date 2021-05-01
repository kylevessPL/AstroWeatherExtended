package pl.piasta.astroweatherextended.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import pl.piasta.astroweatherextended.model.GeocodingResponse;
import pl.piasta.astroweatherextended.model.ReverseGeocodingResponse;
import pl.piasta.astroweatherextended.repository.GeocodingRepository;
import pl.piasta.astroweatherextended.util.GlobalVariables;
import pl.piasta.astroweatherextended.util.SingleLiveEvent;

public class SettingsViewModel extends ViewModel {

    private final GeocodingRepository mGeocodingRepository = new GeocodingRepository();

    private LiveData<GeocodingResponse> mGeocodingResponse;
    private LiveData<ReverseGeocodingResponse> mReverseGeocodingResponse;
    private SingleLiveEvent<String> mToastMessage;
    private SingleLiveEvent<String> mSnackbarMessage;

    public LiveData<GeocodingResponse> getCoordinatesResponse() {
        if (mGeocodingResponse == null) {
            mGeocodingResponse = new MutableLiveData<>();
        }
        return mGeocodingResponse;
    }

    public LiveData<ReverseGeocodingResponse> getReverseCoordinatesResponse() {
        if (mReverseGeocodingResponse == null) {
            mReverseGeocodingResponse = new MutableLiveData<>();
        }
        return mReverseGeocodingResponse;
    }

    public LiveData<String> getToastMessage() {
        if (mToastMessage == null) {
            mToastMessage = new SingleLiveEvent<>();
        }
        return mToastMessage;
    }

    public LiveData<String> getSnackbarMessage() {
        if (mSnackbarMessage == null) {
            mSnackbarMessage = new SingleLiveEvent<>();
        }
        return mSnackbarMessage;
    }

    public void fetchCoordinatesData(String town) {
        if (!GlobalVariables.sIsNetworkConnected) {
            mToastMessage.setValue("No Internet connection");
            return;
        }
        LiveData<GeocodingResponse> data =
                mGeocodingRepository.getCoordinates(town, GlobalVariables.API_KEY);
        if (data != null) {
            mGeocodingResponse = data;
            return;
        }
        mSnackbarMessage.setValue("Location " + town + " not found");
    }

    public void retrieveReverseCoordinatesData(double latitude, double longtitude) {
        if (!GlobalVariables.sIsNetworkConnected) {
            mToastMessage.setValue("No Internet connection");
            return;
        }
        LiveData<ReverseGeocodingResponse> data =
                mGeocodingRepository.getTown(latitude, longtitude, GlobalVariables.API_KEY);
        if (data != null) {
            mReverseGeocodingResponse = data;
            return;
        }
        mSnackbarMessage.setValue("No nearby town for latitude " + latitude +
                " and longtitude " + longtitude + " found");
    }
}
