package com.github.mattiagaspa.simon

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.github.mattiagaspa.simon.components.Keypad
import com.github.mattiagaspa.simon.components.Submit
import com.github.mattiagaspa.simon.ui.theme.SimonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimonTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val configuration = LocalConfiguration.current
                    var sequence by rememberSaveable { mutableStateOf("") }
                    var allSequences by rememberSaveable { mutableStateOf("") }

                    when(configuration.orientation) {
                        Configuration.ORIENTATION_PORTRAIT -> {
                            Column(
                                modifier = Modifier.padding(innerPadding),
                            ) {
                                Keypad(
                                    onSequenceChange = { newColor ->
                                        val adding = newColor[0].toString()
                                        sequence = if (sequence.isEmpty()) adding else "$sequence, $adding"
                                    },
                                )
                                Submit(
                                    sequence = sequence,
                                    allSequences = allSequences,
                                    onSequenceUpdate = {
                                        allSequences = "$allSequences\n$sequence"
                                    },
                                    onSequenceDelete = {
                                        sequence = ""
                                    }
                                )
                            }
                        }

                        Configuration.ORIENTATION_LANDSCAPE -> {
                            Column(
                                modifier = Modifier.padding(innerPadding),
                            ) {
                                Keypad(
                                    onSequenceChange = { newColor ->
                                        val adding = newColor[0].toString()
                                        sequence = if (sequence.isEmpty()) adding else "$sequence, $adding"
                                    },
                                )
                                Submit(
                                    sequence = sequence,
                                    allSequences = allSequences,
                                    onSequenceUpdate = {
                                        allSequences = "$allSequences\n$sequence"
                                    },
                                    onSequenceDelete = {
                                        sequence = ""
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}