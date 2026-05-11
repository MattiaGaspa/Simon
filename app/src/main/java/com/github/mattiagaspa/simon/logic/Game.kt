package com.github.mattiagaspa.simon.logic

import android.util.Log
import androidx.room.*

/** Class used to represent a game
 * @param sequence The sequence that the user must replicate
 * @param userSequence The sequence inputted by the user
 */
@Entity
data class Game(
    @PrimaryKey(autoGenerate=true) internal val uid: Int = 0,
    @ColumnInfo(name="sequence") internal var sequence: String = "",
    @ColumnInfo(name="user_sequence") internal var userSequence: String = "",
    @ColumnInfo(name="max_correct_length") internal var maxCorrectLength: Int = 0,
) {
    /** Sequence length */
    val length: Int
        get() = sequence.replace(", ", "").length
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

@Dao
interface GameDao {
    @Query("SELECT * FROM Game")
    fun getAll(): List<Game>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg game: Game)

    @Delete
    fun delete(game: Game)
}


@Database(entities = [Game::class], version = 1)
abstract class GameDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
}