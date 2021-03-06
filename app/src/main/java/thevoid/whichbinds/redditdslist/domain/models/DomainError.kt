package thevoid.whichbinds.redditdslist.domain.models

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import thevoid.whichbinds.redditdslist.data.datasource.network.NetworkError

sealed class DomainError {

    companion object {
        fun fromThrowable(e: Throwable): DomainError =
            when (e) {
                is NetworkError.Unauthorized -> AuthenticationError
                is NetworkError.NotFound -> NotFoundError
                else -> UnknownServerError((Some(e)))
            }
    }

    object AuthenticationError : DomainError()
    object NotFoundError : DomainError()
    data class UnknownServerError(val e: Option<Throwable> = None) : DomainError()
}