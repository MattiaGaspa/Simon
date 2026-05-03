package com.github.mattiagaspa.simon

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.github.mattiagaspa.simon.components.GameDetails
import com.github.mattiagaspa.simon.logic.SimonViewModel

/** Composable function that defines the behavior of the details screen
 * @param modifier The modifier to be applied
 * @param viewModel The `SimonViewModel` to be used
 * @param index The index of the match to be shown
 */
@Composable
fun DetailsActivity(
    modifier: Modifier = Modifier,
    viewModel: SimonViewModel = SimonViewModel(),
    index: Int
) {
    val configuration = LocalConfiguration.current

    // Configuration.ORIENTATION_SQUARE and Configuration.ORIENTATION_UNDEFINED aren't necessary for a phone application
    when(configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            DetailsActivityPortrait(
                modifier = modifier,
                viewModel = viewModel,
                index = index
            )
        }

        Configuration.ORIENTATION_LANDSCAPE -> {
            DetailsActivityLandscape(
                modifier = modifier,
                viewModel = viewModel,
                index = index
            )
        }
    }
}

/** Composable function that builds the UI when the screen is in portrait mode
 * @param modifier The modifier to be applied
 * @param viewModel The `SimonViewModel` to be used
 * @param index The index of the match to be shown
 */
@Composable
fun DetailsActivityPortrait(modifier: Modifier = Modifier,
                            viewModel: SimonViewModel = SimonViewModel(),
                            index: Int) {
    GameDetails(
        modifier,
        viewModel,
        index
    )
}

/** Composable function that builds the UI when the screen is in landscape mode
 * @param modifier The modifier to be applied
 * @param viewModel The `SimonViewModel` to be used
 * @param index The index of the match to be shown
 */
@Composable
fun DetailsActivityLandscape(modifier: Modifier = Modifier,
                             viewModel: SimonViewModel = SimonViewModel(),
                             index: Int) {
    GameDetails(
        modifier,
        viewModel,
        index
    )
}