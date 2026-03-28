package com.github.mattiagaspa.simon

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.github.mattiagaspa.simon.ui.theme.SimonTheme

class HistoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SimonTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val configuration = LocalConfiguration.current

                    val intent = getIntent()
                    val allSequences = intent.getStringExtra("allSequences") ?: ""

                    when(configuration.orientation) {
                        Configuration.ORIENTATION_PORTRAIT -> {
                            HistoryActivityPortrait(
                                modifier = Modifier.padding(innerPadding),
                                sequence = allSequences,
                            )
                        }

                        Configuration.ORIENTATION_LANDSCAPE -> {
                            HistoryActivityLandscape(
                                modifier = Modifier.padding(innerPadding),
                                sequence = allSequences,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryActivityPortrait(modifier: Modifier = Modifier, sequence: String) {
    Text(
        text = sequence,
        modifier = modifier
    )
}

@Composable
fun HistoryActivityLandscape(modifier: Modifier = Modifier, sequence: String) {
    Text(
        text = sequence,
        modifier = modifier
    )
}