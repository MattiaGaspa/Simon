package com.github.mattiagaspa.simon.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.mattiagaspa.simon.R
import com.github.mattiagaspa.simon.logic.SimonViewModel

/** Composable function to display games button.
 * The user starts the game by pressing the `Start game` button. The sequence is generated until the game is over.
 * When the sequence is played, the user cna stop it by pressing the `Pause` button. The user can resume the game by pressing the `Resume` button.
 * The user can end the game by pressing the `End game` button.
 * @param modifier The modifier to be applied to the `Submit`
 * @param viewModel The `SimonViewModel` to be used
 * @param onBack Action to be performed to get back to home screen
 */
@Composable
fun Submit(
    modifier: Modifier = Modifier,
    viewModel: SimonViewModel,
    onBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Row(
        modifier = modifier.padding(4.dp)
    ) {
        Button(
            onClick = {
                viewModel.startGame()
                viewModel.generateSequence()
            },
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .weight(1f),
            enabled = !uiState.isGameStarted
        ) {
            Text(
                text = stringResource(R.string.start_game),
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1
            )
        }

        Button(
            onClick = { viewModel.togglePauseResume() },
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .weight(1f),
            enabled = uiState.isGameStarted && uiState.isSequencePlayed
        ) {
            Text(
                text = stringResource(if (uiState.isGamePaused) R.string.resume else R.string.pause),
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1
            )
        }

        Button(
            onClick = {
                onBack()
            },
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .weight(1f),
            enabled = uiState.isGameStarted and !uiState.isGameOver
        ) {
            Text(
                text = stringResource(R.string.end_game),
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1
            )
        }
    }
}