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
import androidx.compose.material3.Text
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
                    val sequence by rememberSaveable { mutableStateOf(mutableListOf<String>()) }

                    when(configuration.orientation) {
                        Configuration.ORIENTATION_PORTRAIT -> {
                            MainActivityPortrait(
                                modifier = Modifier.padding(innerPadding),
                                sequence = sequence
                            )
                        }

                        Configuration.ORIENTATION_LANDSCAPE -> {
                            MainActivityLandscape(
                                modifier = Modifier.padding(innerPadding),
                                sequence = sequence
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainActivityPortrait(modifier: Modifier = Modifier, sequence: MutableList<String>) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Keypad(
            sequence = sequence
        )
        Submit(
            sequence = sequence
        )
    }
}

@Composable
fun MainActivityLandscape(modifier: Modifier = Modifier, sequence: MutableList<String>) {
    Text(sequence.joinToString(", "), modifier = modifier.fillMaxWidth())

}