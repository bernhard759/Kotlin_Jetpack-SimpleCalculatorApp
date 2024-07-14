package com.example.calculatortutapp

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.calculatortutapp.stores.UserPreferencesRepository

class CalculatorApp : Application() {
    private val dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreate() {
        super.onCreate()
        userPreferencesRepository = UserPreferencesRepository(dataStore)
    }
}
