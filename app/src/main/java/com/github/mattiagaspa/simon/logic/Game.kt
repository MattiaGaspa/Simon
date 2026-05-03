package com.github.mattiagaspa.simon.logic

import android.util.Log

/** Class used to represent a game
 * @param sequence The sequence that the user must replicate
 * @param userSequence The sequence inputted by the user
 */
data class Game(
    internal var sequence: String = "",
    internal var userSequence: String = "",
) {
    /** Number of colors pressed */
    val length: Int
        get() = sequence.replace(", ", "").length

    /** Number of correct colors pressed by the user */
    val correctLength: Int
        get() = if (isCorrect()) length else (length - 1).coerceAtLeast(0)

    override fun toString(): String = sequence
}

/** Function to check if the user made the correct guess
 * @param log If true, logs the result
 * @return True if the user made the correct guess, false otherwise
 */
fun Game.isCorrect(log: Boolean = true): Boolean {
    if (sequence == userSequence) {
        if (log) Log.i(this::class.java.toString(), "User made the correct guess")
        return true
    } else {
        if (log) Log.i(this::class.java.toString(), "User made the wrong guess")
        return false
    }
}

/** Function to check if the user is inserting the correct color
 * @param log If true, logs the result
 * @return True if the user is inserting the correct color, false otherwise
 */
fun Game.isCorrectGuess(log: Boolean = true): Boolean {
    if (sequence.startsWith(userSequence)) {
        if (log) Log.i(this::class.java.toString(), "User is inserting the correct color")
        return true
    } else {
        if (log) Log.i(this::class.java.toString(), "User is inserting the wrong color")
        return false
    }
}