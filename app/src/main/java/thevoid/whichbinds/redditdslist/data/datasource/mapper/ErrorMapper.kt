package thevoid.whichbinds.redditdslist.data.datasource.mapper

import thevoid.whichbinds.redditdslist.data.datasource.network.NetworkError

fun Int.toNetworkError() = when (this) {
    401 -> NetworkError.Unauthorized
    else -> NetworkError.ServerError
}

fun Throwable.normalizeError() = when (this) {
    is NetworkError -> this
    else -> NetworkError.ServerError
}