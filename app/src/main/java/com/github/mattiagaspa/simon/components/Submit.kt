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
import com.github.mattiagaspa.simon.stateHolders.MainActivityStateHolder

// Composable function to define the two buttons: the one to cancel the sequence and the one to end the game
// Takes a MainActivityStateHolder object to:
// - cancel the current sequence, with the .clearSequence() method, when `cancel` is pressed
// - updates the string that holds all the sequences from the app launh, with the .updateAllSequences() method, and cancels the current sequence when `end game` is pressed
@Composable
fun Submit(modifier: Modifier = Modifier, stateHolder: MainActivityStateHolder = MainActivityStateHolder()) {
    Row(
        modifier = modifier.padding(4.dp)
    ) {
        Button(
            onClick = { stateHolder.clearSequence() },
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .weight(1f)
        ) {
            Text(stringResource(R.string.cancel))
        }

        // The Intent() first argument is the context in which the intent is created.
        // Since this composable function is defined outside MainActivity, we retrieve the context with LocalContext.current
        val context = LocalContext.current
        Button(
            onClick = {
                stateHolder.updateAllSequences()
                stateHolder.clearSequence()
                val intent = Intent(context, HistoryActivity::class.java)
                // Pass all the sequences in the intent
                intent.putExtra("allSequences", stateHolder.allSequences)
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