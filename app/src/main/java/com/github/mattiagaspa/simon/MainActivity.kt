package com.github.mattiagaspa.simon

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.github.mattiagaspa.simon.components.Keypad
import com.github.mattiagaspa.simon.ui.theme.SimonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimonTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val configuration = LocalConfiguration.current

                    when(configuration.orientation) {
                        Configuration.ORIENTATION_PORTRAIT -> {
                            MainActivityPortrait(
                                modifier = Modifier.padding(innerPadding)
                            )
                        }

                        Configuration.ORIENTATION_LANDSCAPE -> {
                            MainActivityLandscape(
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainActivityPortrait(modifier: Modifier = Modifier) {
    Keypad(modifier = modifier.fillMaxWidth())
}

@Composable
fun MainActivityLandscape(modifier: Modifier = Modifier) {
    Text("Landscape mode WIP", modifier = modifier.fillMaxWidth())
}