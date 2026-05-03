package com.github.mattiagaspa.simon

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.github.mattiagaspa.simon.components.Keypad
import com.github.mattiagaspa.simon.components.SequenceVisualizer
import com.github.mattiagaspa.simon.components.Submit
import com.github.mattiagaspa.simon.logic.SimonViewModel

/** Composable function that defines the behavior of the game screen
 * @param modifier The modifier to be applied
 * @param viewModel The `SimonViewModel` to be used
 */
@Composable
fun GameScreen(modifier: Modifier = Modifier, viewModel: SimonViewModel = SimonViewModel()) {
    val configuration = LocalConfiguration.current

    // Configuration.ORIENTATION_SQUARE and Configuration.ORIENTATION_UNDEFINED aren't necessary for a phone application
    when(configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            MainActivityPortrait(
                modifier = modifier,
                viewModel = viewModel
            )
        }

        Configuration.ORIENTATION_LANDSCAPE -> {
            MainActivityLandscape(
                modifier = modifier,
                viewModel = viewModel
            )
        }
    }
}

/** Composable function that builds the UI when the screen is in portrait mode
 * @param modifier The modifier to be applied
 * @param viewModel The `SimonViewModel` to be used
 */
@Composable
fun MainActivityPortrait(modifier: Modifier = Modifier, viewModel: SimonViewModel = SimonViewModel()) {
    Column(
        modifier = modifier
    ) {
        Keypad(
            modifier = Modifier.align(Alignment.CenterHorizontally).weight(0.9f),
            viewModel = viewModel
        )
        SequenceVisualizer(
            modifier = Modifier.align(Alignment.CenterHorizontally).weight(0.2f),
            viewModel = viewModel
        )
        Submit(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            viewModel = viewModel
        )
    }
}

/** Composable function that builds the UI when the screen is in landscape mode
 * @param modifier The modifier to be applied
 * @param viewModel The `SimonViewModel` to be used
 */
@Composable
fun MainActivityLandscape(modifier: Modifier = Modifier, viewModel: SimonViewModel = SimonViewModel()) {
    // Split screen in half: one half for keypad and the other half for the sequence and the buttons
    Row(
        modifier = modifier
    ) {
        Keypad(
            modifier = Modifier.weight(1f),
            viewModel = viewModel
        )
        Column(
            modifier = Modifier.fillMaxHeight().weight(1f)
        ) {
            SequenceVisualizer(
                modifier = Modifier.weight(1f).align(Alignment.CenterHorizontally),
                viewModel = viewModel
            )
            Submit(
                viewModel = viewModel
            )
        }
    }
}