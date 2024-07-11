package com.example.calculatortutapp.model

sealed class CalculatorAction {
    data class Number(val number: Int) : CalculatorAction()
    data class Operator(val operator: String) : CalculatorAction()
    object Calculate : CalculatorAction()
    object Clear : CalculatorAction()
}