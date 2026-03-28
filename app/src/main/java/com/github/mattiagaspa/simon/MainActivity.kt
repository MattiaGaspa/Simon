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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.github.mattiagaspa.simon.components.*
import com.github.mattiagaspa.simon.ui.theme.SimonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimonTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val configuration = LocalConfiguration.current
                    val stateHolder = rememberSaveable(saver = StateHolder.Saver) {
                        StateHolder()
                    }
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

@Composable
fun MainActivityPortrait(modifier: Modifier = Modifier, stateHolder: StateHolder = StateHolder()) {
    Column(
        modifier = modifier,
    ) {
        Keypad(
            stateHolder = stateHolder
        )
        Submit(
            stateHolder = stateHolder
        )
    }
}

@Composable
fun MainActivityLandscape(modifier: Modifier = Modifier, stateHolder: StateHolder = StateHolder()) {
    Column(
        modifier = modifier,
    ) {
        Keypad(
            stateHolder = stateHolder
        )
        Submit(
            stateHolder = stateHolder
        )
    }
}