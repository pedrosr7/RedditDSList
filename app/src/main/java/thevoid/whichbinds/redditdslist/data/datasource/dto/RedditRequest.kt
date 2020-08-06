package thevoid.whichbinds.redditdslist.data.datasource.dto

data class RedditRequest(
    val loadSize: Int,
    val after: String? = null,
    val before: String? = null
)