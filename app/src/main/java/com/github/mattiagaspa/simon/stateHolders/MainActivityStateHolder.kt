package com.github.mattiagaspa.simon.stateHolders

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.github.mattiagaspa.simon.ui.theme.*

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