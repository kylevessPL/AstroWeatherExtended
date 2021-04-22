package pl.piasta.astroweatherextended.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import pl.piasta.astroweatherextended.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    private CoordinatesPreference latitude;
    private CoordinatesPreference longtitude;
    private SwitchPreferenceCompat autoSync;
    private ListPreference syncFrequency;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        setHasOptionsMenu(true);
        latitude = (CoordinatesPreference) findPreference("latitude");
        longtitude = (CoordinatesPreference) findPreference("longtitude");
        autoSync = (SwitchPreferenceCompat) findPreference("auto_sync");
        syncFrequency = (ListPreference) findPreference("sync_frequency");
        setPersistenceState();
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
        latitude.setPersistent(false);
        longtitude.setPersistent(false);
        autoSync.setPersistent(false);
        syncFrequency.setPersistent(false);
    }

    private void savePreferences() {
        SharedPreferences.Editor editor = getPreferenceManager().getSharedPreferences().edit();
        editor.putString(latitude.getKey(), latitude.getText());
        editor.putString(longtitude.getKey(), longtitude.getText());
        editor.putBoolean(autoSync.getKey(), autoSync.isChecked());
        editor.putString(syncFrequency.getKey(), syncFrequency.getValue());
        editor.apply();
    }
}