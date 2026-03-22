package com.apkforge.calculator

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

/**
 * ViewModel الخاص بالآلة الحاسبة للتحكم في المنطق والحالات
 * CalculatorViewModel to handle calculation logic and states
 */
class CalculatorViewModel : ViewModel() {

    private val _displayState = mutableStateOf("0")
    val displayState: State<String> = _displayState

    private var firstOperand: Double? = null
    private var pendingOperator: String? = null
    private var shouldResetDisplay = false

    fun onAction(action: CalculatorAction) {
        when (action) {
            is CalculatorAction.Number -> enterNumber(action.number)
            is CalculatorAction.Operator -> enterOperator(action.operator)
            is CalculatorAction.Calculate -> calculateResult()
            is CalculatorAction.Clear -> clearAll()
            is CalculatorAction.Decimal -> enterDecimal()
            is CalculatorAction.Delete -> deleteLast()
        }
    }

    private fun enterNumber(number: Int) {
        if (_displayState.value == "0" || shouldResetDisplay) {
            _displayState.value = number.toString()
            shouldResetDisplay = false
        } else {
            _displayState.value += number.toString()
        }
    }

    private fun enterOperator(operator: String) {
        firstOperand = _displayState.value.toDoubleOrNull()
        pendingOperator = operator
        shouldResetDisplay = true
    }

    private fun calculateResult() {
        val secondOperand = _displayState.value.toDoubleOrNull()
        if (firstOperand != null && secondOperand != null && pendingOperator != null) {
            val result = when (pendingOperator) {
                "+" -> firstOperand!! + secondOperand
                "-" -> firstOperand!! - secondOperand
                "×" -> firstOperand!! * secondOperand
                "÷" -> if (secondOperand != 0.0) firstOperand!! / secondOperand else Double.NaN
                else -> 0.0
            }
            // تنسيق النتيجة لإزالة .0 إذا كان رقماً صحيحاً
            _displayState.value = if (result % 1 == 0.0) result.toInt().toString() else result.toString()
            firstOperand = null
            pendingOperator = null
            shouldResetDisplay = true
        }
    }

    private fun clearAll() {
        _displayState.value = "0"
        firstOperand = null
        pendingOperator = null
        shouldResetDisplay = false
    }

    private fun enterDecimal() {
        if (!shouldResetDisplay && !_displayState.value.contains(".")) {
            _displayState.value += "."
        } else if (shouldResetDisplay) {
            _displayState.value = "0."
            shouldResetDisplay = false
        }
    }

    private fun deleteLast() {
        if (_displayState.value.length > 1) {
            _displayState.value = _displayState.value.dropLast(1)
        } else {
            _displayState.value = "0"
        }
    }
}

sealed class CalculatorAction {
    data class Number(val number: Int) : CalculatorAction()
    data class Operator(val operator: String) : CalculatorAction()
    object Calculate : CalculatorAction()
    object Clear : CalculatorAction()
    object Decimal : CalculatorAction()
    object Delete : CalculatorAction()
}