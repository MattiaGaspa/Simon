package com.github.mattiagaspa.simon

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.Alignment
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
                    val stateHolder = rememberSaveable(saver = MainActivityStateHolder.Saver) {
                        MainActivityStateHolder()
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
fun MainActivityPortrait(modifier: Modifier = Modifier, stateHolder: MainActivityStateHolder = MainActivityStateHolder()) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
    ) {
        Keypad(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            stateHolder = stateHolder
        )
        SequenceVisualizer(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            stateHolder = stateHolder
        )
        Submit(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            stateHolder = stateHolder
        )
    }
}

@Composable
fun MainActivityLandscape(modifier: Modifier = Modifier, stateHolder: MainActivityStateHolder = MainActivityStateHolder()) {
    Row(
        modifier = modifier
    ) {
        Keypad(
            modifier = Modifier.weight(1f).align(Alignment.CenterVertically),
            stateHolder = stateHolder
        )
        Column(
            modifier = Modifier.fillMaxHeight().verticalScroll(rememberScrollState()).align(Alignment.CenterVertically)
        ) {
            SequenceVisualizer(
                modifier = Modifier.weight(1f),
                stateHolder = stateHolder
            )
            Submit(
                modifier = Modifier.weight(1f),
                stateHolder = stateHolder
            )
        }
    }
}