package com.example.calculatortutapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.calculatortutapp.model.CalculatorAction


class CalculatorViewModel : ViewModel() {

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
    }

    private fun clear() {
        num1 = ""
        num2 = ""
        operator = ""
        result = ""
    }
}


