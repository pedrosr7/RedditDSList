package thevoid.whichbinds.redditdslist.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * from movie_table ORDER BY chartRating ASC")
    fun getTopMovies(): List<MovieTopEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: MovieTopEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTopMovies(topMovies: List<MovieTopEntity>)

    @Query("DELETE FROM movie_table")
    suspend fun deleteAll()
}