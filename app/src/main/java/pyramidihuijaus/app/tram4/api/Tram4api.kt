package pyramidihuijaus.app.tram4.api

import pyramidihuijaus.app.tram4.api.models.DepartureInfo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface Tram4api{

    @GET("api/{stop}")
    fun getTimeTable(@Path("stop") stop: String): Single<List<DepartureInfo>>

}