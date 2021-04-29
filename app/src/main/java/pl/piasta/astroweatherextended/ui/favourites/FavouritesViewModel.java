package pl.piasta.astroweatherextended.ui.favourites;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import pl.piasta.astroweatherextended.model.CoordinatesResponse;
import pl.piasta.astroweatherextended.repository.GeocodingRepository;
import pl.piasta.astroweatherextended.util.GlobalVariables;
import pl.piasta.astroweatherextended.util.SingleLiveEvent;

public class FavouritesViewModel extends ViewModel {

    private final GeocodingRepository mGeocodingRepository = new GeocodingRepository();

    private LiveData<CoordinatesResponse> mCoordinates;
    private SingleLiveEvent<String> mToastMessage;
    private SingleLiveEvent<String> mSnackbarMessage;

    public LiveData<CoordinatesResponse> getCoordinatesResponse() {
        if (mCoordinates == null) {
            mCoordinates = new MutableLiveData<>();
        }
        return mCoordinates;
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

    public void retrieveCoordinatesData(String town) {
        if (!GlobalVariables.sIsNetworkConnected) {
            mToastMessage.setValue("Cannot set location without Internet connection");
            return;
        }
        LiveData<CoordinatesResponse> data = mGeocodingRepository.getCoordinates(town, GlobalVariables.API_KEY);
        if (data != null) {
            mCoordinates = data;
            return;
        }
        mSnackbarMessage.setValue("Location " + town + " not found");
    }
}
