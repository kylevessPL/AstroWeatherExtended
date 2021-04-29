package pl.piasta.astroweatherextended.ui.favourites;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import pl.piasta.astroweatherextended.R;
import pl.piasta.astroweatherextended.model.CoordinatesResponse;

public class FavouritesActivity extends AppCompatActivity {

    public static final String TOWN_DEFAULT = "Warszawa";

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
        RecyclerView recyclerView = findViewById(R.id.favourites_list);
        recyclerView.setAdapter(new FavouritesRecyclerViewAdapter(
                mFavouriteList.stream()
                .map(e -> {
                    String current = mPreferences.getString("town", TOWN_DEFAULT);
                    return new FavouriteItem(e, e.equals(current));
                })
                .collect(Collectors.toList()),
                (itemId, position) -> {
                    if (itemId == R.id.favourite_set) {
                        mModel.retrieveCoordinatesData(mFavouriteList.get(position));
                    } else if (itemId == R.id.favourite_delete) {
                        SharedPreferences.Editor editor = mPreferences.edit();
                        mFavouriteList.remove(position);
                        editor.putStringSet("favourites", new HashSet<>(mFavouriteList));
                        editor.apply();
                    }
        }));
        observeModel();
    }

    private void observeModel() {
        mModel.getCoordinatesResponse().observe(this, this::setCoordinatesData);
        mModel.getToastMessage().observe(this,
                message -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
        mModel.getSnackbarMessage().observe(this,
                message -> Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                        .setAction("DISMISS", view -> {})
                        .show());
    }

    private void setCoordinatesData(CoordinatesResponse coordinatesData) {
        SharedPreferences.Editor editor = mPreferences.edit();
        NumberFormat numberFormat = DecimalFormat.getInstance(Locale.US);
        numberFormat.setMinimumFractionDigits(6);
        editor.putString("town", coordinatesData.getTown() + " " + coordinatesData.getCountryCode());
        editor.putString("latitude", numberFormat.format(coordinatesData.getLatitude()));
        editor.putString("longtitude", numberFormat.format(coordinatesData.getLongtitude()));
        editor.apply();
    }
}