package pl.piasta.astroweatherextended.ui.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Objects;

import pl.piasta.astroweatherextended.R;

public class MainActivity extends AppCompatActivity {

    public static final String SYNC_FREQUENCY_DEFAULT = "3";
    public static final String TOWN_DEFAULT = "Warszawa";
    public static final boolean AUTO_SYNC_DEFAULT = false;

    private MainViewModel model;
    private BroadcastReceiver dateTimeBroadcastReceiver;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences mPreferences;

    private TextView mTime;
    private TextView mTown;
    private ImageButton mFavourite;
    private TextView mLastUpdateCheck;
    private TextView mLatitude;
    private TextView mLongitude;
    private ImageButton mRefreshButton;
    private CardView mCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mPreferences = getPreferences(MODE_PRIVATE);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT &&
                !getResources().getBoolean(R.bool.isTablet)
        ) {
            setupPager();
        }
        mTime = findViewById(R.id.time);
        mTown = findViewById(R.id.town);
        mFavourite = findViewById(R.id.favourite_add);
        mLatitude = findViewById(R.id.latitude);
        mLongitude = findViewById(R.id.longitude);
        mLastUpdateCheck = findViewById(R.id.last_update_check);
        model = new ViewModelProvider(this).get(MainViewModel.class);
        mRefreshButton = findViewById(R.id.refresh);
        mCard = findViewById(R.id.card);
        setupListeners();
        observeModel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        registerDateTimeBroadcastReceiver();
        loadPreferences();
        setupAutoUpdate();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (dateTimeBroadcastReceiver != null) {
            unregisterReceiver(dateTimeBroadcastReceiver);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settings = new Intent("pl.piasta.astroweatherextended.SETTINGS");
            startActivity(settings);
            return true;
        } else if (id == R.id.action_favourites) {
            Intent favourites = new Intent("pl.piasta.astroweatherextended.FAVOURITES");
            startActivity(favourites);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupPager() {
        ViewPager2 pager = findViewById(R.id.pager);
        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                int color;
                switch(position) {
                    case 2:
                        color = R.color.yellow_700;
                        break;
                    case 3:
                        color = R.color.grey_700;
                        break;
                    default:
                        color = R.color.orange_900;
                        break;
                }
                mCard.setBackgroundColor(getColor(color));
                super.onPageSelected(position);
            }
        });
        setAstroFragmentViewPager(pager);
        setAstroTabLayout(pager);
    }

    private void setAstroFragmentViewPager(ViewPager2 pa) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentStateAdapter sa = new AstroFragmentStateAdapter(fm, getLifecycle());
        pa.setAdapter(sa);
    }

    private void setAstroTabLayout(ViewPager2 pa) {
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, pa, (tab, position) ->
                tab.setText(((AstroFragmentStateAdapter) Objects.requireNonNull(pa.getAdapter()))
                        .getItemName(position))
        ).attach();
    }

    private void registerDateTimeBroadcastReceiver() {
        dateTimeBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context ctx, Intent intent) {
                if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
                    model.updateClock();
                }
            }
        };
        registerReceiver(dateTimeBroadcastReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
    }

    private void setupAutoUpdate() {
        String town = mTown.getText().toString();
        UpdateInterval updateInterval = UpdateInterval.DISABLED;
        int delay = 0;
        boolean autoSync = mSharedPreferences.getBoolean("auto_sync", AUTO_SYNC_DEFAULT);
        if (autoSync) {
            String frequency = mSharedPreferences.getString("sync_frequency", SYNC_FREQUENCY_DEFAULT);
            updateInterval = UpdateInterval.values()[Integer.parseInt(frequency)];
            String lastUpdateCheck = mPreferences.getString(
                    "lastUpdateCheck",
                    LocalDateTime.now().toString());
            LocalDateTime dateTime = LocalDateTime.parse(lastUpdateCheck).plusSeconds(updateInterval.getUnit()
                    .toSeconds(updateInterval.getInterval()));
            long diff = ChronoUnit.SECONDS.between(
                    LocalDateTime.now().toInstant(ZoneOffset.UTC),
                    dateTime.toInstant(ZoneOffset.UTC));
            if (diff > 0) {
                delay = (int) diff;
            }

        }
        model.setupDataUpdate(updateInterval, delay, town);
    }

    private void setupListeners() {
        Animation rotation = createRefreshAnimation();
        mRefreshButton.setOnClickListener(button -> button.startAnimation(rotation));
    }

    private Animation createRefreshAnimation() {
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.animation_refresh);
        rotation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                updateData();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                model.updateLastUpdateCheckTime();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        return rotation;
    }

    private void observeModel() {
        model.getTime().observe(this, mTime::setText);
        model.getLastUpdateCheck().observe(this, text -> {
            mLastUpdateCheck.setText(text);
            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
            mPreferences.edit()
                    .putString("lastUpdateCheck", LocalDateTime.parse(text, formatter).toString())
                    .apply();
        });
    }

    private void loadPreferences() {
        String town = mSharedPreferences.getString("town", TOWN_DEFAULT);
        boolean favourite = mSharedPreferences.getStringSet("favourites", Collections.emptySet())
                .contains(town);
        this.mTown.setText(town);
        if (favourite) {
            mFavourite.setColorFilter(R.color.red);
            return;
        }
        mFavourite.clearColorFilter();
        //TODO read last weather data from preferences
    }

    private void updateData() {
        double latitude = Double.parseDouble(mLatitude.getText().toString());
        double longtitude = Double.parseDouble(mLongitude.getText().toString());
        model.refreshData(latitude, longtitude);
    }
}