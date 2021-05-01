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

    private LiveData<GeocodingResponse> mGeocodingResponse = new MutableLiveData<>();
    private LiveData<ReverseGeocodingResponse> mReverseGeocodingResponse = new MutableLiveData<>();
    private final SingleLiveEvent<String> mToastMessage = new SingleLiveEvent<>();
    private final SingleLiveEvent<String> mSnackbarMessage = new SingleLiveEvent<>();

    public LiveData<GeocodingResponse> getGeocodingResponse() {
        return mGeocodingResponse;
    }

    public LiveData<ReverseGeocodingResponse> getReverseGeocodingResponse() {
        return mReverseGeocodingResponse;
    }

    public SingleLiveEvent<String> getToastMessage() {
        return mToastMessage;
    }

    public SingleLiveEvent<String> getSnackbarMessage() {
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
