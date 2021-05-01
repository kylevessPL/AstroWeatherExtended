package pl.piasta.astroweatherextended.ui.favourites;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import pl.piasta.astroweatherextended.model.GeocodingResponse;
import pl.piasta.astroweatherextended.repository.GeocodingRepository;
import pl.piasta.astroweatherextended.util.GlobalVariables;
import pl.piasta.astroweatherextended.util.SingleLiveEvent;

public class FavouritesViewModel extends ViewModel {

    private final GeocodingRepository mGeocodingRepository = new GeocodingRepository();

    private LiveData<GeocodingResponse> mGeocodingResponse = new MutableLiveData<>();
    private final SingleLiveEvent<String> mToastMessage = new SingleLiveEvent<>();
    private final SingleLiveEvent<String> mSnackbarMessage = new SingleLiveEvent<>();

    public LiveData<GeocodingResponse> getGeocodingResponse() {
        return mGeocodingResponse;
    }

    public SingleLiveEvent<String> getToastMessage() {
        return mToastMessage;
    }

    public SingleLiveEvent<String> getSnackbarMessage() {
        return mSnackbarMessage;
    }

    public void retrieveCoordinatesData(String town) {
        if (!GlobalVariables.sIsNetworkConnected) {
            mToastMessage.setValue("Cannot set location without Internet connection");
            return;
        }
        LiveData<GeocodingResponse> data = mGeocodingRepository.getCoordinates(town, GlobalVariables.API_KEY);
        if (data != null) {
            mGeocodingResponse = data;
            return;
        }
        mSnackbarMessage.setValue("Location " + town + " not found");
    }
}
