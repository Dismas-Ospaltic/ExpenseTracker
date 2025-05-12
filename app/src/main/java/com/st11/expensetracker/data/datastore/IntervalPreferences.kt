package com.st11.expensetracker.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extension property for DataStore
private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_interval_prefs")

class IntervalPreferences(private val context: Context) {

    companion object {
        val TIME_INTERVAL = longPreferencesKey("user_time_interval") // ✅ Use correct type key
    }

    // Save user time interval
    suspend fun saveUserData(userTimeInterval: Long) {
        context.userDataStore.edit { preferences ->
            preferences[TIME_INTERVAL] = userTimeInterval
        }
    }

    // Observe user time interval
    val userTimeData: Flow<UserTimeData> = context.userDataStore.data.map { preferences ->
        UserTimeData(
            userTimeInterval = preferences[TIME_INTERVAL] ?: 1L // ✅ Constructor call fixed
        )
    }

    // Clear all preferences
    suspend fun clearUserData() {
        context.userDataStore.edit { preferences ->
            preferences.clear()
        }
    }
}

// ✅ Correct name used throughout
data class UserTimeData(
    val userTimeInterval: Long
)
