package com.github.mattiagaspa.simon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.mattiagaspa.simon.logic.StateHolder
import com.github.mattiagaspa.simon.ui.theme.SimonTheme

class MainActivity : ComponentActivity() {
    val stateHolder = StateHolder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimonTheme {
                val stateHolder: StateHolder = remember { stateHolder } // MAKE SAVABLE
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "history",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("history") {
                            HistoryScreen(
                                stateHolder = stateHolder
                            )
                        }
                        composable("details") {
                            DetailsActivity(
                                stateHolder = stateHolder
                            )
                        }
                        composable("game") {
                            GameScreen(
                                stateHolder = stateHolder
                            )
                        }
                    }
                }
            }
        }
    }
}