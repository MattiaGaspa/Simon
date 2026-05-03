package com.github.mattiagaspa.simon.components

import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.github.mattiagaspa.simon.logic.SimonViewModel

/** Fragment that shows the detail of the match
 * @param modifier The modifier to be applied
 * @param viewModel The `SimonViewModel` to be used
 * @param index The index of the match to be shown
 */
@Composable
fun GameDetails(modifier: Modifier = Modifier,
                viewModel: SimonViewModel,
                index: Int) {
    val uiState by viewModel.uiState.collectAsState()
    Text(
        text = uiState.allGames[index].toString(),
        modifier = modifier
    )
}