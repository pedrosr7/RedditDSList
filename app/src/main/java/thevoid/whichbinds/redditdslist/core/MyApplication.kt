package thevoid.whichbinds.redditdslist.core

import android.app.Application
import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import thevoid.whichbinds.redditdslist.data.datasource.local.MovieRoomDatabase
import thevoid.whichbinds.redditdslist.data.datasource.network.ApiService
import thevoid.whichbinds.redditdslist.data.datasource.network.NewsAuthInterceptor
import thevoid.whichbinds.redditdslist.di.appModule
import java.util.concurrent.TimeUnit

class MyApplication : Application() {

    val runtimeContext by lazy {
        RuntimeContext(
            bgDispatcher = Dispatchers.IO,
            mainDispatcher = Dispatchers.Main,
            apiService = apiService
           // movieDao = movieRoom.movieDao()
        )
    }

    private val apiService: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.reddit.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()

        retrofit.create(ApiService::class.java)
    }

    private val movieRoom: MovieRoomDatabase by lazy {
        Room.databaseBuilder(
            baseContext, MovieRoomDatabase::class.java,
            "movie_database").build()
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true)
            .addNetworkInterceptor(NewsAuthInterceptor())
            .addInterceptor(loggingInterceptor)
            .build()
    }


    override fun onCreate(){
        super.onCreate()

        // start Koin!
        startKoin {
            // declare used Android context
            androidContext(this@MyApplication)
            // declare modules
            modules(appModule)
        }
    }
}

fun Context.application(): MyApplication = applicationContext as MyApplication