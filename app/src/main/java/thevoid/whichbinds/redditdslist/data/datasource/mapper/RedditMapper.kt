package thevoid.whichbinds.redditdslist.data.datasource.mapper


import com.google.gson.annotations.SerializedName
import thevoid.whichbinds.redditdslist.data.datasource.dto.RedditResponse
import thevoid.whichbinds.redditdslist.data.datasource.dto.NetworkPostContainer
import thevoid.whichbinds.redditdslist.data.datasource.dto.NetworkRedditMedia
import thevoid.whichbinds.redditdslist.data.datasource.dto.NetworkRedditVideo
import thevoid.whichbinds.redditdslist.domain.models.RedditListing
import thevoid.whichbinds.redditdslist.domain.models.RedditMedia
import thevoid.whichbinds.redditdslist.domain.models.RedditPost

fun RedditResponse.toDomain() = RedditListing(
    posts = data.children.toDomain(),
    after = data.after,
    before = data.before
)

fun List<NetworkPostContainer>.toDomain(): List<RedditPost> = map { it.toDomain() }

fun NetworkPostContainer.toDomain() = RedditPost(
    key = data.key,
    url = data.url,
    title = data.title,
    score = data.score,
    author = data.author,
    commentCount = data.commentCount,
    permalink = data.permalink,
    media = data.media?.video?.toDomain()
)

fun NetworkRedditMedia.toDomain() = RedditMedia(
    scrubber_media_url = scrubber_media_url,
    fallbackUrl = fallbackUrl,
    dash_url = dash_url,
    hls_url = hls_url,
    duration = duration,
    height = height,
    width = width
)