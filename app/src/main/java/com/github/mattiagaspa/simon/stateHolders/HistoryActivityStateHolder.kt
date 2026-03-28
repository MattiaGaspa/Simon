package com.github.mattiagaspa.simon.stateHolders

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.*

class HistoryActivityStateHolder(allSequences: String = "") {
    var allSequences by mutableStateOf(allSequences)
        private set

    fun getList(): List<Array<Any>> {
        val result = mutableListOf<Array<Any>>()
        for (sequence in allSequences.split("\n")) {
            result.add(
                arrayOf(
                    sequence.replace(", ", "").length,
                    sequence
                )
            )
        }
        return result
    }

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