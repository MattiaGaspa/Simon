package com.github.mattiagaspa.simon.logic

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.ui.graphics.Color
import com.github.mattiagaspa.simon.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    isGamePaused: Boolean = false,
    isGameOver: Boolean = false
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
    /** State used to manage game over */
    var isGameOver: Boolean by mutableStateOf(isGameOver)
        internal set

    /** Reset the class to its initial state */
    fun reset() {
        isGameStarted = false
        isSequencePlayed = false
        isGamePaused = false
        isGameOver = false
        game = Game()
    }

    fun generateSequenceCoroutine() {
        val coroutineScope = CoroutineScope(Dispatchers.Main)
        coroutineScope.launch {
            while (!isGameOver) {
                // Add color to sequence
                var currentSequence = game.sequence
                if (currentSequence.isNotEmpty()) {
                    currentSequence += ", "
                }
                val colorString = listOf("R", "G", "B", "C", "M", "Y").random()
                currentSequence += colorString
                game = game.copy(sequence = currentSequence)
                Log.i(this::class.java.toString(), "Added color $colorString to sequence")
                Log.v(this::class.java.toString(), "Content of game.sequence:\n${game.sequence}")

                // Play animation and sound
                isSequencePlayed = true
                game.sequence.split(", ").forEach { color ->
                    if (isSequencePlayed) {
                        Log.i(this::class.java.toString(), "Playing animation for color $color")
                        delay(1000)
                    }
                }
                isSequencePlayed = false
                game = game.copy(userSequence = "")
                Log.i(this::class.java.toString(), "Emptied game.userSequence")

                // Wait for the user to input the guess
                while (game.isCorrectGuess(false) && !game.isCorrect(false)) {
                    delay(10)
                }
                // Check if the user input is correct
                if (game.isCorrect()) {
                    Log.i(this::class.java.toString(), "User input is correct")
                } else {
                    Log.i(this::class.java.toString(), "Game over")
                    isGameOver = true
                }
            }
            addGame()
        }
    }

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

    /** Add current game to history if sequence length is >1 and the user has inputted at least one color */
    fun addGame() {
        if (!(game.sequence.length == 1 && game.userSequence.isEmpty())) {
            Log.i(this::class.java.toString(), "Adding game to allGames")
            allGames.add(game)
        }
    }

    companion object {
        val Saver: Saver<StateHolder, *> = listSaver(
            save = {
                listOf(
                    it.game.sequence,
                    it.game.userSequence,
                    it.allGames.map { g -> g.sequence },
                    it.isGameStarted,
                    it.isSequencePlayed,
                    it.isGamePaused,
                    it.isGameOver
                )
            },
            restore = {
                val allGamesStrings = it[2] as List<String>
                val restoredAllGames = allGamesStrings.map { seq -> Game(sequence = seq) }.toMutableList()
                StateHolder(
                    game = Game(it[0] as String, it[1] as String),
                    allGames = restoredAllGames,
                    isGameStarted = it[3] as Boolean,
                    isSequencePlayed = it[4] as Boolean,
                    isGamePaused = it[5] as Boolean,
                    isGameOver = it[6] as Boolean
                )
            }
        )
    }
}