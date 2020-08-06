package thevoid.whichbinds.redditdslist.data.datasource

import arrow.Kind
import thevoid.whichbinds.redditdslist.core.Runtime
import thevoid.whichbinds.redditdslist.data.datasource.dto.RedditRequest
import thevoid.whichbinds.redditdslist.data.datasource.mapper.normalizeError
import thevoid.whichbinds.redditdslist.data.datasource.mapper.*
import thevoid.whichbinds.redditdslist.domain.models.RedditListing

fun <F> Runtime<F>.loadRedditPost(redditRequest: RedditRequest): Kind<F, RedditListing> = fx.concurrent {
    val response = !effect(context.bgDispatcher) {
        fetchRedditPost(redditRequest)
    }
    continueOn(context.mainDispatcher)

    if (response.isSuccessful) {
        response.body()!!.toDomain()
    } else {
        !raiseError<RedditListing>(response.code().toNetworkError())
    }
}.handleErrorWith { error -> raiseError(error.normalizeError()) }

private fun <F> Runtime<F>.fetchRedditPost(redditRequest: RedditRequest) =
    context.apiService.fetchPosts(loadSize = redditRequest.loadSize, after = redditRequest.after, before = redditRequest.before).execute()