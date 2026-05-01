package com.github.mattiagaspa.simon.logic

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.github.mattiagaspa.simon.ui.theme.Blue
import com.github.mattiagaspa.simon.ui.theme.Cyan
import com.github.mattiagaspa.simon.ui.theme.Green
import com.github.mattiagaspa.simon.ui.theme.Magenta
import com.github.mattiagaspa.simon.ui.theme.Red
import com.github.mattiagaspa.simon.ui.theme.Yellow

/** Class to hold all variables and states needed for the app's functions
 * @param game Initial value for current game
 * @param allGames History of all previous games
 * @param isGameStarted Set to true if the game is started
 * @param isGamePaused Se to true if the game has been paused
 */
class StateHolder(
    game: Game = Game(),
    allGames: MutableList<Game> = mutableListOf<Game>(),
    isGameStarted: Boolean = false,
    isSequencePlayed: Boolean = false,
    isGamePaused: Boolean = false
) {
    /** Variable that holds the current game */
    var game: Game by mutableStateOf(game)
        private set
    /** Variable that holds the history of previous games */
    var allGames: MutableList<Game> = allGames
        private set

    /** State used to start the game */
    var isGameStarted: Boolean by mutableStateOf(isGameStarted)
        internal set
    /** State used block user input when the sequence is played */
    var isSequencePlayed: Boolean by mutableStateOf(isSequencePlayed)
        internal set
    /** State used to pause the game */
    var isGamePaused: Boolean by mutableStateOf(isGamePaused)
        internal set

    /** Add a color to the user's sequence
     * @param color The color to be added
     * @return True if the color is correct
     */
    fun addUserColor(color: Color): Boolean {
        var currentSequence = game.userSequence
        if (currentSequence.isNotEmpty()) {
            currentSequence += ", "
        }
        val colorString = when (color) {
            Red -> "R"
            Green -> "G"
            Blue -> "B"
            Cyan -> "C"
            Magenta -> "M"
            Yellow -> "Y"
            else -> ""
        }
        currentSequence += colorString
        game = game.copy(userSequence = currentSequence)
        Log.i(this::class.java.toString(), "Added color $colorString to user's sequence")
        Log.v(this::class.java.toString(), "Content of game.userSequence:\n${game.userSequence}")
        return game.isCorrect()
    }
}