package thevoid.whichbinds.redditdslist.domain.models

data class RedditMedia(
    val scrubber_media_url: String,
    val fallbackUrl: String,
    val dash_url: String,
    val hls_url: String,
    val duration: Int,
    val height: Int,
    val width: Int
)