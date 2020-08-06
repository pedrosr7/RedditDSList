package thevoid.whichbinds.redditdslist.domain.usecases

import arrow.Kind
import thevoid.whichbinds.redditdslist.core.Runtime
import thevoid.whichbinds.redditdslist.core.CachePolicy
import thevoid.whichbinds.redditdslist.data.datasource.dto.RedditRequest
import thevoid.whichbinds.redditdslist.domain.models.RedditListing
import thevoid.whichbinds.redditdslist.data.repository.getRedditPostsWithCachePolicy

fun <F> Runtime<F>.getRedditPosts(redditRequest: RedditRequest): Kind<F, RedditListing> =
    getRedditPostsWithCachePolicy(redditRequest, CachePolicy.NetworkOnly)