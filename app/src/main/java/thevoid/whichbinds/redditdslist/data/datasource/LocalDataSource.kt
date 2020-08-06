package thevoid.whichbinds.redditdslist.data.datasource

import arrow.Kind
import thevoid.whichbinds.redditdslist.core.Runtime

/*
fun <F> Runtime<F>.loadLocalTopMovies(): Kind<F, List<MovieTop>> = fx.concurrent {

    val response = !effect(context.bgDispatcher) { getTopMovies() }
    continueOn(context.mainDispatcher)
    response.entityToDomain()
}

private fun <F> Runtime<F>.getTopMovies() = context.movieDao.getTopMovies()

fun <F> Runtime<F>.insertLocalTopMovies(topMovies: List<MovieTop>): Kind<F, Unit> = fx.concurrent {
    !effect(context.bgDispatcher) { insert(topMovies) }
    continueOn(context.mainDispatcher)
}

private fun <F> Runtime<F>.insert(topMovies: List<MovieTop>) = context.movieDao.insertTopMovies(topMovies = topMovies.toEntity())
*/
