package com.github.mattiagaspa.simon

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.github.mattiagaspa.simon.logic.SimonViewModel
import com.github.mattiagaspa.simon.ui.theme.SimonTheme

/** Main activity that manages the application navigation. */
class MainActivity : ComponentActivity() {
    /** ViewModel to update the application state */
    private lateinit var viewModel: SimonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimonTheme {
                viewModel = viewModel()
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "history",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("history") {
                            HistoryScreen(
                                viewModel = viewModel,
                                gameDetails = { index -> navController.navigate("details/${Uri.encode(index.toString())}") },
                                startGame = {
                                    viewModel.reset()
                                    navController.navigate("game")
                                }
                            )
                        }
                        composable("details/{index}") { backStackEntry ->
                            DetailsActivity(
                                viewModel = viewModel,
                                index = Uri.decode(backStackEntry.arguments?.getString("index")).toInt()
                            )
                        }
                        composable("game") {
                            GameScreen(
                                viewModel = viewModel
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.release()
    }
}