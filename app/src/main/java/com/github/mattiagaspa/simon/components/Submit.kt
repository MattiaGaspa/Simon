package com.github.mattiagaspa.simon.components

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.mattiagaspa.simon.HistoryActivity
import com.github.mattiagaspa.simon.R

@Composable
fun Submit(modifier: Modifier = Modifier, sequence: MutableList<String>) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = sequence.joinToString(", "), // Fix by turning it to a state
            onValueChange = {},
            modifier = Modifier.align(Alignment.CenterHorizontally),
            enabled = true,
            readOnly = true,
        )

        Row(
            modifier = Modifier.padding(4.dp)
        ) {
            Button(
                onClick = { sequence.clear() },
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .weight(1f)
            ) {
                Text(stringResource(R.string.cancel))
            }

            val context = LocalContext.current
            Button(
                onClick = {
                    val intent = Intent(context, HistoryActivity::class.java)
                    context.startActivity(intent)
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