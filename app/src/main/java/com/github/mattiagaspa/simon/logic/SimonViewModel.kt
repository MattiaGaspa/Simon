package com.github.mattiagaspa.simon.logic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import android.media.SoundPool
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.github.mattiagaspa.simon.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import com.github.mattiagaspa.simon.R

/** ViewModel to update the application state
 * Documentation: [handling configuration changes](https://developer.android.com/guide/topics/resources/runtime-changes),
 *                [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
 */
class SimonViewModel(application: Application) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(SimonUIState())
    val uiState: StateFlow<SimonUIState> = _uiState.asStateFlow()
    /** Getter for current UI state */
    private val currentState get() = _uiState.value

    /** Job to manage the game loop coroutine */
    private var gameJob: kotlinx.coroutines.Job? = null
    /** SoundPool to manage the sound effects */
    private var soundPool: SoundPool = SoundPool.Builder()
        .setMaxStreams(1)
        .build()
    /** Persistent save method
     * @param Game The game to be saved
     */
    internal lateinit var persistentSave: (Game) -> Unit
    /** Remove game from persistent memory
     * @param Game The game to be removed
     */
    internal lateinit var gameDelete: (Game) -> Unit

    /** All available sounds mapped to their soundId and durations */
    private val soundIds: Map<String, Int> = mutableMapOf<String, Int>().apply {
        val assetsManager = getApplication<Application>().assets
        listOf("R", "G", "B", "C", "M", "Y", "Game Over").forEach { elem ->
            val afd = assetsManager.openFd("sounds/$elem.mp3")
            put(elem, soundPool.load(afd, 1))
        }
    }

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

    /** Release necessary objects */
    fun release() {
        soundPool.release()
    }

    /** Populate game history with games from database */
    fun populateHistory(allGames: List<Game>) {
        _uiState.update { currentState ->
            currentState.copy(allGames = allGames as MutableList<Game>)
        }
    }

    /** Remove game from history */
    fun removeGame(game: Game) {
        gameDelete(game)
        _uiState.update { currentState ->
            val newList = currentState.allGames.toMutableList()
            newList.remove(game)
            currentState.copy(allGames = newList)
        }
    }

    /** Start the game */
    fun startGame() {
        _uiState.update { currentState ->
            currentState.copy(
                game = currentState.game.copy(start = System.currentTimeMillis()),
                isGameStarted = true
            )
        }
    }

    /** Stop the game */
    fun stopGame() {
        // Stop sequence generation
        gameJob?.cancel()

        // Register stop time
        _uiState.update { currentState ->
            currentState.copy(
                game = currentState.game.copy(stop = System.currentTimeMillis())
            )
        }
        // Add game to history if user has inputted at least one color, play sound and show toast
        if (!(currentState.game.sequence.length == 1 && currentState.game.userSequence.isEmpty())) {
            Log.i(this::class.java.simpleName, "Adding current game to allGames")
            _uiState.update { currentState ->
                currentState.copy(
                    allGames = currentState.allGames.toMutableList().apply { add(currentState.game) }
                )
            }
            // Save game history in database
            persistentSave(currentState.game)

            soundPool.autoPause()
            Toast.makeText(
                getApplication(),
                R.string.you_lost,
                Toast.LENGTH_SHORT
            ).show()
            soundIds["Game Over"]?.let { soundPool.play(it, 1f, 1f, 0, 0, 1f) }
        } else {
            Log.i(this::class.java.simpleName, "Not adding current game to allGames")
        }

        // Set up flags
        _uiState.update { currentState ->
            currentState.copy(
                isSequencePlayed = false,
                isGameOver = true,
                buttonColorAnimation = Color.Transparent
            )
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
    fun addUserColor(color: Color): Boolean {
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
        return currentState.game.isCorrectGuess()
    }

    /** Coroutine that generates the sequence and plays it */
    fun generateSequence() {
        gameJob?.cancel()
        gameJob = viewModelScope.launch {
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

                // Empty user sequence
                _uiState.update { currentState ->
                    Log.i(this::class.java.simpleName, "Emptying user's sequence")
                    currentState.copy(
                        game = currentState.game.copy(userSequence = "")
                    )
                }
                // Play animation and sound
                _uiState.update { currentState -> currentState.copy(isSequencePlayed = true) }
                currentState.game.sequence.split(", ").filter { it.isNotEmpty() }.forEach { colorString ->
                    val soundId = playSound(stringToColor(colorString))
                    flashButton(stringToColor(colorString))
                    while (currentState.isGamePaused) {
                        soundPool.pause(soundId)
                        delay(50)
                    }
                    soundPool.resume(soundId)
                }
                _uiState.update { currentState -> currentState.copy(isSequencePlayed = false) }

                // Wait for the user to input the guess
                while (currentState.game.isCorrectGuess(false)
                    && !currentState.game.isCorrect(false)) {
                    delay(10)
                }

                // Check if the user input is correct
                if (currentState.game.isCorrect()) {
                    Log.i(this::class.java.toString(), "User input is correct")
                    currentState.game.maxCorrectLength++
                } else {
                    Log.i(this::class.java.toString(), "Game over")
                    stopGame()
                }
            }
        }
    }

    /** Flash the button with the given color.
     * @param color The color to be flashed
     */
    suspend fun flashButton(color: Color) {
        _uiState.update { it.copy(buttonColorAnimation = color) }
        delay(1000)
        _uiState.update { it.copy(buttonColorAnimation = Color.Transparent) }
        delay(150)
    }

    /** Play the sound with the given color.
     * Documentation: [`SoundPool`](https://developer.android.com/reference/android/media/SoundPool), [heads up](https://stackoverflow.com/questions/69344299/soundpool-builder-explained-to-a-newbie)
     * @param color The color to be played
     */
    fun playSound(color: Color): Int {
        val soundId = soundIds[colorToString(color)] ?: return -1
        return soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
    }
}

/** Convert Color to String
 * @param color The color to be converted
 * @return The string representation of the color
 */
private fun colorToString(color: Color): String = when(color) {
    Color.Red -> "R"
    Color.Green -> "G"
    Color.Blue -> "B"
    Color.Cyan -> "C"
    Color.Magenta -> "M"
    Color.Yellow -> "Y"
    else -> ""
}
/** Convert String to Color
 * @param color The string to be converted
 * @return The color representation of the string
 */
private fun stringToColor(color: String): Color = when(color) {
    "R" -> Color.Red
    "G" -> Color.Green
    "B" -> Color.Blue
    "C" -> Color.Cyan
    "M" -> Color.Magenta
    "Y" -> Color.Yellow
    else -> Color.Transparent
}