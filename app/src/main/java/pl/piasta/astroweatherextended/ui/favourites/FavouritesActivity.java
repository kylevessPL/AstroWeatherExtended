package pl.piasta.astroweatherextended.ui.favourites;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import pl.piasta.astroweatherextended.R;
import pl.piasta.astroweatherextended.model.GeocodingResponse;
import pl.piasta.astroweatherextended.util.AppUtils;

public class FavouritesActivity extends AppCompatActivity {

    public static final String TOWN_DEFAULT = "Warszawa, PL";

    private FavouritesViewModel mModel;
    private SharedPreferences mPreferences;

    private List<String> mFavouriteList;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mModel = new ViewModelProvider(this).get(FavouritesViewModel.class);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mFavouriteList = new ArrayList<>(mPreferences.getStringSet("favourites", Collections.emptySet()));
        setupRecyclerView();
        observeModel();
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.favourites_list);
        String current = mPreferences.getString("town", TOWN_DEFAULT);
        if (mFavouriteList.contains(current)) {
            mFavouriteList.remove(current);
            mFavouriteList.add(0, current);
        }
        List<FavouriteItem> list = mFavouriteList.stream()
                .map(town -> new FavouriteItem(town, town.equals(current)))
                .collect(Collectors.toList());
        recyclerView.setAdapter(new FavouritesRecyclerViewAdapter(list, (id, position) -> {
            String item = mFavouriteList.remove(position);
            if (id == R.id.favourite_set) {
                mFavouriteList.add(0, item);
                mModel.fetchCoordinatesData(item);
            } else if (id == R.id.favourite_delete) {
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putStringSet("favourites", new HashSet<>(mFavouriteList));
                editor.apply();
            }
        }));
    }

    private void observeModel() {
        mModel.getGeocodingResponse().observe(this, data -> {
            if (data == null) {
                AppUtils.createSnackbar(findViewById(android.R.id.content), "Location not found").show();
                return;
            }
            setCoordinatesData(data);
        });
        mModel.getToastMessage().observe(this,
                message -> AppUtils.createToast(this, message).show());
    }

    private void setCoordinatesData(GeocodingResponse data) {
        SharedPreferences.Editor editor = mPreferences.edit();
        NumberFormat numberFormat = DecimalFormat.getInstance(Locale.US);
        numberFormat.setMinimumFractionDigits(6);
        String town;
        if (data.getLocalNamesData() != null && data.getLocalNamesData().getPolishName() != null) {
            town = data.getLocalNamesData().getPolishName();
        } else {
            town = data.getTown();
        }
        editor.putString("town", town + ", " + data.getCountryCode());
        editor.putString("latitude", numberFormat.format(data.getLatitude()));
        editor.putString("longtitude", numberFormat.format(data.getLongtitude()));
        editor.apply();
    }
}