package thevoid.whichbinds.redditdslist.presentation.viewmodels

import androidx.lifecycle.*
import arrow.fx.IO
import arrow.fx.extensions.io.unsafeRun.runNonBlocking
import arrow.unsafe
import kotlinx.coroutines.flow.*
import thevoid.whichbinds.redditdslist.core.MyApplication
import thevoid.whichbinds.redditdslist.core.runtime
import thevoid.whichbinds.redditdslist.domain.models.RedditPost

class RedditPostViewModel(application: MyApplication) : ViewModel(),
    RedditListView {

    val redditPost: MutableLiveData<List<RedditPost>> by lazy {
        MutableLiveData<List<RedditPost>>()
    }

    val showLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }


    private val _redditPosts: Flow<List<RedditPost>> = flow { listOf<RedditPost>() }
    var redditPostsFlow: List<RedditPost> = listOf()

    private val runtime = IO.runtime(application.runtimeContext)


    fun getPosts(after: String?, before: String?) {
        unsafe { runNonBlocking({ runtime.getPosts(
            after = after,
            before = before,
            view = this@RedditPostViewModel
        )}, {}) }
    }


    override fun drawReddit(redditPosts: List<RedditPost>) {
        redditPost.postValue(redditPosts)
        redditPostsFlow.asFlow()
    }

    override fun showLoading() {
        showLoading.postValue(true)
    }

    override fun hideLoading() {
        showLoading.postValue(false)
    }

    override fun showNotFoundError() {
        TODO("Not yet implemented")
    }

    override fun showGenericError() {
        TODO("Not yet implemented")
    }

    override fun showAuthenticationError() {
        TODO("Not yet implemented")
    }

}
