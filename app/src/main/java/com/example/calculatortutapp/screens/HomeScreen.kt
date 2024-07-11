package com.example.calculatortutapp.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.calculatortutapp.stores.UserPreferences
import com.example.calculatortutapp.stores.UserPreferencesRepository

@Composable
fun HomeScreen(userPreferencesRepository: UserPreferencesRepository) {
    val userPreferences by userPreferencesRepository.userPreferencesFlow.collectAsState(
        initial = UserPreferences()
    )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Home Screen")
        Text(
            text = "Decimal Places: ${userPreferences.decimalPlaces}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
