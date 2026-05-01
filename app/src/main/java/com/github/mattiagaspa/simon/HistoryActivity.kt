package com.github.mattiagaspa.simon

import android.content.res.Configuration
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.github.mattiagaspa.simon.components.SequenceList
import com.github.mattiagaspa.simon.logic.StateHolder

@Composable
fun HistoryScreen(modifier: Modifier = Modifier, stateHolder: StateHolder = StateHolder()) {
    val configuration = LocalConfiguration.current

    // Configuration.ORIENTATION_SQUARE and Configuration.ORIENTATION_UNDEFINED aren't necessary for a phone application
    when(configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            HistoryActivityPortrait(
                modifier = modifier,
                stateHolder = stateHolder
            )
        }

        Configuration.ORIENTATION_LANDSCAPE -> {
            HistoryActivityLandscape(
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
fun HistoryActivityPortrait(modifier: Modifier = Modifier, stateHolder: StateHolder = StateHolder()) {
    SequenceList(
        modifier,
        stateHolder
    )
}

/** Composable function that builds the UI when the screen is in landscape mode
 * @param modifier The modifier to be applied to the activity screen
 * @param stateHolder Instance of MainActivityStateHolder that holds the states of the current activity
 */
@Composable
fun HistoryActivityLandscape(modifier: Modifier = Modifier, stateHolder: StateHolder = StateHolder()) {
    SequenceList(
        modifier,
        stateHolder
    )
}