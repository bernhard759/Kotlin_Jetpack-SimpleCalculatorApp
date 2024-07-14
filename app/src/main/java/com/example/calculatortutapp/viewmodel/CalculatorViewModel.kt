package com.example.calculatortutapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.calculatortutapp.database.entities.Calculation
import com.example.calculatortutapp.database.repository.CalculatorRepository
import com.example.calculatortutapp.model.CalculatorAction
import kotlinx.coroutines.launch


class CalculatorViewModel(private val repository: CalculatorRepository) : ViewModel() {

    // State
    var num1 by mutableStateOf("")
    var num2 by mutableStateOf("")
    var operator by mutableStateOf("")
    var result by mutableStateOf("")

    fun onAction(action: CalculatorAction) {
        when (action) {
            is CalculatorAction.Number -> enterNumber(action.number)
            is CalculatorAction.Operator -> enterOperator(action.operator)
            is CalculatorAction.Calculate -> calculate()
            is CalculatorAction.Clear -> clear()
        }
    }

    private fun enterNumber(number: Int) {
        if (operator.isEmpty()) {
            num1 += number.toString()
        } else {
            num2 += number.toString()
        }
    }

    private fun enterOperator(op: String) {
        operator = op
    }

    private fun calculate() {
        val n1 = num1.toDoubleOrNull() ?: return
        val n2 = num2.toDoubleOrNull() ?: return
        result = when (operator) {
            "+" -> (n1 + n2).toString()
            "-" -> (n1 - n2).toString()
            "*" -> (n1 * n2).toString()
            "/" -> if (n2 != 0.0) (n1 / n2).toString() else "Error"
            else -> ""
        }
        // Insert into DB
        insert("$n1 $operator $n2", result)
    }

    private fun clear() {
        num1 = ""
        num2 = ""
        operator = ""
        result = ""
    }

    val allCalculations: LiveData<List<Calculation>> = repository.allCalculations.asLiveData()

    fun insert(expression: String, result: String) = viewModelScope.launch {
        repository.insert(Calculation(expression = expression, result = result))
    }

    fun clearHistory() = viewModelScope.launch {
        repository.deleteAll()
    }
}


