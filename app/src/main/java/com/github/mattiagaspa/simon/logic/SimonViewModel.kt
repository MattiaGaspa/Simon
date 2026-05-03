package com.github.mattiagaspa.simon.logic

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mattiagaspa.simon.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/** ViewModel to update the application state
 * Documentation: [handling configuration changes](https://developer.android.com/guide/topics/resources/runtime-changes),
 *                [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
 */
class SimonViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SimonUIState())
    val uiState: StateFlow<SimonUIState> = _uiState.asStateFlow()
    /** Getter for current UI state */
    private val currentState get() = _uiState.value

    /** Reset the game state */
    fun reset() {
        _uiState.update { currentState ->
            currentState.copy(
                game = Game(),
                isGameStarted = false,
                isSequencePlayed = false,
                isGamePaused = false,
                isGameOver = false,
                buttonColorAnimation = Color.Transparent,
            )
        }
    }

    /** Start the game */
    fun startGame() {
        _uiState.update { currentState ->
            currentState.copy(isGameStarted = true)
        }
    }

    /** Stop the game */
    fun stopGame() {
        _uiState.update { currentState ->
            currentState.copy(isSequencePlayed = false, isGameOver = true)
        }
    }

    /** Toggle the pause/resume state */
    fun togglePauseResume() {
        _uiState.update { currentState ->
            currentState.copy(isGamePaused = !currentState.isGamePaused)
        }

    }

    /** Add a color to the user's sequence
     * @param color The color to be added
     */
    fun addUserColor(color: Color) {
        _uiState.update { currentState ->
            var currentSequence = currentState.game.userSequence
            if (currentSequence.isNotEmpty()) {
                currentSequence += ", "
            }
            val colorString = colorToString(color)
            currentSequence += colorString
            Log.i(this::class.java.simpleName, "Added color $colorString to user's sequence")
            Log.v(this::class.java.simpleName, "Content of game.userSequence:\n${currentSequence}")
            currentState.copy(
                game = currentState.game.copy(userSequence = currentSequence)
            )
        }
    }

    /** Coroutine that generates the sequence and plays it */
    fun generateSequence() {
        viewModelScope.launch {
            while (!currentState.isGameOver) {
                delay(800)
                // Add color to sequence
                _uiState.update { currentState ->
                    var currentSequence = currentState.game.sequence
                    if (currentSequence.isNotEmpty()) {
                        currentSequence += ", "
                    }
                    val colorString = listOf("R", "G", "B", "C", "M", "Y").random()
                    currentSequence += colorString
                    Log.i(this::class.java.simpleName, "Added color $colorString to sequence")
                    Log.v(this::class.java.simpleName, "Content of game.sequence:\n${currentSequence}")
                    currentState.copy(
                        game = currentState.game.copy(sequence = currentSequence)
                    )
                }
                // Play animation and sound
                _uiState.update { currentState -> currentState.copy(isSequencePlayed = true) }
                currentState.game.sequence.split(", ").filter { it.isNotEmpty() }.forEach { colorString ->
                    flashButton(stringToColor(colorString))
//                    playSound()
                    while (currentState.isGamePaused) {
                        delay(50)
                    }
                }
                _uiState.update { currentState -> currentState.copy(isSequencePlayed = false) }

                // Empty user sequence
                _uiState.update { currentState ->
                    Log.i(this::class.java.simpleName, "Emptying user's sequence")
                    currentState.copy(
                        game = currentState.game.copy(userSequence = "")
                    )
                }

                // Wait for the user to input the guess
                while (currentState.game.isCorrectGuess(false)
                    && !currentState.game.isCorrect(false)) {
                    delay(10)
                }

                // Check if the user input is correct
                _uiState.update { currentState ->
                    if (currentState.game.isCorrect()) {
                        Log.i(this::class.java.toString(), "User input is correct")
                        currentState
                    } else {
                        Log.i(this::class.java.toString(), "Game over")
                        currentState.copy(isGameOver = true)
                    }
                }
            }
            addGameToHistory()

        }
    }

    /** Add current game to history if the user has inputted at least one color */
    fun addGameToHistory() {
        _uiState.update { currentState ->
            if (!(currentState.game.sequence.length == 1 && currentState.game.userSequence.isEmpty())) {
                Log.i(this::class.java.simpleName, "Adding current game to allGames")
                currentState.copy(allGames = currentState.allGames.toMutableList().apply { add(currentState.game) })
            } else {
                Log.i(this::class.java.simpleName, "Not adding current game to allGames")
                currentState
            }
        }
    }

    /** Flash the button with the given color.
     * @param color The color to be flashed
     */
    suspend fun flashButton(color: Color) {
        _uiState.update { it.copy(buttonColorAnimation = color) }
        delay(500)
        _uiState.update { it.copy(buttonColorAnimation = Color.Transparent) }
        delay(150)
    }

    /** Play the sound with the given color.
     * @param color The color to be played
     */
    fun playSound(color: Color) {

    }
}

/** Convert Color to String
 * @param color The color to be converted
 * @return The string representation of the color
 */
private fun colorToString(color: Color): String = when(color) {
    Red -> "R"
    Green -> "G"
    Blue -> "B"
    Cyan -> "C"
    Magenta -> "M"
    Yellow -> "Y"
    else -> ""
}
/** Convert String to Color
 * @param color The string to be converted
 * @return The color representation of the string
 */
private fun stringToColor(color: String): Color = when(color) {
    "R" -> Red
    "G" -> Green
    "B" -> Blue
    "C" -> Cyan
    "M" -> Magenta
    "Y" -> Yellow
    else -> Color.Transparent
}