package pl.piasta.astroweatherextended.ui.favourites;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import pl.piasta.astroweatherextended.model.GeocodingResponse;
import pl.piasta.astroweatherextended.repository.GeocodingRepository;
import pl.piasta.astroweatherextended.util.AppUtils;
import pl.piasta.astroweatherextended.util.GlobalVariables;
import pl.piasta.astroweatherextended.util.SingleLiveEvent;

public class FavouritesViewModel extends ViewModel {

    private final GeocodingRepository mGeocodingRepository = new GeocodingRepository();

    private final SingleLiveEvent<String> mToastMessage = new SingleLiveEvent<>();

    public LiveData<GeocodingResponse> getGeocodingResponse() {
        return mGeocodingRepository.getGeocodingResponse();
    }

    public SingleLiveEvent<String> getToastMessage() {
        return mToastMessage;
    }

    public void fetchCoordinatesData(String town) {
        if (!GlobalVariables.sIsNetworkConnected) {
            mToastMessage.setValue("Cannot set location without Internet connection");
            return;
        }
        town = AppUtils.stripAccents(town);
        mGeocodingRepository.fetchGeocodingData(town, GlobalVariables.API_KEY);
    }
}
