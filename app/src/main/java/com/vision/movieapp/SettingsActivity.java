package com.vision.movieapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public static class MovieSettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        private static final String TAG = "Settings Fragment";

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_pref);

            Preference sortBy = findPreference(getString(R.string.pref_sort_by_key));
            bindPreferenceToSummary(sortBy);
        }



        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String strValue = newValue.toString();
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(strValue);
                if (prefIndex > 0) {
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);

                } else {
                    preference.setSummary(strValue);
                }
            }
            return true;
        }

        private void bindPreferenceToSummary(Preference sortBy) {
            sortBy.setOnPreferenceChangeListener(this);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(sortBy.getContext());
            String prefString = sharedPreferences.getString(sortBy.getKey(), "");
            Log.d(TAG, "bindPreferenceToSummary: " + prefString );
            onPreferenceChange(sortBy, prefString);
            Log.d(TAG, "bindPreferenceToSummary: " +prefString);

        }
    }
}
