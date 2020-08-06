package thevoid.whichbinds.redditdslist.presentation

import androidx.lifecycle.*
import arrow.fx.IO
import kotlinx.coroutines.CoroutineScope

class StateViewModel<ViewState>(initialState: ViewState) : ViewModel(), ViewStateCache<ViewState> {

    private val _viewState = MutableLiveData<ViewState>(initialState)
    private val viewState: LiveData<ViewState> = _viewState

    override val cacheScope: CoroutineScope = viewModelScope

    override fun observeViewState(
        observer: LifecycleOwner,
        renderingScope: CoroutineScope,
        render: (ViewState) -> IO<Unit>
    ) = IO {
        viewState.observe(observer, Observer<ViewState> { viewState ->
            viewState?.let {
                //render(it).unsafeRunScoped(renderingScope) {}
            }
        })
    }

    override fun updateViewState(transform: (ViewState) -> ViewState): IO<ViewState> =
        IO {
            val transformedState = transform(viewState.value!!)
            _viewState.postValue(transformedState)
            transformedState
        }

    /*fun doNetworkRequest(): IO<Unit> = IO.fx {
        !viewStateCache.updateViewState { Loading }
        !repository.requestSomeResult().redeemWith(
            ft = {
                viewStateCache.updateViewState { ErrorViewState(ServerError) }
            },
            fe = { error ->
                viewStateCache.updateViewState { ErrorViewState(error) }
            },
            fb = { data ->
                viewStateCache.updateViewState { SuccessfulViewState(data) }
            }
        )
    }*/

}
