package thevoid.whichbinds.redditdslist.data.datasource.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import thevoid.whichbinds.redditdslist.data.datasource.dto.RedditResponse

interface ApiService {

    @GET("/r/aww/hot.json")
    fun fetchPosts(
        @Query("limit") loadSize: Int = 30,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null
    ): Call<RedditResponse>

}