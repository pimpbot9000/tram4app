package com.example.tram4.api

import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class TramService{

    private val mTramService: Tram4api

    init {

        val client = OkHttpClient()
        client.newBuilder()
            .connectTimeout(10000, TimeUnit.SECONDS)
            .writeTimeout(10000, TimeUnit.SECONDS)
            .readTimeout(10000, TimeUnit.SECONDS).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mTramService = retrofit.create(Tram4api::class.java)
    }

    /**
     * Return the API for zenderdata
     */
    fun getService(): Tram4api{
        return mTramService
    }

    companion object {
        //api entry point
        private const val BASE_URL = "https://tram-4-service.herokuapp.com"
    }
}