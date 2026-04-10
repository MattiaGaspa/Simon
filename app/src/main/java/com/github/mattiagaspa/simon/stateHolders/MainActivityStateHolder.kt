package com.github.mattiagaspa.simon.stateHolders

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.github.mattiagaspa.simon.ui.theme.*

// Class to hold all states of MainActivity. Contains:
// - sequence: String: to hold the current sequence of pressed buttons.
// - allSequences: String: to hold all the sequences pressed from the app launch.
// Contains the helper methods:
// - addColor: to add a color to the current sequence (when a button is pressed)
// - clearSequence: to clear the sequence (when the `cancel` or `end game` button is pressed)
// - updateAllSequences: to add the current sequence at the end of all sequences (used when the `end game` button is pressed)
// Two rememberSaveable mutableStateOf string would have been enough, but I preferred not to pass many () -> Unit functions.
// State control is now centralized for every component in the MainActivity activity.
class MainActivityStateHolder(sequence: String = "", allSequences: String = "") {
    var sequence by mutableStateOf(sequence)
        private set
    var allSequences by mutableStateOf(allSequences)
        private set

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

    fun clearSequence() {
        sequence = ""
        Log.i(null, "Cleared sequence")
    }

    fun updateAllSequences() {
        allSequences += sequence + "\n"
        Log.i(null, "Added $sequence to sequence history")
        Log.v(null, "Content of allSequences:\n$allSequences")
    }

    // rememberSaveable saves out of the box primitive types inside the bundle.
    // In this case, we need to create a companion object to specify how to save the data in the bundle (in a list)
    // and how to retrieve the data (passing the list's elements to the constructor)
    // Documentation:
    // - companion objects: https://kotlinlang.org/docs/object-declarations.html#companion-objects
    // - rememberSaveable: https://developer.android.com/reference/kotlin/androidx/compose/runtime/saveable/rememberSaveable.composable#rememberSaveable(kotlin.Array,androidx.compose.runtime.saveable.Saver,kotlin.String,kotlin.Function0)
    companion object {
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