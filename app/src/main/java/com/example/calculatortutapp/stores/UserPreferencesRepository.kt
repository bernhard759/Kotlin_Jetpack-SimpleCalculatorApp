package com.example.calculatortutapp.stores

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {

    private object PreferencesKeys {
        val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
        val DECIMAL_PLACES = intPreferencesKey("decimal_places")
    }

    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            UserPreferences(
                isDarkMode = preferences[PreferencesKeys.IS_DARK_MODE] ?: false,
                decimalPlaces = preferences[PreferencesKeys.DECIMAL_PLACES] ?: 2
            )
        }

    suspend fun updateIsDarkMode(isDarkMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_DARK_MODE] = isDarkMode
        }
    }

    suspend fun updateDecimalPlaces(decimalPlaces: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.DECIMAL_PLACES] = decimalPlaces
        }
    }
}
