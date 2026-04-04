package com.github.mattiagaspa.simon.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.github.mattiagaspa.simon.stateHolders.HistoryActivityStateHolder

@Composable
fun SequenceList(modifier: Modifier = Modifier, stateHolder: HistoryActivityStateHolder = HistoryActivityStateHolder()) {
    Column(
        modifier = modifier.padding(2.dp).verticalScroll(rememberScrollState())
    ) {
        for (row in stateHolder.getList()) {
            Row(
                modifier = Modifier.padding(2.dp)
            ) {
                Text(
                    text = row[0].toString(),
                    modifier = Modifier.weight(0.1f),
                    maxLines = 1
                )
                Text(
                    text = row[1].toString(),
                    modifier = Modifier.weight(0.9f),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
    }
}