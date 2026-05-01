package com.github.mattiagaspa.simon.logic

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.github.mattiagaspa.simon.ui.theme.*

/** Class used to represent a game
 * @param sequence The sequence that the user must replicate
 * @param userSequence The sequence inputted by the user
 */
data class Game(internal var sequence: String = "", internal var userSequence: String = "") {
    /** Number of colors pressed */
    val length: Int
        get() = sequence.replace(", ", "").length

    /** Number of correct colors pressed by the user */
    val correctLength: Int
        get() = if (isCorrect()) {length} else {length-1}

    override fun toString(): String {
        return sequence
    }

    /** Check if the user has inputted the correct color
     * @return True if the sequences are the same
     */
    fun isCorrect(): Boolean {
        return sequence == userSequence
    }

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
}
