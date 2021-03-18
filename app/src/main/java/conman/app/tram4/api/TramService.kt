package conman.app.tram4.api

import io.reactivex.schedulers.Schedulers
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class TramService(baseUrl: HttpUrl){

    private val mTramService: Tram4api

    init {

        val client = OkHttpClient()
        client.newBuilder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mTramService = retrofit.create(Tram4api::class.java)
    }


    fun getService(): Tram4api {
        return mTramService
    }

    companion object {
        private const val TIMEOUT = 10L
        const val BASE_URL = "https://tram-4-service.herokuapp.com"
    }
}