package thevoid.whichbinds.redditdslist.data.datasource.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
class MovieTopEntity(@PrimaryKey @ColumnInfo(name = "id") val id: String, @ColumnInfo(name = "chartRating") val chartRating: Double)