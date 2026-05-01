package com.github.mattiagaspa.simon

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.github.mattiagaspa.simon.components.GameDetails
import com.github.mattiagaspa.simon.logic.StateHolder

@Composable
fun DetailsActivity(
    modifier: Modifier = Modifier,
    stateHolder: StateHolder = StateHolder(),
    index: Int
) {
    val configuration = LocalConfiguration.current

    // Configuration.ORIENTATION_SQUARE and Configuration.ORIENTATION_UNDEFINED aren't necessary for a phone application
    when(configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            DetailsActivityPortrait(
                modifier = modifier,
                stateHolder = stateHolder,
                index = index
            )
        }

        Configuration.ORIENTATION_LANDSCAPE -> {
            DetailsActivityLandscape(
                modifier = modifier,
                stateHolder = stateHolder,
                index = index
            )
        }
    }
}

/** Composable function that builds the UI when the screen is in portrait mode
 * @param modifier The modifier to be applied to the activity screen
 * @param stateHolder Instance of MainActivityStateHolder that holds the states of the current activity
 * @param index Index of the game to be seen
 */
@Composable
fun DetailsActivityPortrait(modifier: Modifier = Modifier, stateHolder: StateHolder = StateHolder(), index: Int) {
    GameDetails(
        modifier,
        stateHolder,
        index
    )
}

/** Composable function that builds the UI when the screen is in landscape mode
 * @param modifier The modifier to be applied to the activity screen
 * @param stateHolder Instance of MainActivityStateHolder that holds the states of the current activity
 * @param index Index of the game to be seen
 */
@Composable
fun DetailsActivityLandscape(modifier: Modifier = Modifier, stateHolder: StateHolder = StateHolder(), index: Int) {
    GameDetails(
        modifier,
        stateHolder,
        index
    )
}