package com.apkforge.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalculatorScreen()
                }
            }
        }
    }
}

@Composable
fun CalculatorScreen(viewModel: CalculatorViewModel = viewModel()) {
    val state = viewModel.displayState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        // شاشة العرض (الرقم الحالي)
        Text(
            text = state,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp),
            fontSize = 64.sp,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.End,
            maxLines = 1,
            color = MaterialTheme.colorScheme.onBackground
        )

        // شبكة الأزرار
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                CalculatorButton("AC", Modifier.weight(1f), MaterialTheme.colorScheme.errorContainer) { viewModel.onAction(CalculatorAction.Clear) }
                CalculatorButton("⌫", Modifier.weight(1f), MaterialTheme.colorScheme.secondaryContainer) { viewModel.onAction(CalculatorAction.Delete) }
                CalculatorButton("÷", Modifier.weight(1f), MaterialTheme.colorScheme.primaryContainer) { viewModel.onAction(CalculatorAction.Operator("÷")) }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                CalculatorButton("7", Modifier.weight(1f)) { viewModel.onAction(CalculatorAction.Number(7)) }
                CalculatorButton("8", Modifier.weight(1f)) { viewModel.onAction(CalculatorAction.Number(8)) }
                CalculatorButton("9", Modifier.weight(1f)) { viewModel.onAction(CalculatorAction.Number(9)) }
                CalculatorButton("×", Modifier.weight(1f), MaterialTheme.colorScheme.primaryContainer) { viewModel.onAction(CalculatorAction.Operator("×")) }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                CalculatorButton("4", Modifier.weight(1f)) { viewModel.onAction(CalculatorAction.Number(4)) }
                CalculatorButton("5", Modifier.weight(1f)) { viewModel.onAction(CalculatorAction.Number(5)) }
                CalculatorButton("6", Modifier.weight(1f)) { viewModel.onAction(CalculatorAction.Number(6)) }
                CalculatorButton("-", Modifier.weight(1f), MaterialTheme.colorScheme.primaryContainer) { viewModel.onAction(CalculatorAction.Operator("-")) }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                CalculatorButton("1", Modifier.weight(1f)) { viewModel.onAction(CalculatorAction.Number(1)) }
                CalculatorButton("2", Modifier.weight(1f)) { viewModel.onAction(CalculatorAction.Number(2)) }
                CalculatorButton("3", Modifier.weight(1f)) { viewModel.onAction(CalculatorAction.Number(3)) }
                CalculatorButton("+", Modifier.weight(1f), MaterialTheme.colorScheme.primaryContainer) { viewModel.onAction(CalculatorAction.Operator("+")) }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                CalculatorButton("0", Modifier.weight(2f)) { viewModel.onAction(CalculatorAction.Number(0)) }
                CalculatorButton(".", Modifier.weight(1f)) { viewModel.onAction(CalculatorAction.Decimal) }
                CalculatorButton("=", Modifier.weight(1f), MaterialTheme.colorScheme.primary) { viewModel.onAction(CalculatorAction.Calculate) }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorButton(
    text: String,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier.aspectRatio(if (text == "0") 2.1f else 1f),
        shape = CircleShape,
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(
                text = text,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = contentColorFor(containerColor)
            )
        }
    }
}