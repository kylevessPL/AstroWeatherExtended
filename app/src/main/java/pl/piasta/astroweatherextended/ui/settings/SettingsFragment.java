package pl.piasta.astroweatherextended.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import pl.piasta.astroweatherextended.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    private EditTextPreference town;
    private ListPreference temperatureUnit;
    private SwitchPreferenceCompat autoSync;
    private ListPreference syncFrequency;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        setHasOptionsMenu(true);
        town = findPreference("town");
        temperatureUnit = findPreference("temperature_unit");
        autoSync = findPreference("auto_sync");
        syncFrequency = findPreference("sync_frequency");
        town.setOnPreferenceChangeListener((preference, newValue) -> !(((String) newValue).isEmpty()));
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
        town.setPersistent(false);
        temperatureUnit.setPersistent(false);
        autoSync.setPersistent(false);
        syncFrequency.setPersistent(false);
    }

    private void savePreferences() {
        SharedPreferences.Editor editor = getPreferenceManager().getSharedPreferences().edit();
        editor.putString(town.getKey(), town.getText());
        editor.putString(temperatureUnit.getKey(), temperatureUnit.getValue());
        editor.putBoolean(autoSync.getKey(), autoSync.isChecked());
        editor.putString(syncFrequency.getKey(), syncFrequency.getValue());
        editor.apply();
    }
}