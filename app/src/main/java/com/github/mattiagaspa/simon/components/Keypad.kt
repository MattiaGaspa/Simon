package com.github.mattiagaspa.simon.components

import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.mattiagaspa.simon.logic.SimonViewModel
import com.github.mattiagaspa.simon.ui.theme.*

/** Composable function that arranges buttons in a 3x2 grid.
 * The buttons light up when the user presses them and when the sequence is played.
 * Documentation used to manager press interactions: [handling interactions](https://developer.android.com/develop/ui/compose/touch-input/user-interactions/handling-interactions)
 * @param modifier The modifier to be applied
 * @param viewModel The `SimonViewModel` to be used
 */
@Composable
fun Keypad(modifier: Modifier = Modifier, viewModel: SimonViewModel = SimonViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val colorDisposition = arrayOf(
        arrayOf(Red, Green),
        arrayOf(Blue, Cyan),
        arrayOf(Magenta, Yellow)
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(7.dp)
    ) {
        colorDisposition.forEach { row ->
            Row(
                modifier = Modifier.weight(1f)
            ) {
                row.forEach { color ->
                    val interactionSource = remember { MutableInteractionSource() }
                    val isPressed by interactionSource.collectIsPressedAsState()
                    Button(
                        onClick = {
                            // The button works only when the game is started and the sequence is not playing
                            if (uiState.isGameStarted and !uiState.isSequencePlayed) {
                                viewModel.addUserColor(color)
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(5.dp)
                            .fillMaxHeight(),
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(
                            // Light up only when pressed or the sequence plays the current button
                            containerColor = if (uiState.buttonColorAnimation == color || isPressed)
                                color
                            else
                                color.copy(alpha = 0.5f)

                        ),
                        interactionSource = interactionSource
                    ) {  }
                }
            }
        }
    }
}