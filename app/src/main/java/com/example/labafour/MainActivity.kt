
package com.example.labafour

import androidx.compose.material3.Button
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.labafour.ui.theme.LabaFourTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LabaFourTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CycleScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}




fun calculateSeries(epsilon: Double): Triple<Double, Double, Int> {
    var sum = 0.0
    var n = 1
    var count = 0
    var lastTerm = 0.0

    while (true) {
        var factorial = 1.0

        for (i in 1..n) {
            factorial *= i
        }

        val term = 1.0 / factorial

        android.util.Log.d(
            "CycleLab",
            "CHECK: n=$n, term=$term, epsilon=$epsilon, stop=${kotlin.math.abs(term) < epsilon}"
        )

        if (kotlin.math.abs(term) < epsilon) {
            break
        }

        sum += term
        lastTerm = term
        count++
        n += 2
    }

    android.util.Log.d(
        "CycleLab",
        "FINAL: sum=$sum, lastTerm=$lastTerm, count=$count"
    )

    return Triple(sum, lastTerm, count)
}






@Composable
fun CycleScreen(modifier: Modifier = Modifier) {
    var epsilon by remember { mutableStateOf("0.000001") }
    var message by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Вычисление суммы ряда",
            style = MaterialTheme.typography.headlineSmall
        )

        OutlinedTextField(
            value = epsilon,
            onValueChange = { epsilon = it },
            label = { Text("Точность ε") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            singleLine = true
        )

        Button(
            onClick = {
                val value = epsilon.toDoubleOrNull()

                message = if (value != null && value > 0) {
                    val result = calculateSeries(value)
                    "Точность: $value\n" +
                            "Сумма: ${result.first}\n" +
                            "Последнее слагаемое: ${result.second}\n" +
                            "Повторений: ${result.third}"
                } else {
                    "Введите положительное число"
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Вычислить")
        }


        Text(
            text = message,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
    }
}



