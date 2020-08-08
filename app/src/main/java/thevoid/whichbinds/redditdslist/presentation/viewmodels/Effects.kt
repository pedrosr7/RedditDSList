package thevoid.whichbinds.redditdslist.presentation.viewmodels

import arrow.Kind
import thevoid.whichbinds.redditdslist.core.Runtime
import thevoid.whichbinds.redditdslist.domain.models.*
import thevoid.whichbinds.redditdslist.domain.usecases.getRedditPosts
import thevoid.whichbinds.redditdslist.data.datasource.dto.RedditRequest

fun <F> Runtime<F>.getPosts(after: String?, before: String?, view: RedditListView): Kind<F, Unit> = fx.concurrent {
    !effect { view.showLoading() }

    val maybeNews = !getRedditPosts(RedditRequest(loadSize = 10, after = after, before = before)).attempt()

    !effect { view.hideLoading() }
    !effect {
        maybeNews.fold(
            ifLeft = {
                displayErrors(
                    view,
                    it
                )
            },
            ifRight = {
                view.drawReddit(it.posts)
            }
        )
    }
}


interface StateView {
    fun showLoading()
    fun hideLoading()
    fun showNotFoundError()
    fun showGenericError()
    fun showAuthenticationError()
}

interface RedditListView :
    StateView {
    fun drawReddit(redditPosts: List<RedditPost>)
}


private fun displayErrors(
    view: StateView,
    t: Throwable
) {
    when (DomainError.fromThrowable(t)) {
        is DomainError.NotFoundError -> view.showNotFoundError()
        is DomainError.UnknownServerError -> view.showGenericError()
        is DomainError.AuthenticationError -> view.showAuthenticationError()
    }
}