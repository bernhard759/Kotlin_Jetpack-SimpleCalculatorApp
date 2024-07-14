package com.example.calculatortutapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.calculatortutapp.database.repository.CalculatorRepository
import com.example.calculatortutapp.model.CalculatorAction
import com.example.calculatortutapp.stores.UserPreferences
import com.example.calculatortutapp.stores.UserPreferencesRepository
import com.example.calculatortutapp.viewmodel.CalculatorViewModel
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(
    repository: CalculatorRepository,
    userPreferencesRepository: UserPreferencesRepository,
    viewModel: CalculatorViewModel,
    onSettingsClick: () -> Unit
) {
    // Init user preferences
    val userPreferences by userPreferencesRepository.userPreferencesFlow.collectAsState(
        initial = UserPreferences()
    )

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            item {
                Column {
                    Text(
                        text = viewModel.num1 + viewModel.operator + viewModel.num2,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = "Result: ${
                            formatResult(
                                viewModel.result,
                                userPreferences.decimalPlaces
                            )
                        }",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        CalculatorButton(text = "7") { viewModel.onAction(CalculatorAction.Number(7)) }
                        CalculatorButton(text = "8") { viewModel.onAction(CalculatorAction.Number(8)) }
                        CalculatorButton(text = "9") { viewModel.onAction(CalculatorAction.Number(9)) }
                        CalculatorButton(text = "+") {
                            viewModel.onAction(
                                CalculatorAction.Operator(
                                    "+"
                                )
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        CalculatorButton(text = "4") { viewModel.onAction(CalculatorAction.Number(4)) }
                        CalculatorButton(text = "5") { viewModel.onAction(CalculatorAction.Number(5)) }
                        CalculatorButton(text = "6") { viewModel.onAction(CalculatorAction.Number(6)) }
                        CalculatorButton(text = "-") {
                            viewModel.onAction(
                                CalculatorAction.Operator(
                                    "-"
                                )
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        CalculatorButton(text = "1") { viewModel.onAction(CalculatorAction.Number(1)) }
                        CalculatorButton(text = "2") { viewModel.onAction(CalculatorAction.Number(2)) }
                        CalculatorButton(text = "3") { viewModel.onAction(CalculatorAction.Number(3)) }
                        CalculatorButton(text = "*") {
                            viewModel.onAction(
                                CalculatorAction.Operator(
                                    "*"
                                )
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        CalculatorButton(text = "C") { viewModel.onAction(CalculatorAction.Clear) }
                        CalculatorButton(text = "0") { viewModel.onAction(CalculatorAction.Number(0)) }
                        CalculatorButton(text = "=") { viewModel.onAction(CalculatorAction.Calculate) }
                        CalculatorButton(text = "/") {
                            viewModel.onAction(
                                CalculatorAction.Operator(
                                    "/"
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun CalculatorButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(8.dp)
            .size(64.dp)
    ) {
        Text(text = text)
    }
}

// Helper function to format the result
fun formatResult(value: String, digits: Int): String {
    if (value == "") return ""
    return "%.${digits}f".format(value.toDouble())
}
