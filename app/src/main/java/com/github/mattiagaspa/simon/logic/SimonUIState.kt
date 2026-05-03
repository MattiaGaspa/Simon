package com.github.mattiagaspa.simon.logic

import androidx.compose.ui.graphics.Color

/** Class to hold the states of the application
 * @param game Current game
 * @param allGames List of all games played
 * @param isGameStarted True if the game has been started
 * @param isSequencePlayed True if the sequence is being played
 * @param isGamePaused True if the game is paused
 * @param isGameOver True if the game is over
 * @param buttonColorAnimation Color of the button that is being animated
 */
data class SimonUIState(
    internal var game: Game = Game(),
    internal var allGames: MutableList<Game> = mutableListOf(),
    internal var isGameStarted: Boolean = false,
    internal var isSequencePlayed: Boolean = false,
    internal var isGamePaused: Boolean = false,
    internal var isGameOver: Boolean = false,
    internal var buttonColorAnimation: Color = Color.Transparent
)