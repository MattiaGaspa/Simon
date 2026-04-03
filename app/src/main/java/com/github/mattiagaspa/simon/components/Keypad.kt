package com.github.mattiagaspa.simon.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.github.mattiagaspa.simon.stateHolders.MainActivityStateHolder
import com.github.mattiagaspa.simon.ui.theme.*

// Composable to arrange the 3x2 buttons array.
// Takes a MainActivityStateHolder object to add the first letter of the color pressed to the current sequence (with the method .addColor())
@Composable
fun Keypad(modifier: Modifier = Modifier, stateHolder: MainActivityStateHolder = MainActivityStateHolder()) {
    val colorDisposition = arrayOf(
        arrayOf(Red, Green, Blue),
        arrayOf(Cyan, Magenta, Yellow)
    )

    Column(
        modifier = modifier.fillMaxWidth().padding(7.dp)
    ) {
        for (row in colorDisposition) {
            Row(
                modifier = Modifier.weight(1f)
            ) {
                for (color in row) {
                    Button(
                        onClick = { stateHolder.addColor(color) },
                        modifier = Modifier.weight(1f).padding(5.dp).fillMaxHeight(),
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = color
                        )
                    ) {  }
                }
            }
        }
    }
}