package com.github.mattiagaspa.simon.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.mattiagaspa.simon.R
import com.github.mattiagaspa.simon.logic.StateHolder

/** Composable function to display games button.
 * Use `Cancel` to clear the current sequence, by calling `stateHolder.clearSequence()`.
 * Use `End game` to clear the current sequence and add the game to history, by calling `stateHolder.updateAllSequences()`.
 * @param modifier The modifier to be applied to the `Submit`
 * @param stateHolder Instance of `MainActivityStateHolder` that holds the states of the current activity
 */
@Composable
fun Submit(modifier: Modifier = Modifier, stateHolder: StateHolder = StateHolder()) {
    Row(
        modifier = modifier.padding(4.dp)
    ) {
        Button(
            onClick = {
                stateHolder.isGameStarted = true
                stateHolder.generateSequence()
            },
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .weight(1f),
            enabled = !stateHolder.isGameStarted
        ) {
            Text(
                text = stringResource(R.string.start_game),
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1
            )
        }

        Button(
            onClick = { stateHolder.isGamePaused = !stateHolder.isGamePaused },
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .weight(1f),
            enabled = stateHolder.isGameStarted and stateHolder.isSequencePlayed
        ) {
            Text(
                text = stringResource(if (stateHolder.isGamePaused) R.string.resume else R.string.pause),
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1
            )
        }

        Button(
            onClick = {
                stateHolder.isGameOver = true
                stateHolder.isSequencePlayed = false
                stateHolder.addGame()
            },
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .weight(1f),
            enabled = stateHolder.isGameStarted and !stateHolder.isGameOver
        ) {
            Text(
                text = stringResource(R.string.end_game),
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1
            )
        }
    }
}