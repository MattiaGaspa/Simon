package com.github.mattiagaspa.simon

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.github.mattiagaspa.simon.components.SequenceList
import com.github.mattiagaspa.simon.logic.StateHolder

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    stateHolder: StateHolder = StateHolder(),
    gameDetails: (Int) -> Unit,
    startGame: () -> Unit
) {
    val configuration = LocalConfiguration.current

    // Configuration.ORIENTATION_SQUARE and Configuration.ORIENTATION_UNDEFINED aren't necessary for a phone application
    when(configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            HistoryActivityPortrait(
                modifier = modifier,
                stateHolder = stateHolder,
                gameDetails = gameDetails,
                startGame = startGame
            )
        }

        Configuration.ORIENTATION_LANDSCAPE -> {
            HistoryActivityLandscape(
                modifier = modifier,
                stateHolder = stateHolder,
                gameDetails = gameDetails,
                startGame = startGame
            )
        }
    }
}

/** Composable function that builds the UI when the screen is in portrait mode
 * @param modifier The modifier to be applied to the activity screen
 * @param stateHolder Instance of MainActivityStateHolder that holds the states of the current activity
 * @param gameDetails Action to be performed when clicking a game
 * @param startGame Action to be performed when the `Start game` button is pressed
 */
@Composable
fun HistoryActivityPortrait(modifier: Modifier = Modifier,
                            stateHolder: StateHolder = StateHolder(),
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
            stateHolder,
            gameDetails,
            startGame
        )
    }
}

/** Composable function that builds the UI when the screen is in landscape mode
 * @param modifier The modifier to be applied to the activity screen
 * @param stateHolder Instance of MainActivityStateHolder that holds the states of the current activity
 * @param gameDetails Action to be performed when clicking a game
 * @param startGame Action to be performed when the `Start game` button is pressed
 */
@Composable
fun HistoryActivityLandscape(modifier: Modifier = Modifier,
                             stateHolder: StateHolder = StateHolder(),
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
            stateHolder,
            gameDetails,
            startGame
        )
    }
}