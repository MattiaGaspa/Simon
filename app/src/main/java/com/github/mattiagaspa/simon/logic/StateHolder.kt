package com.github.mattiagaspa.simon.logic

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*

/** Class to hold all variables and states needed for the app's functions
 * @param game Initial value for current game
 * @param isGameStarted Set to true if the game is started
 * @param isPaused Se to true if the game has been paused
 */
class StateHolder(
    game: Game = Game(),
    isGameStarted: Boolean = false,
    isSequencePlayed: Boolean = false,
    isPaused: Boolean = false
) {
    /** Variable that holds the current game */
    var game: Game by mutableStateOf(game)
        private set

    /** State used to start the game */
    var isGameStarted: Boolean by mutableStateOf(isGameStarted)
        private set
    /** State used block user input when the sequence is played */
    var isSequencePlayed: Boolean by mutableStateOf(isSequencePlayed)
        private set
    /** State used to pause the game */
    var isPaused: Boolean by mutableStateOf(isPaused)
        private set
}