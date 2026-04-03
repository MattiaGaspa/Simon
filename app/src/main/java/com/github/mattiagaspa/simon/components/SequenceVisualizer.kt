package com.github.mattiagaspa.simon.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.mattiagaspa.simon.R
import com.github.mattiagaspa.simon.stateHolders.MainActivityStateHolder

// Composable function to visualize the current sequence
// Takes a MainActivityStateHolder object to retrieve the current sequence
@Composable
fun SequenceVisualizer(modifier: Modifier = Modifier, stateHolder: MainActivityStateHolder = MainActivityStateHolder()) {
    Row(
        modifier = modifier
    ) {
        // Used a read-only TextField instead of a simple Text so that the user can select and copy its content.
        TextField(
            value = stateHolder.sequence,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .align(Alignment.CenterVertically),
            readOnly = true,
            label = { Text(stringResource(R.string.sequence)) }
        )
    }
}