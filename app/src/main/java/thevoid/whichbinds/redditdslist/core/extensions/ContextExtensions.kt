package thevoid.whichbinds.redditdslist.core.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities


val Context.connectivityManager: ConnectivityManager
    get() = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

val Context.network: Network?
    get() = connectivityManager.activeNetwork

val Context.capabilities: NetworkCapabilities
    get() = connectivityManager.getNetworkCapabilities(network)!!

fun Context.isNetworkAvailable(): Boolean = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)