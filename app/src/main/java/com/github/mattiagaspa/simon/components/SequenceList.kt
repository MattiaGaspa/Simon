package com.github.mattiagaspa.simon.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.mattiagaspa.simon.R
import com.github.mattiagaspa.simon.logic.SimonViewModel

/** Composable function to display all games.
 * Uses `LazyColumn` to list, for each game, the number of colors pressed and the sequence (truncated if too long)
 * @param modifier The modifier to be applied
 * @param viewModel The `SimonViewModel` to be used
 * @param gameDetails Callback function to be called when a game is selected
 * @param startGame Callback function to be called when the user wants to start a new game
 */
@Composable
fun SequenceList(modifier: Modifier = Modifier,
                 viewModel: SimonViewModel = SimonViewModel(),
                 gameDetails: (Int) -> Unit,
                 startGame: () -> Unit) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Box(modifier = modifier.fillMaxSize()) {
        val list = uiState.allGames
        LazyColumn(
            modifier = modifier.padding(2.dp)
        ) {
            items(list.size) { index ->
                Row(
                    modifier = Modifier.padding(2.dp)
                ) {
                    Text(
                        text = list[index].toString(),
                        modifier = Modifier.clickable { gameDetails(index) },
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            }
        }
        ExtendedFloatingActionButton(
            onClick = { startGame() },
            icon = { Icon(Icons.Filled.PlayArrow, contentDescription = null) },
            text = { Text(text = stringResource(R.string.new_game)) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        )
    }
}