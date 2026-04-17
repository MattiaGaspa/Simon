package com.github.mattiagaspa.simon.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.github.mattiagaspa.simon.stateHolders.MainActivityStateHolder
import com.github.mattiagaspa.simon.ui.theme.*

/** Composable function that arranges buttons in a 3x2 grid.
 * On button press, it adds the initial of the color with the `stateHolder.addColor()`
 * @param modifier The modifier to be applied to the `Keypad`
 * @param stateHolder Instance of `MainActivityStateHolder` that holds the states of the current activity
 */
@Composable
fun Keypad(modifier: Modifier = Modifier, stateHolder: MainActivityStateHolder = MainActivityStateHolder()) {
    val colorDisposition = arrayOf(
        arrayOf(Red, Green),
        arrayOf(Blue, Cyan),
        arrayOf(Magenta, Yellow)
    )

    Column(
        modifier = modifier.fillMaxWidth().padding(7.dp)
    ) {
        colorDisposition.forEach { row ->
            Row(
                modifier = Modifier.weight(1f)
            ) {
                row.forEach { color ->
                    Button(
                        onClick = {
                            stateHolder.addColor(color)
                        },
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