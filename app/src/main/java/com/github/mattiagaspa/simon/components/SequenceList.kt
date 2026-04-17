package com.github.mattiagaspa.simon.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.github.mattiagaspa.simon.stateHolders.HistoryActivityStateHolder

/** Composable function to display all games.
 * Uses `LazyColumn` to list, for each game, the number of colors pressed and the sequence (truncated if too long)
 * @param modifier The modifier to be applied to the `SequenceList`
 * @param stateHolder Instance of `HistoryActivityStateHolder` that holds the states of the current activity
 */
@Composable
fun SequenceList(modifier: Modifier = Modifier, stateHolder: HistoryActivityStateHolder = HistoryActivityStateHolder()) {
    val list = stateHolder.getList()
    LazyColumn(
        modifier = modifier.padding(2.dp)
    ) {
        items(list.size) { index ->
            Row(
                modifier = Modifier.padding(2.dp)
            ) {
                Text(
                    text = list[index][0].toString(),
                    modifier = Modifier.weight(0.1f),
                    maxLines = 1
                )
                Text(
                    text = list[index][1].toString(),
                    modifier = Modifier.weight(0.9f),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
    }
}