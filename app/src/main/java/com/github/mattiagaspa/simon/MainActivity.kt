package com.github.mattiagaspa.simon

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import androidx.room.Room
import com.github.mattiagaspa.simon.logic.GameDatabase
import com.github.mattiagaspa.simon.logic.SimonViewModel
import com.github.mattiagaspa.simon.ui.theme.SimonTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/** Main activity that manages the application navigation. */
class MainActivity : ComponentActivity() {
    /** ViewModel to update the application state */
    private lateinit var viewModel: SimonViewModel
    /** Database for storing games */
    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            GameDatabase::class.java, "game"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimonTheme {
                viewModel = viewModel()
                LaunchedEffect(Unit) {
                    val gameDao = database.gameDao()
                    val history = withContext(Dispatchers.IO) { gameDao.getAll() }
                    viewModel.populateHistory(history)
                }
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
                                viewModel = viewModel,
                                onBack = {
                                    navController.popBackStack()
                                }
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
        val gameDao = database.gameDao()
        lifecycleScope.launch(Dispatchers.IO) {
            Log.i(this::class.java.simpleName, "Saving history to database")
            viewModel.uiState.value.allGames.forEach { game ->
                gameDao.insertAll(game)
            }
        }
    }
}