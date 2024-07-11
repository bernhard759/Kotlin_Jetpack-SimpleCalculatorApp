package com.example.calculatortutapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.calculatortutapp.components.BottomNavItem
import com.example.calculatortutapp.components.BottomNavigation
import com.example.calculatortutapp.screens.CalculatorScreen
import com.example.calculatortutapp.screens.HomeScreen
import com.example.calculatortutapp.screens.SettingsScreen
import com.example.calculatortutapp.screens.SettingsScreen2
import com.example.calculatortutapp.stores.UserPreferencesRepository
import com.example.calculatortutapp.ui.theme.CalculatorTutAppTheme
import kotlinx.coroutines.flow.map

class MainActivity : ComponentActivity() {

    // Data store
    private val dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var userPreferencesRepository: UserPreferencesRepository


    // CREATE
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userPreferencesRepository = UserPreferencesRepository(dataStore)
        setContent {
            val isDarkMode by userPreferencesRepository.userPreferencesFlow
                .map { it.isDarkMode }
                .collectAsState(initial = false)

            CalculatorTutAppTheme(darkTheme = isDarkMode) {
                MainScreen(userPreferencesRepository)
            }
        }
    }
}

// Main Screen
@Composable
fun MainScreen(userPreferencesRepository: UserPreferencesRepository) {
    // Nav controller
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    // Scaffold ui stuff
    Scaffold(
        // Bottom bar
        bottomBar = {
            if (currentRoute != "settings2") {
                BottomNavigation(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Calculator.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Home.route) { HomeScreen(userPreferencesRepository = userPreferencesRepository) }
            composable(BottomNavItem.Calculator.route) {
                CalculatorScreen(onSettingsClick = {
                    navController.navigate("settings2")
                })
            }
            composable(BottomNavItem.Settings.route) { SettingsScreen() }
            composable("settings2") {
                SettingsScreen2(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    userPreferencesRepository = userPreferencesRepository
                )
            }
        }
    }
}
