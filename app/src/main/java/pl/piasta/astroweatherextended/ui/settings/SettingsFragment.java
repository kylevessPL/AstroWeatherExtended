package pl.piasta.astroweatherextended.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import pl.piasta.astroweatherextended.R;
import pl.piasta.astroweatherextended.model.CoordinatesResponse;

public class SettingsFragment extends PreferenceFragmentCompat {

    private SettingsViewModel mModel;

    private EditTextPreference mTown;
    private EditTextPreference mLatitude;
    private EditTextPreference mLongtitude;
    private ListPreference mTemperatureUnit;
    private SwitchPreferenceCompat mAutoSync;
    private ListPreference mSyncFrequency;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        setHasOptionsMenu(true);
        mModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        mTown = findPreference("town");
        mLatitude = findPreference("latitude");
        mLongtitude = findPreference("longtitude");
        mTemperatureUnit = findPreference("temperature_unit");
        mAutoSync = findPreference("auto_sync");
        mSyncFrequency = findPreference("sync_frequency");
        mTown.setOnPreferenceChangeListener((preference, newValue) -> !(((String) newValue).isEmpty()));
        setPersistenceState();
        setupListeners();
        observeModel();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            savePreferences();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setPersistenceState() {
        mTown.setPersistent(false);
        mLatitude.setPersistent(false);
        mLongtitude.setPersistent(false);
        mTemperatureUnit.setPersistent(false);
        mAutoSync.setPersistent(false);
        mSyncFrequency.setPersistent(false);
    }

    private void savePreferences() {
        SharedPreferences.Editor editor = getPreferenceManager().getSharedPreferences().edit();
        editor.putString(mTown.getKey(), mTown.getText());
        editor.putString(mLatitude.getKey(), mLatitude.getText());
        editor.putString(mLongtitude.getKey(), mLongtitude.getText());
        editor.putString(mTemperatureUnit.getKey(), mTemperatureUnit.getValue());
        editor.putBoolean(mAutoSync.getKey(), mAutoSync.isChecked());
        editor.putString(mSyncFrequency.getKey(), mSyncFrequency.getValue());
        editor.apply();
    }

    private void setupListeners() {
        mTown.setOnPreferenceChangeListener((preference, newValue) -> {
            mModel.retrieveCoordinatesData(newValue.toString());
            return false;
        });
    }

    private void observeModel() {
        mModel.getCoordinatesResponse().observe(this, this::setCoordinatesData);
        mModel.getToastMessage().observe(this,
                message -> Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show());
        mModel.getSnackbarMessage().observe(this,
                message -> Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG)
                        .setAction("DISMISS", view -> {})
                        .show());
    }

    private void setCoordinatesData(CoordinatesResponse coordinatesData) {
        NumberFormat numberFormat = DecimalFormat.getInstance(Locale.US);
        numberFormat.setMinimumFractionDigits(6);
        mTown.setText(coordinatesData.getTown() + " " + coordinatesData.getCountryCode());
        mLatitude.setText(numberFormat.format(coordinatesData.getLatitude()));
        mLongtitude.setText(numberFormat.format(coordinatesData.getLongtitude()));
    }
}