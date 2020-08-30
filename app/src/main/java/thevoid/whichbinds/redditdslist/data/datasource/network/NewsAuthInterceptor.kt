package thevoid.whichbinds.redditdslist.data.datasource.network

import okhttp3.Interceptor
import okhttp3.Response

class NewsAuthInterceptor(private val apiKey: String = "") : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        /*val url = request.url()
            .newBuilder()
            .addQueryParameter("x-rapidapi-key","")
            .build()*/

        request = request
            .newBuilder()
           /* .addHeader("x-rapidapi-host", "imdb8.p.rapidapi.com")
            .addHeader("x-rapidapi-key", "")*/
            .build()
        return chain.proceed(request)
    }
}