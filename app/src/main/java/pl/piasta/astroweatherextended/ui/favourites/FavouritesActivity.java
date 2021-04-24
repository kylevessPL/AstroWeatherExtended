package pl.piasta.astroweatherextended.ui.favourites;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import pl.piasta.astroweatherextended.R;

public class FavouritesActivity extends AppCompatActivity {

    private List<String> mFavouriteList;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        mFavouriteList = new ArrayList<>(preferences.getStringSet("favourites", Collections.emptySet()));
        RecyclerView recyclerView = findViewById(R.id.favourites_list);
        recyclerView.setAdapter(new FavouritesRecyclerViewAdapter(
                mFavouriteList.stream()
                .map(e -> {
                    String current = preferences.getString("town", "Warszawa");
                    return new FavouriteItem(e, e.equals(current));
                })
                .collect(Collectors.toList()),
                (itemId, position) -> {
                    SharedPreferences.Editor editor = preferences.edit();
                    if (itemId == R.id.favourite_set) {
                        editor.putString("town", mFavouriteList.get(position));
                    } else if (itemId == R.id.favourite_delete) {
                        mFavouriteList.remove(position);
                        editor.putStringSet("favourites", new HashSet<>(mFavouriteList));
                    }
                    editor.apply();
        }));
    }
}