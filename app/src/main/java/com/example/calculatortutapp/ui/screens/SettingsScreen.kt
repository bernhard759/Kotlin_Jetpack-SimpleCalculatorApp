@file:JvmName("SettingsScreenKt")

package com.example.calculatortutapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.calculatortutapp.stores.UserPreferences
import com.example.calculatortutapp.stores.UserPreferencesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onBackClick: () -> Unit,
                    userPreferencesRepository: UserPreferencesRepository
) {
    // Init user preferences
    val userPreferences by userPreferencesRepository.userPreferencesFlow.collectAsState(
        initial = UserPreferences()
    )
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Switch(
                checked = userPreferences.isDarkMode,
                onCheckedChange = {
                    scope.launch {
                        userPreferencesRepository.updateIsDarkMode(it)
                    }
                },
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text("Dark Mode", modifier = Modifier.padding(start = 8.dp))

            Slider(
                value = userPreferences.decimalPlaces.toFloat(),
                onValueChange = {
                    scope.launch {
                        userPreferencesRepository.updateDecimalPlaces(it.toInt())
                    }
                },
                valueRange = 0f..4f,
                steps = 4,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text("Decimal Places: ${userPreferences.decimalPlaces}", modifier = Modifier.padding(start = 8.dp))
        }
    }
}