package thevoid.whichbinds.redditdslist.presentation

import androidx.lifecycle.LifecycleOwner
import arrow.fx.IO
import kotlinx.coroutines.CoroutineScope

// TypeClass  for view state
interface ViewStateCache<ViewState> {

    val cacheScope: CoroutineScope

    fun observeViewState(observer: LifecycleOwner, renderingScope: CoroutineScope, render: (ViewState) -> IO<Unit>): IO<Unit>

    fun updateViewState(transform: (ViewState) -> ViewState): IO<ViewState>
}