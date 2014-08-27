package jorwoody.sunshine.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import jorwoody.sunshine.app.R;

/* Created by: Jordan Wood - August 2014
 * Description:
 * Activity for editing app settings
 */
public class SettingsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {

    Intent returnIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        returnIntent = new Intent();
        addPreferencesFromResource(R.xml.pref_general);

        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_location_key)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_units_key)));
    }

    private void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(this);

        onPreferenceChange(preference,
                PreferenceManager.getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    public boolean onPreferenceChange(Preference preference, Object value) {
        String strValue = value.toString();

        if(preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(strValue);
            if(prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else {
            preference.setSummary(strValue);
        }

        returnIntent.putExtra("settingChanged", true);
        setResult(RESULT_OK, returnIntent);

        return true;
    }
}
