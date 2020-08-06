package thevoid.whichbinds.redditdslist.data.datasource.network

import okhttp3.Interceptor
import okhttp3.Response

class NewsAuthInterceptor(private val apiKey: String = "") : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        /*val url = request.url()
            .newBuilder()
            .addQueryParameter("x-rapidapi-key", "9d322448fdmshd1c0651de1b691dp1eb1acjsn91f37ff302cc")
            .build()*/

        request = request
            .newBuilder()
           /* .addHeader("x-rapidapi-host", "imdb8.p.rapidapi.com")
            .addHeader("x-rapidapi-key", "9d322448fdmshd1c0651de1b691dp1eb1acjsn91f37ff302cc")*/
            .build()
        return chain.proceed(request)
    }
}