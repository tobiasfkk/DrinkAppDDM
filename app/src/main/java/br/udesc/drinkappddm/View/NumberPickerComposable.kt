package br.udesc.drinkappddm.View

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NumberPicker(value: Int, onValueChange: (Int) -> Unit, range: IntRange) {
    var displayValue by remember { mutableStateOf(value.toString()) }

    Row {
        Button(onClick = {
            if (value > range.first) {
                onValueChange(value - 1)
                displayValue = (value - 1).toString()
            }
        }) {
            Text("-")
        }

        Spacer(modifier = Modifier.width(16.dp))

        BasicTextField(
            value = displayValue,
            onValueChange = {
                displayValue = it
                val newValue = it.toIntOrNull()
                if (newValue != null && newValue in range) {
                    onValueChange(newValue)
                }
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.width(16.dp))

        Button(onClick = {
            if (value < range.last) {
                onValueChange(value + 1)
                displayValue = (value + 1).toString()
            }
        }) {
            Text("+")
        }
    }
}
