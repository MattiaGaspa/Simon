package com.github.mattiagaspa.simon.logic

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

/** Class used to represent a game
 * @param sequence The sequence that the user must replicate
 * @param userSequence The sequence inputted by the user
 * @param maxCorrectLength Length of the longest sequence inputted by the user
 * @param start Game start time
 * @param stop Game stop time
 */
@Entity
data class Game(
    @PrimaryKey(autoGenerate = true) internal val uid: Int = 0,
    @ColumnInfo(name = "sequence") internal var sequence: String = "",
    @ColumnInfo(name = "user_sequence") internal var userSequence: String = "",
    @ColumnInfo(name = "max_correct_length") internal var maxCorrectLength: Int = 0,
    @ColumnInfo(name = "start") internal var start: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "stop") internal var stop: Long = System.currentTimeMillis(),
)

/** Function to check if the user made the correct guess
 * @return True if the user made the correct guess, false otherwise
 */
fun Game.isCorrect(): Boolean {
    return sequence == userSequence
}

/** Function to check if the user is inserting the correct color
 * @return True if the user is inserting the correct color, false otherwise
 */
fun Game.isCorrectGuess(): Boolean {
    return sequence.startsWith(userSequence)
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