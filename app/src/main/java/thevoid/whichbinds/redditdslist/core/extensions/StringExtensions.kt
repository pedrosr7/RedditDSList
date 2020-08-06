package thevoid.whichbinds.redditdslist.core.extensions

fun String.refactorId() = replace("/title/", "").replace("/", "")