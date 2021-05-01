package pl.piasta.astroweatherextended.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import pl.piasta.astroweatherextended.R;
import pl.piasta.astroweatherextended.model.GeocodingResponse;
import pl.piasta.astroweatherextended.model.ReverseGeocodingResponse;

public class SettingsFragment extends PreferenceFragmentCompat {

    private SettingsViewModel mModel;

    private EditTextPreference mTown;
    private CoordinatesPreference mLatitude;
    private CoordinatesPreference mLongtitude;
    private ListPreference mTemperatureUnit;
    private SwitchPreferenceCompat mAutoSync;
    private ListPreference mSyncFrequency;

    private Snackbar mSnackbar;

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
        ((AppCompatActivity) requireActivity()).onSupportNavigateUp();
    }

    private void setupListeners() {
        findPreference("coordinates_set").setOnPreferenceClickListener(preference ->
                setCoordinatesSetClickListener());
        mTown.setOnPreferenceClickListener(this::setTownClickListener);
        mTown.setOnPreferenceChangeListener((preference, newValue) -> setTownChangeListener(newValue));
        mLatitude.setOnPreferenceChangeListener((preference1, newValue1) ->
                setLatitudeClickListener(newValue1));
    }

    private void observeModel() {
        mModel.getGeocodingResponse().observe(this, this::setCoordinatesData);
        mModel.getReverseGeocodingResponse().observe(this, this::setTownData);
        mModel.getToastMessage().observe(this,
                message -> Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show());
        mModel.getSnackbarMessage().observe(this, message -> {
            mSnackbar = Snackbar.make(requireView(), message, Snackbar.LENGTH_INDEFINITE)
                    .setAction("DISMISS", view -> {});
            mSnackbar.show();
        });
    }

    private void setCoordinatesData(GeocodingResponse data) {
        NumberFormat numberFormat = DecimalFormat.getInstance(Locale.US);
        numberFormat.setMinimumFractionDigits(6);
        mTown.setText(data.getTown() + " " + data.getCountryCode());
        mLatitude.setText(numberFormat.format(data.getLatitude()));
        mLongtitude.setText(numberFormat.format(data.getLongtitude()));
    }

    private void setTownData(ReverseGeocodingResponse data) {
        NumberFormat numberFormat = DecimalFormat.getInstance(Locale.US);
        numberFormat.setMinimumFractionDigits(6);
        if (data.getLocalNamesData() != null && data.getLocalNamesData().getPolishName() != null) {
            mTown.setText(data.getLocalNamesData().getPolishName() + " " + data.getCountryCode());
        } else {
            mTown.setText(data.getTown() + " " + data.getCountryCode());
        }
        mLatitude.setText(numberFormat.format(data.getLatitude()));
        mLongtitude.setText(numberFormat.format(data.getLongtitude()));
    }

    private boolean setLatitudeClickListener(final Object value) {
        String latitude = value.toString();
        if (isCoordinatesPreferenceValid(latitude)) {
            getPreferenceManager().showDialog(mLongtitude);
            mLongtitude.setOnPreferenceChangeListener((preference2, newValue) -> {
                String longtitude = newValue.toString();
                if (isCoordinatesPreferenceValid(longtitude)) {
                    mModel.retrieveReverseCoordinatesData(
                            Double.parseDouble(latitude),
                            Double.parseDouble(longtitude)
                    );
                }
                return false;
            });
        }
        return false;
    }

    private boolean setTownChangeListener(final Object newValue) {
        if (!newValue.toString().isEmpty()) {
            mModel.fetchCoordinatesData(newValue.toString());
        }
        return false;
    }

    private boolean setTownClickListener(final Preference preference) {
        if (mSnackbar != null) {
            mSnackbar.dismiss();
        }
        return onPreferenceTreeClick(preference);
    }

    private boolean setCoordinatesSetClickListener() {
        if (mSnackbar != null) {
            mSnackbar.dismiss();
        }
        getPreferenceManager().showDialog(mLatitude);
        return true;
    }

    private boolean isCoordinatesPreferenceValid(String value) {
        return !(value.isEmpty() || value.matches("^[.\\-]{1,2}"));
    }
}