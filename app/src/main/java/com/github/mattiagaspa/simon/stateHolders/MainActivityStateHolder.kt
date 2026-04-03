package com.github.mattiagaspa.simon.stateHolders

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
// Two rememberSaveable mutableStateOf string were enough, but I didn't like to pass many () -> Unit function in preparation for the second delivery.
// Now the control of the states is centralized for every component in the MainActivity activity.
class MainActivityStateHolder(sequence: String = "", allSequences: String = "") {
    var sequence by mutableStateOf(sequence)
        private set
    var allSequences by mutableStateOf(allSequences)
        private set

    fun addColor(color: Color) {
        if (sequence.isNotEmpty()) {
            sequence += ", "
        }
        sequence += when (color) {
            Red -> "R"
            Green -> "G"
            Blue -> "B"
            Cyan -> "C"
            Magenta -> "M"
            Yellow -> "Y"
            else -> ""
        }
    }

    fun clearSequence() {
        sequence = ""
    }

    fun updateAllSequences() {
        if (allSequences.isNotEmpty()) {
            allSequences += "\n"
        }
        allSequences += sequence
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