package com.github.mattiagaspa.simon.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.mattiagaspa.simon.logic.StateHolder

@Composable
fun GameDetails(modifier: Modifier = Modifier,
                stateHolder: StateHolder = StateHolder(),
                index: Int) {
    Text(
        text = stateHolder.allGames[index].toString()
    )
}