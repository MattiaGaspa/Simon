package com.github.mattiagaspa.simon.components

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.mattiagaspa.simon.HistoryActivity
import com.github.mattiagaspa.simon.R
import com.github.mattiagaspa.simon.StateHolder

@Composable
fun Submit(modifier: Modifier = Modifier, stateHolder: StateHolder = StateHolder()) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = stateHolder.sequence,
            onValueChange = {},
            modifier = Modifier.align(Alignment.CenterHorizontally),
            readOnly = true,
        )

        Row(
            modifier = Modifier.padding(4.dp)
        ) {
            Button(
                onClick = { stateHolder.clearSequence() },
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .weight(1f)
            ) {
                Text(stringResource(R.string.cancel))
            }

            val context = LocalContext.current
            Button(
                onClick = {
                    if (stateHolder.sequence.isNotEmpty()) {
                        val updatedAllSequences = if (stateHolder.allSequences.isEmpty()) {
                            stateHolder.sequence
                        } else {
                            "${stateHolder.allSequences}\n${stateHolder.sequence}"
                        }
                        stateHolder.updateAllSequences()
                        val intent = Intent(context, HistoryActivity::class.java)
                        intent.putExtra("allSequences", updatedAllSequences)
                        context.startActivity(intent)
                        stateHolder.clearSequence()
                    }
                },
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .weight(1f)
            ) {
                Text(stringResource(R.string.end))
            }
        }
    }
}