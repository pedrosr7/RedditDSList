package thevoid.whichbinds.redditdslist.data.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(MovieTopEntity::class), version = 1, exportSchema = false)
abstract class MovieRoomDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: MovieRoomDatabase? = null

        fun getDatabase(context: Context): MovieRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance: MovieRoomDatabase by lazy {
                    Room.databaseBuilder(context.applicationContext,
                    MovieRoomDatabase::class.java,
                    "movie_database").build()
                }
                INSTANCE = instance
                return instance
            }
        }
    }
}