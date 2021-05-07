package pl.piasta.astroweatherextended.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.EditTextPreference;
import androidx.preference.EditTextPreferenceDialogFragmentCompat;
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
import pl.piasta.astroweatherextended.util.AppUtils;

public class SettingsFragment extends PreferenceFragmentCompat {

    private SettingsViewModel mModel;

    private EditTextPreference mTown;
    private CoordinatesPreference mLatitude;
    private CoordinatesPreference mLongtitude;
    private ListPreference mMeasurementUnitType;
    private SwitchPreferenceCompat mAutoSync;
    private ListPreference mSyncFrequency;
    private Preference mCoordinatesSet;

    private Snackbar mSnackbar;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        setHasOptionsMenu(true);
        mModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        mTown = findPreference("town");
        mLatitude = findPreference("latitude");
        mLongtitude = findPreference("longtitude");
        mMeasurementUnitType = findPreference("measurement_unit_type");
        mAutoSync = findPreference("auto_sync");
        mSyncFrequency = findPreference("sync_frequency");
        mCoordinatesSet = findPreference("coordinates_set");
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
        mMeasurementUnitType.setPersistent(false);
        mAutoSync.setPersistent(false);
        mSyncFrequency.setPersistent(false);
    }

    private void savePreferences() {
        SharedPreferences.Editor editor = getPreferenceManager().getSharedPreferences().edit();
        editor.putString(mTown.getKey(), mTown.getText());
        editor.putString(mLatitude.getKey(), mLatitude.getText());
        editor.putString(mLongtitude.getKey(), mLongtitude.getText());
        editor.putString(mMeasurementUnitType.getKey(), mMeasurementUnitType.getValue());
        editor.putBoolean(mAutoSync.getKey(), mAutoSync.isChecked());
        editor.putString(mSyncFrequency.getKey(), mSyncFrequency.getValue());
        editor.apply();
        ((AppCompatActivity) requireActivity()).onSupportNavigateUp();
    }

    private void setupListeners() {
        mCoordinatesSet.setOnPreferenceClickListener(preference ->
                setCoordinatesSetClickListener());
        mTown.setOnPreferenceClickListener(this::setTownClickListener);
        mTown.setOnPreferenceChangeListener((preference, newValue) -> setTownChangeListener(newValue));
        mTown.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_TEXT));
        mLatitude.setOnPreferenceChangeListener((preference1, newValue) ->
                setLatitudeChangeListener(newValue));
    }

    private void observeModel() {
        mModel.getGeocodingResponse().observe(this, data -> {
            if (data == null) {
                mSnackbar = AppUtils.createSnackbar(
                        requireView(),
                        Snackbar.LENGTH_INDEFINITE,
                        "Location not found");
                mSnackbar.show();
                return;
            }
            setCoordinatesData(data);
        });
        mModel.getReverseGeocodingResponse().observe(this, data -> {
            if (data == null) {
                mSnackbar = AppUtils.createSnackbar(
                        requireView(),
                        Snackbar.LENGTH_INDEFINITE,
                        "No nearby town for coordinates found.");
                mSnackbar.show();
                return;
            }
            setCoordinatesData(data);
        });
        mModel.getToastMessage().observe(this,
                message -> AppUtils.createToast(requireActivity(), message).show());
    }

    private void setCoordinatesData(GeocodingResponse data) {
        NumberFormat numberFormat = DecimalFormat.getInstance(Locale.US);
        numberFormat.setMinimumFractionDigits(6);
        String town;
        if (data.getLocalNamesData() != null && data.getLocalNamesData().getPolishName() != null) {
            town = data.getLocalNamesData().getPolishName();
        } else {
            town = data.getTown();
        }
        mTown.setText(town + ", " + data.getCountryCode());
        mLatitude.setText(numberFormat.format(data.getLatitude()));
        mLongtitude.setText(numberFormat.format(data.getLongtitude()));
    }

    private boolean setLatitudeChangeListener(final Object value) {
        String latitude = value.toString();
        if (AppUtils.isCoordinateValid(latitude)) {
            mLongtitude.setOnPreferenceChangeListener((preference, newValue) ->
                    setLongtitudeChangeListener(latitude, newValue));
            DialogFragment fragment = EditTextPreferenceDialogFragmentCompat.newInstance(mLongtitude.getKey());
            fragment.setTargetFragment(this, 0);
            fragment.show(getParentFragmentManager(), AppUtils.DIALOG_FRAGMENT_TAG);
        }
        return false;
    }

    private boolean setLongtitudeChangeListener(final String latitude, final Object value) {
        String longtitude = value.toString();
        if (AppUtils.isCoordinateValid(longtitude) &&
                (!latitude.equals(mLatitude.getText()) || !longtitude.equals(mLongtitude.getText()))) {
            mModel.fetchReverseCoordinatesData(Double.parseDouble(latitude), Double.parseDouble(longtitude));
        }
        return false;
    }

    private boolean setTownChangeListener(final Object value) {
        String town = value.toString();
        if (!town.isEmpty() && !town.equals(mTown.getText())) {
            mModel.fetchCoordinatesData(value.toString());
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
        onDisplayPreferenceDialog(mLatitude);
        return true;
    }
}