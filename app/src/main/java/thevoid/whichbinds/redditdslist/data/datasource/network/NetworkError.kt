package thevoid.whichbinds.redditdslist.data.datasource.network

sealed class NetworkError : Throwable() {
    object Unauthorized : NetworkError()
    object NotFound : NetworkError()
    object ServerError : NetworkError()
}