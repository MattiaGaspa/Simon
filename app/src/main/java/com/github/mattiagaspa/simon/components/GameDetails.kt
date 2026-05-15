package com.github.mattiagaspa.simon.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.mattiagaspa.simon.R
import com.github.mattiagaspa.simon.logic.SimonViewModel
import java.text.DateFormat

/**
 * Fragment that shows the detail of the match
 *
 * @param modifier The modifier to be applied
 * @param viewModel The `SimonViewModel` to be used
 * @param index The index of the match to be shown
 * @param onBack Action to be performed to get back to home screen
 */
@Composable
fun GameDetails(
    modifier: Modifier = Modifier,
    viewModel: SimonViewModel,
    index: Int,
    onBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = modifier.padding(2.dp)) {
            Text(
                text =
                    stringResource(R.string.started) + ": " + longToDate(uiState.allGames[index].start),
            )

            Text(
                text = stringResource(R.string.ended) + ": " + longToDate(uiState.allGames[index].stop),
            )

            Text(
                text =
                    buildAnnotatedString {
                        val common =
                            uiState.allGames[index]
                                .userSequence
                                .commonPrefixWith(uiState.allGames[index].sequence)
                        withStyle(style = SpanStyle(color = Color.Green)) { append(common) }
                        withStyle(style = SpanStyle(color = Color.Red)) {
                            append(uiState.allGames[index].sequence.drop(common.length))
                        }
                    },
                modifier = Modifier.padding(2.dp),
            )
        }
        ExtendedFloatingActionButton(
            onClick = {
                viewModel.removeGame(uiState.allGames[index])
                onBack()
            },
            icon = { Icon(Icons.Filled.Delete, contentDescription = null) },
            text = { Text(text = stringResource(R.string.delete)) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
        )
    }
}

/**
 * Convert from long to date string
 *
 * @param time Time from 1/1/1970
 * @return Formatted date
 */
fun longToDate(time: Long): String {
    val date = java.util.Date(time)
    val formatter =
        DateFormat.getDateTimeInstance(
            DateFormat.MEDIUM,
            DateFormat.MEDIUM,
            java.util.Locale.getDefault(),
        )
    return formatter.format(date)
}
