package com.mcecelja.pocket.data

import android.content.Context
import com.mcecelja.pocket.Pocket
import com.mcecelja.pocket.enums.PreferenceEnum

class PreferenceManager {

    companion object {
        private const val PREFS_FILE = "preferences"

        fun getPreference(preference: PreferenceEnum): String {
            return Pocket.application.getSharedPreferences(
                PREFS_FILE, Context.MODE_PRIVATE
            )
                .getString(preference.key, "") ?: ""
        }

        fun savePreference(preference: PreferenceEnum, value: String?) {
            Pocket.application.getSharedPreferences(
                PREFS_FILE, Context.MODE_PRIVATE
            )
                .edit()
                .putString(preference.key, value)
                .apply()
        }

        fun removePreference(preference: PreferenceEnum) {
            Pocket.application.getSharedPreferences(
                PREFS_FILE, Context.MODE_PRIVATE
            )
                .edit()
                .remove(preference.key)
                .apply()
        }
    }
}