package thevoid.whichbinds.redditdslist.domain.models

data class RedditPost (
    val key: String,
    val url: String,
    val title: String,
    val score: Int,
    val author: String,
    val commentCount: Int,
    val permalink: String,
    val media: RedditMedia?
)