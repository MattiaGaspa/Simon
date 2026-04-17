package com.github.mattiagaspa.simon.stateHolders

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.*

/** Class to hold all states of `HistoryActivity`.
 * This class was not necessary for this project. Since there is no way to modify the `allSequence` string in `HistoryActivity`, a string would have been enough.
 * I preferred to wrap the string in a state holder class anyway to maintain the similarity with `MainActivity` and to be able to define helper methods
 * @param allSequences Initial value for `allSequences` variable
 */
class HistoryActivityStateHolder(allSequences: String = "") {
    /** String that contains the game history.
     * Games are separated by `\n` character.
     */
    var allSequences by mutableStateOf(allSequences)
        internal set

    /** Function to show how many colors were pressed in every game
     * @return A list of `Array<Any>` where the first element is the number of colors pressed and the second is the game
     */
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
        result.removeAt(result.size - 1) // Remove trailing \n
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

    /** Companion object used to define how `rememberSavable` should save this class. */
    companion object {
        /** `rememberSaveable` saves out of the box primitive types inside the bundle.
         * `HistoryActivityStateHolder` is not a primitive object, we need to specify how to save and retrieve the data from a list.
         * Documentation: [companion objects](https://kotlinlang.org/docs/object-declarations.html#companion-objects), [rememberSaveable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/saveable/rememberSaveable.composable#rememberSaveable(kotlin.Array,androidx.compose.runtime.saveable.Saver,kotlin.String,kotlin.Function0))
         */
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