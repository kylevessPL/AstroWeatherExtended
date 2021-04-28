package pl.piasta.astroweatherextended.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pl.piasta.astroweatherextended.model.CoordinatesResponse;
import pl.piasta.astroweatherextended.repository.GeocodingRepository;
import pl.piasta.astroweatherextended.util.GlobalVariables;
import pl.piasta.astroweatherextended.util.SingleLiveEvent;

public class SettingsViewModel extends ViewModel {

    private final ExecutorService mExecutorService = Executors.newSingleThreadExecutor();
    private final GeocodingRepository mGeocodingRepository = new GeocodingRepository();

    private LiveData<CoordinatesResponse> mCoordinates;
    private SingleLiveEvent<String> mToastMessage;

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

    public void retrieveCoordinatesData(String town) {
        if (!GlobalVariables.sIsNetworkConnected) {
            mToastMessage.setValue("No Internet connection");
            return;
        }
        LiveData<CoordinatesResponse> data = mGeocodingRepository.getCoordinates(town, GlobalVariables.API_KEY);
        if (data != null) {
            mCoordinates = data;
            return;
        }
        mToastMessage.setValue("Location not found!");
    }
}
