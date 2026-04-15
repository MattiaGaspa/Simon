package com.github.mattiagaspa.simon.stateHolders

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.*

// Class to hold all states of HistoryActivity. Contains:
// - allSequences: String: to hold all the sequences pressed from the app launch.
// Contains the helper methods:
// - getList: to get a list of arrays that contains two elements: the number of button pressed and the sequence
// This class is not necessary for the project. Since there is no way to modify the `allSequence` string in the HistoryActivity,
// a string would have been enough. I preferred to wrap the string in a state holder class anyway to maintain the similarity
// with MainActivity and to be able to define helper methods
class HistoryActivityStateHolder(allSequences: String = "") {
    var allSequences by mutableStateOf(allSequences)
        internal set

    fun getList(): List<Array<Any>> {
        val result = mutableListOf<Array<Any>>()
        allSequences.split("\n").forEach { sequence ->
            result.add(
                arrayOf(
                    sequence.replace(", ", "").length,
                    sequence
                )
            )
        }
        result.removeAt(result.size - 1)
        Log.v(null, "Content of displayed list:\n${
            result.joinToString(
                separator = "\n"
            ) {
                it.joinToString(
                    separator = ", "
                )
            }
        }")
        return result
    }

    // rememberSaveable saves out of the box primitive types inside the bundle.
    // In this case, we need to create a companion object to specify how to save the data in the bundle (in a list)
    // and how to retrieve the data (passing the list's elements to the constructor)
    // Documentation:
    // - companion objects: https://kotlinlang.org/docs/object-declarations.html#companion-objects
    // - rememberSaveable: https://developer.android.com/reference/kotlin/androidx/compose/runtime/saveable/rememberSaveable.composable#rememberSaveable(kotlin.Array,androidx.compose.runtime.saveable.Saver,kotlin.String,kotlin.Function0)
    companion object {
        val Saver: Saver<HistoryActivityStateHolder, *> = listSaver(
            save = { listOf(it.allSequences) },
            restore = {
                HistoryActivityStateHolder(
                    allSequences = it[0]
                )
            }
        )
    }
}