package thevoid.whichbinds.redditdslist.data.datasource.dto

import com.google.gson.annotations.SerializedName

data class NetworkRedditPost(
    @SerializedName("name")
    val key: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("score")
    val score: Int,
    @SerializedName("author")
    val author: String,
    @SerializedName("num_comments")
    val commentCount: Int,
    @SerializedName("permalink")
    val permalink: String,
    @SerializedName("media")
    val media: NetworkRedditVideo? = null
)

data class NetworkRedditVideo(@SerializedName("reddit_video") val video: NetworkRedditMedia)

data class NetworkRedditMedia(
    @SerializedName("scrubber_media_url")
    val scrubber_media_url: String,
    @SerializedName("fallback_url")
    val fallbackUrl: String,
    @SerializedName("dash_url")
    val dash_url: String,
    @SerializedName("hls_url")
    val hls_url: String,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("width")
    val width: Int
)