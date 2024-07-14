package com.example.calculatortutapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.calculatortutapp.ui.components.BottomNavItem
import com.example.calculatortutapp.ui.components.BottomNavigation
import com.example.calculatortutapp.database.db.CalculatorDatabase
import com.example.calculatortutapp.database.repository.CalculatorRepository
import com.example.calculatortutapp.ui.screens.CalculatorScreen
import com.example.calculatortutapp.ui.screens.DBScreen
import com.example.calculatortutapp.ui.screens.SettingsScreen
import com.example.calculatortutapp.stores.UserPreferencesRepository
import com.example.calculatortutapp.ui.theme.CalculatorTutAppTheme
import com.example.calculatortutapp.viewmodel.CalculatorViewModel
import kotlinx.coroutines.flow.map

class MainActivity : ComponentActivity() {

    // Data store
    private lateinit var userPreferencesRepository: UserPreferencesRepository

    // DB Calculator repository for the room database
    private lateinit var calculatorRepository: CalculatorRepository


    // CREATE
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Get UserPreferencesRepository from Application
        userPreferencesRepository = (application as CalculatorApp).userPreferencesRepository

        // DB Setup
        val database = CalculatorDatabase.getDatabase(applicationContext)
        calculatorRepository = CalculatorRepository(database.calculationDao())

        // App content
        setContent {
            val isDarkMode by userPreferencesRepository.userPreferencesFlow
                .map { it.isDarkMode }
                .collectAsState(initial = false)
            CalculatorTutAppTheme(darkTheme = isDarkMode) {
                MainScreen(userPreferencesRepository, calculatorRepository)
            }
        }
    }
}

// Main Screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    userPreferencesRepository: UserPreferencesRepository,
    calculatorRepository: CalculatorRepository
) {
    // Nav controller
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    // Scaffold ui stuff
    Scaffold(
        topBar = {
            if (currentRoute != "settings") {
                TopAppBar(
                    title = { Text("Calculator") },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    ),
                    actions = {
                        IconButton(onClick = { navController.navigate("settings") }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Settings"
                            )
                        }
                    },
                )
            }
        },
        // Bottom bar
        bottomBar = {
            if (currentRoute != "settings") {
                BottomNavigation(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Calculator.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Calculator.route) {
                val viewModel: CalculatorViewModel = viewModel {
                    CalculatorViewModel(calculatorRepository)
                }
                CalculatorScreen(calculatorRepository,
                    viewModel = viewModel,
                    userPreferencesRepository = userPreferencesRepository,
                    onSettingsClick = {
                        navController.navigate("settings")
                    })
            }
            composable(BottomNavItem.DB.route) {
                val viewModel: CalculatorViewModel = viewModel {
                    CalculatorViewModel(calculatorRepository)
                }
                DBScreen(
                    viewModel = viewModel
                )
            }
            composable("settings") {
                SettingsScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    userPreferencesRepository = userPreferencesRepository
                )
            }
        }
    }
}
