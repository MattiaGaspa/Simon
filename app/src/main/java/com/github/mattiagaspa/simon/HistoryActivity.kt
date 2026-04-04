package com.github.mattiagaspa.simon

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.github.mattiagaspa.simon.components.SequenceList
import com.github.mattiagaspa.simon.stateHolders.HistoryActivityStateHolder
import com.github.mattiagaspa.simon.ui.theme.SimonTheme

class HistoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SimonTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val configuration = LocalConfiguration.current

                    // Retrieve all the sequences
                    val intent = getIntent()
                    val allSequences = intent.getStringExtra("allSequences") ?: ""
                    // Initialize the object to hold the activity states with the sequences just received.
                    val stateHolder = rememberSaveable(saver = HistoryActivityStateHolder.Saver) {
                        HistoryActivityStateHolder(allSequences)
                    }

                    // Configuration.ORIENTATION_SQUARE and Configuration.ORIENTATION_UNDEFINED aren't necessary for a phone application
                    when(configuration.orientation) {
                        Configuration.ORIENTATION_PORTRAIT -> {
                            HistoryActivityPortrait(
                                modifier = Modifier.padding(innerPadding),
                                stateHolder = stateHolder
                            )
                        }

                        Configuration.ORIENTATION_LANDSCAPE -> {
                            HistoryActivityLandscape(
                                modifier = Modifier.padding(innerPadding),
                                stateHolder = stateHolder
                            )
                        }
                    }
                }
            }
        }
    }
}

// Composable to define the layout for portrait screens.
@Composable
fun HistoryActivityPortrait(modifier: Modifier = Modifier, stateHolder: HistoryActivityStateHolder = HistoryActivityStateHolder()) {
    SequenceList(
        modifier,
        stateHolder
    )
}

// Composable to define the layout for landscape screens.
@Composable
fun HistoryActivityLandscape(modifier: Modifier = Modifier, stateHolder: HistoryActivityStateHolder = HistoryActivityStateHolder()) {
    SequenceList(
        modifier,
        stateHolder
    )
}