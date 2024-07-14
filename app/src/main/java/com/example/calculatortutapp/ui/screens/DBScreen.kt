package com.example.calculatortutapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.calculatortutapp.database.entities.Calculation
import com.example.calculatortutapp.viewmodel.CalculatorViewModel

@Composable
fun DBScreen(viewModel: CalculatorViewModel) {
    val calculations by viewModel.allCalculations.observeAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Calculation History", style = MaterialTheme.typography.headlineMedium)
        Box(modifier = Modifier.weight(1f)) {
            // Lazy column for displaying the db entries as cards
            LazyColumn {
                items(calculations) { calculation ->
                    CalculationItem(calculation)
                }
            }
        }
        Button(onClick = { viewModel.clearHistory() }) {
            Text("Clear History")
        }
    }
}

@Composable
fun CalculationItem(calculation: Calculation) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Expression: ${calculation.expression}")
            Text(text = "Result: ${calculation.result}")
        }
    }
}
