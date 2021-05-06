package pl.piasta.astroweatherextended.ui.favourites;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import pl.piasta.astroweatherextended.model.GeocodingResponse;
import pl.piasta.astroweatherextended.repository.GeocodingRepository;
import pl.piasta.astroweatherextended.util.AppUtils;
import pl.piasta.astroweatherextended.util.GlobalVariables;

public class FavouritesViewModel extends ViewModel {

    private final GeocodingRepository mGeocodingRepository = new GeocodingRepository();

    public LiveData<GeocodingResponse> getGeocodingResponse() {
        return mGeocodingRepository.getGeocodingResponse();
    }

    public void fetchCoordinatesData(String town) {
        town = AppUtils.stripAccents(town);
        mGeocodingRepository.fetchGeocodingData(town, GlobalVariables.API_KEY);
    }
}
