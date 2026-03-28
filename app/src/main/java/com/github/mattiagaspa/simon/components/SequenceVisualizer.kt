package com.github.mattiagaspa.simon.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import com.github.mattiagaspa.simon.stateHolders.MainActivityStateHolder

@Composable
fun SequenceVisualizer(modifier: Modifier = Modifier, stateHolder: MainActivityStateHolder = MainActivityStateHolder()) {
    Row(
        modifier = modifier
    ) {
        TextField(
            value = stateHolder.sequence,
            onValueChange = {},
            readOnly = true,
        )
    }
}