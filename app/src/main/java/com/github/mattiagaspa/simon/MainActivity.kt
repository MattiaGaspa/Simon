package com.github.mattiagaspa.simon

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.github.mattiagaspa.simon.components.*
import com.github.mattiagaspa.simon.stateHolders.MainActivityStateHolder
import com.github.mattiagaspa.simon.ui.theme.SimonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimonTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val configuration = LocalConfiguration.current
                    // Initialize the object that holds all the states for the current activity
                    val stateHolder = rememberSaveable(saver = MainActivityStateHolder.Saver) {
                        MainActivityStateHolder()
                    }
                    // Configuration.ORIENTATION_SQUARE and Configuration.ORIENTATION_UNDEFINED aren't necessary for a phone application
                    when(configuration.orientation) {
                        Configuration.ORIENTATION_PORTRAIT -> {
                            MainActivityPortrait(
                                modifier = Modifier.padding(innerPadding),
                                stateHolder = stateHolder
                            )
                        }

                        Configuration.ORIENTATION_LANDSCAPE -> {
                            MainActivityLandscape(
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
fun MainActivityPortrait(modifier: Modifier = Modifier, stateHolder: MainActivityStateHolder = MainActivityStateHolder()) {
    Column(
        modifier = modifier
    ) {
        Keypad(
            modifier = Modifier.align(Alignment.CenterHorizontally).weight(0.9f),
            stateHolder = stateHolder
        )
        SequenceVisualizer(
            modifier = Modifier.align(Alignment.CenterHorizontally).weight(0.2f),
            stateHolder = stateHolder
        )
        Submit(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            stateHolder = stateHolder
        )
    }
}

// Composable to define the layout for landscape screens.
@Composable
fun MainActivityLandscape(modifier: Modifier = Modifier, stateHolder: MainActivityStateHolder = MainActivityStateHolder()) {
    // Split screen in half: one half for keypad and the other half for the sequence and the buttons
    Row(
        modifier = modifier
    ) {
        Keypad(
            modifier = Modifier.weight(1f),
            stateHolder = stateHolder
        )
        Column(
            modifier = Modifier.fillMaxHeight().weight(1f)
        ) {
            SequenceVisualizer(
                modifier = Modifier.weight(1f).align(Alignment.CenterHorizontally),
                stateHolder = stateHolder
            )
            Submit(
                stateHolder = stateHolder
            )
        }
    }
}