package com.github.mattiagaspa.simon.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.mattiagaspa.simon.R
import com.github.mattiagaspa.simon.ui.theme.*

@Composable
fun Keypad(modifier: Modifier = Modifier, onSequenceChange: (String) -> Unit) {
    val colorDisposition = arrayOf(
        arrayOf(Red, Green, Blue),
        arrayOf(Cyan, Magenta, Yellow)
    )

    Column(
        modifier = modifier.padding(vertical = 1.dp)
    ) {
        for (row in colorDisposition) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 2.dp)
            ) {
                for (color in row) {
                    val colorName = colorName(color)
                    Button(
                        onClick = { onSequenceChange(colorName) },
                        modifier = Modifier.weight(1f).padding(horizontal = 4.dp),
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = color
                        )
                    ) {
                        // Text(colorName)
                    }
                }
            }
        }
    }
}

@Composable
fun colorName(color: Color): String {
    return when(color) {
        Red -> "Red" // stringResource(R.string.red)
        Green -> "Green" // stringResource(R.string.green)
        Blue -> "Blue" // stringResource(R.string.blue)
        Cyan -> "Cyan" // stringResource(R.string.cyan)
        Magenta -> "Magenta" // stringResource(R.string.magenta)
        Yellow -> "Yellow" // stringResource(R.string.yellow)
        else -> "Unknown color"// stringResource(R.string.unknown_color)
    }
}