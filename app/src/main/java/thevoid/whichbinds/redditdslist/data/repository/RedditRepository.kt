package thevoid.whichbinds.redditdslist.data.repository

import arrow.Kind
import thevoid.whichbinds.redditdslist.core.CachePolicy
import thevoid.whichbinds.redditdslist.core.Runtime
import thevoid.whichbinds.redditdslist.data.datasource.dto.RedditRequest
import thevoid.whichbinds.redditdslist.data.datasource.loadRedditPost
import thevoid.whichbinds.redditdslist.domain.models.RedditListing

fun <F> Runtime<F>.getRedditPostsWithCachePolicy(redditRequest: RedditRequest, policy: CachePolicy): Kind<F, RedditListing> =
    when (policy) {
        CachePolicy.NetworkOnly -> loadRedditPost(redditRequest)
        CachePolicy.NetworkFirst -> loadRedditPost(redditRequest) // TODO change to conditional call
        CachePolicy.LocalOnly -> loadRedditPost(redditRequest) // TODO change to local only cache call
        CachePolicy.LocalFirst -> loadRedditPost(redditRequest) // TODO change to conditional call
    }