package com.github.mattiagaspa.simon

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.mattiagaspa.simon.components.SequenceList
import com.github.mattiagaspa.simon.logic.SimonViewModel

/** Composable function that defines the behavior of the history screen
 * @param modifier The modifier to be applied
 * @param viewModel The `SimonViewModel` to be used
 * @param gameDetails Callback function to be called when a game is selected
 * @param startGame Callback function to be called when the user wants to start a new game
 */
@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: SimonViewModel,
    gameDetails: (Int) -> Unit,
    startGame: () -> Unit
) {
    val configuration = LocalConfiguration.current

    // Configuration.ORIENTATION_SQUARE and Configuration.ORIENTATION_UNDEFINED aren't necessary for a phone application
    when(configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            HistoryActivityPortrait(
                modifier = modifier,
                viewModel = viewModel,
                gameDetails = gameDetails,
                startGame = startGame
            )
        }

        Configuration.ORIENTATION_LANDSCAPE -> {
            HistoryActivityLandscape(
                modifier = modifier,
                viewModel = viewModel,
                gameDetails = gameDetails,
                startGame = startGame
            )
        }
    }
}

/** Composable function that builds the UI when the screen is in portrait mode
 * @param modifier The modifier to be applied
 * @param viewModel The `SimonViewModel` to be used
 * @param gameDetails Callback function to be called when a game is selected
 * @param startGame Callback function to be called when the user wants to start a new game
 */
@Composable
fun HistoryActivityPortrait(modifier: Modifier = Modifier,
                            viewModel: SimonViewModel,
                            gameDetails: (Int) -> Unit,
                            startGame: () -> Unit) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.app_name).uppercase(),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)
        )
        SequenceList(
            Modifier.weight(1f),
            viewModel,
            gameDetails,
            startGame
        )
    }
}

/** Composable function that builds the UI when the screen is in landscape mode
 * @param modifier The modifier to be applied
 * @param viewModel The `SimonViewModel` to be used
 * @param gameDetails Callback function to be called when a game is selected
 * @param startGame Callback function to be called when the user wants to start a new game
 */
@Composable
fun HistoryActivityLandscape(modifier: Modifier = Modifier,
                             viewModel: SimonViewModel,
                             gameDetails: (Int) -> Unit,
                             startGame: () -> Unit) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.app_name).uppercase(),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)
        )
        SequenceList(
            Modifier.weight(1f),
            viewModel,
            gameDetails,
            startGame
        )
    }
}