package com.example.tram4.api

import com.example.tram4.api.models.DepartureInfo
import io.reactivex.Single
import retrofit2.http.GET

interface Tram4api{

    @GET("api/portti")
    fun getTimeTable(): Single<List<DepartureInfo>>

}