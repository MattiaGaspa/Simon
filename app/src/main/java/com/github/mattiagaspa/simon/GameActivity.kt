package com.github.mattiagaspa.simon

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.github.mattiagaspa.simon.components.Keypad
import com.github.mattiagaspa.simon.components.SequenceVisualizer
import com.github.mattiagaspa.simon.components.Submit
import com.github.mattiagaspa.simon.logic.StateHolder

@Composable
fun GameScreen(modifier: Modifier = Modifier, stateHolder: StateHolder = StateHolder()) {
    val configuration = LocalConfiguration.current

    // Configuration.ORIENTATION_SQUARE and Configuration.ORIENTATION_UNDEFINED aren't necessary for a phone application
    when(configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            MainActivityPortrait(
                modifier = modifier,
                stateHolder = stateHolder
            )
        }

        Configuration.ORIENTATION_LANDSCAPE -> {
            MainActivityLandscape(
                modifier = modifier,
                stateHolder = stateHolder
            )
        }
    }
}

/** Composable function that builds the UI when the screen is in portrait mode
 * @param modifier The modifier to be applied to the activity screen
 * @param stateHolder Instance of MainActivityStateHolder that holds the states of the current activity
 */
@Composable
fun MainActivityPortrait(modifier: Modifier = Modifier, stateHolder: StateHolder = StateHolder()) {
    Column(
        modifier = modifier
    ) {
        Keypad(
            modifier = Modifier.align(Alignment.CenterHorizontally).weight(0.9f),
            stateHolder = stateHolder
        )
        SequenceVisualizer(
            modifier = Modifier.align(Alignment.CenterHorizontally).weight(0.2f),
            stateHolder = stateHolder
        )
        Submit(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            stateHolder = stateHolder
        )
    }
}

/** Composable function that builds the UI when the screen is in landscape mode
 * @param modifier The modifier to be applied to the activity screen
 * @param stateHolder Instance of MainActivityStateHolder that holds the states of the current activity
 */
@Composable
fun MainActivityLandscape(modifier: Modifier = Modifier, stateHolder: StateHolder = StateHolder()) {
    // Split screen in half: one half for keypad and the other half for the sequence and the buttons
    Row(
        modifier = modifier
    ) {
        Keypad(
            modifier = Modifier.weight(1f),
            stateHolder = stateHolder
        )
        Column(
            modifier = Modifier.fillMaxHeight().weight(1f)
        ) {
            SequenceVisualizer(
                modifier = Modifier.weight(1f).align(Alignment.CenterHorizontally),
                stateHolder = stateHolder
            )
            Submit(
                stateHolder = stateHolder
            )
        }
    }
}