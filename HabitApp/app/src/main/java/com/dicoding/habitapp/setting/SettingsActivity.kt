package com.dicoding.habitapp.setting

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.habitapp.R
import com.dicoding.habitapp.utils.DarkMode

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            //TODO 11 : Update theme based on value in ListPreference
            val themePreference = findPreference<ListPreference>(getString(R.string.pref_key_dark))
            themePreference?.setOnPreferenceChangeListener { preference, newValue ->
                val newThemeValue = newValue.toString()

                val nightMode = when (newThemeValue) {
                    getString(R.string.pref_dark_follow_system) -> DarkMode.FOLLOW_SYSTEM
                    getString(R.string.pref_dark_on) -> DarkMode.ON
                    getString(R.string.pref_dark_off) -> DarkMode.OFF
                    else -> DarkMode.FOLLOW_SYSTEM
                }

                updateTheme(nightMode.value)
                true
            }

            val prefNotification = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))

            prefNotification?.setOnPreferenceChangeListener { preference, newValue ->
                if (newValue == true) {
                    Toast.makeText(requireContext(), "Reminder: on", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Reminder: off", Toast.LENGTH_SHORT).show()
                }
                true
            }
        }

        private fun updateTheme(mode: Int): Boolean {
            AppCompatDelegate.setDefaultNightMode(mode)
            requireActivity().recreate()
            return true
        }
    }
}