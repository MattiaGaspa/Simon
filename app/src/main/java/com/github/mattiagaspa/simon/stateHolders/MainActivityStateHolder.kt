package com.github.mattiagaspa.simon.stateHolders

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.github.mattiagaspa.simon.ui.theme.*

/** Class to hold all states of `MainActivity`.
 * Instead of passing multiple lambdas to the composable functions of the project, I decided to wrap the states in a class to be able to define helper methods.
 * Now, a single instance of the class is passed to the composable function and inside it the helper methods will be used.
 * @param sequence Initial value for `sequence` variable
 * @param allSequences Initial value for `allSequences` variable
 */
class MainActivityStateHolder(sequence: String = "", allSequences: String = "") {
    /** String that contains the sequence of colors pressed in the current game.
     * Colors are separated by `, `.
     */
    var sequence by mutableStateOf(sequence)
        private set
    /** String that contains the game history.
     * Games are separated by `\n` character.
     */
    var allSequences by mutableStateOf(allSequences)
        internal set

    /** Add a color to the current game
     * @param color Color to add in the sequence
     */
    fun addColor(color: Color) {
        if (sequence.isNotEmpty()) {
            sequence += ", "
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
        sequence += colorString
        Log.i(null, "Added color $colorString to current sequence")
        Log.v(null, "Content of sequence:\n$sequence")
    }

    /** Clear current game sequence */
    fun clearSequence() {
        sequence = ""
        Log.i(null, "Cleared sequence")
    }

    /** Add the current game sequence to the history */
    fun updateAllSequences() {
        allSequences += sequence + "\n"
        Log.i(null, "Added $sequence to sequence history")
        Log.v(null, "Content of allSequences:\n$allSequences")
    }

    /** Companion object used to define how `rememberSavable` should save this class. */
    companion object {
        /** `rememberSaveable` saves out of the box primitive types inside the bundle.
         * `MainActivityStateHolder` is not a primitive object, we need to specify how to save and retrieve the data from a list.
         * Documentation: [companion objects](https://kotlinlang.org/docs/object-declarations.html#companion-objects), [rememberSaveable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/saveable/rememberSaveable.composable#rememberSaveable(kotlin.Array,androidx.compose.runtime.saveable.Saver,kotlin.String,kotlin.Function0))
         */
        val Saver: Saver<MainActivityStateHolder, *> = listSaver(
            save = { listOf(it.sequence, it.allSequences) },
            restore = {
                MainActivityStateHolder(
                    sequence = it[0],
                    allSequences = it[1]
                )
            }
        )
    }
}