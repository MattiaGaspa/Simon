package com.github.mattiagaspa.simon

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.*
import androidx.compose.ui.graphics.Color
import com.github.mattiagaspa.simon.ui.theme.*

class StateHolder(sequence: String = "", allSequences: String = "") {
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
        if (sequence.isNotEmpty()) {
            allSequences += "\n$sequence"
        }
    }

    companion object {
        val Saver: Saver<StateHolder, *> = listSaver(
            save = { listOf(it.sequence, it.allSequences) },
            restore = {
                StateHolder(
                    sequence = it[0],
                    allSequences = it[1]
                )
            }
        )
    }
}