package com.github.mattiagaspa.simon.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.mattiagaspa.simon.R
import com.github.mattiagaspa.simon.logic.SimonViewModel

/** Composable function to display the current game.
 * @param modifier The modifier to be applied
 * @param viewModel The `SimonViewModel` to be used
 */
@Composable
fun SequenceVisualizer(modifier: Modifier = Modifier, viewModel: SimonViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Row(
        modifier = modifier
    ) {
        // Used a read-only TextField instead of a simple Text so that the user can select and copy its content.
        TextField(
            value = uiState.game.userSequence,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .align(Alignment.CenterVertically),
            enabled = uiState.isGameStarted and !uiState.isSequencePlayed,
            readOnly = true,
            label = { Text(stringResource(R.string.sequence)) }
        )
    }
}