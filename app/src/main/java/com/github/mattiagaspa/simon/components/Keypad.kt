package com.github.mattiagaspa.simon.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.github.mattiagaspa.simon.stateHolders.MainActivityStateHolder
import com.github.mattiagaspa.simon.ui.theme.*

@Composable
fun Keypad(modifier: Modifier = Modifier, stateHolder: MainActivityStateHolder = MainActivityStateHolder()) {
    val colorDisposition = arrayOf(
        arrayOf(Red, Green, Blue),
        arrayOf(Cyan, Magenta, Yellow)
    )

    Column(
        modifier = modifier.padding(vertical = 1.dp)
    ) {
        for (row in colorDisposition) {
            Row(
                modifier = Modifier.padding(horizontal = 2.dp)
            ) {
                for (color in row) {
                    Button(
                        onClick = { stateHolder.addColor(color) },
                        modifier = Modifier.weight(1f).padding(horizontal = 4.dp),
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