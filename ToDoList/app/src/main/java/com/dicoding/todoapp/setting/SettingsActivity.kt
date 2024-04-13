package com.dicoding.todoapp.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import androidx.work.*
import com.dicoding.todoapp.R
import com.dicoding.todoapp.notification.NotificationWorker
import com.dicoding.todoapp.utils.NOTIFICATION_CHANNEL_ID
import java.util.concurrent.TimeUnit

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
        private lateinit var request: PeriodicWorkRequest
        private lateinit var workManager: WorkManager
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            workManager = WorkManager.getInstance(requireContext())
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            val prefNotification = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
            prefNotification?.setOnPreferenceChangeListener { preference, newValue ->
                val channelName = getString(R.string.notify_channel_name)

                //TODO 13 : Schedule and cancel daily reminder using WorkManager with data channelName
                if (newValue == true) {
                    startPeriodicTask(channelName)
                } else {
                    cancelPeriodicTask()
                }
                true
            }
        }

        private fun startPeriodicTask(channelName: String) {
            val data = Data.Builder()
                .putString(NOTIFICATION_CHANNEL_ID, channelName)
                .build()
            request = PeriodicWorkRequest.Builder(NotificationWorker::class.java, 24, TimeUnit.HOURS)
                .setInputData(data)
                .build()
            workManager.enqueue(request)
        }

        private fun cancelPeriodicTask() {
            if (::request.isInitialized) {
                workManager.cancelWorkById(request.id)
            }
        }

        private fun updateTheme(mode: Int): Boolean {
            AppCompatDelegate.setDefaultNightMode(mode)
            requireActivity().recreate()
            return true
        }
    }
}