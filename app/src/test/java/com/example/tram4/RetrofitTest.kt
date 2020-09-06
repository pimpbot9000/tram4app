package com.example.tram4

import com.example.tram4.api.Tram4api
import com.example.tram4.api.TramService
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

@RunWith(RobolectricTestRunner::class)
class RetrofitTest{

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: Tram4api

    @Before
    fun setup(){

        System.setProperty("javax.net.ssl.trustStoreType", "JKS") // Some hack found from internet to make MockWebServer Work.
        mockWebServer = MockWebServer()
        val url = mockWebServer.url("/")
        apiService = TramService(url).getService()

    }
    @After
    fun teardown(){
        mockWebServer.shutdown()
    }

    @Test
    fun testParsing(){
        val response = MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(queryResponse)

        mockWebServer.enqueue(response)

        val timeTableResult = apiService.getTimeTable("alepa").blockingGet()
        val firstResult = timeTableResult[0]

        assert(timeTableResult.size == 5)
        assert(firstResult.departureInMinutes == 2)
        assert(firstResult.description == "Katajanokka via Meilahti")
        assert(firstResult.route == "4")
    }

    @Test
    fun testNetworkDelay(){
        val response = MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(queryResponse).setBodyDelay(5L, TimeUnit.SECONDS)
        mockWebServer.enqueue(response)
        val timeTableResult = apiService.getTimeTable("alepa").blockingGet()
        assert(timeTableResult.size == 5)

    }

    companion object{
        const val queryResponse = """
        [
        {
            "departureInMinutes": 2,
            "departureInSeconds": 106,
            "description": "Katajanokka via Meilahti",
            "sign": "Katajanokka",
            "route": "4"
        },
        {
            "departureInMinutes": 13,
            "departureInSeconds": 800,
            "description": "Katajanokka via Meilahti",
            "sign": "Katajanokka",
            "route": "4"
        },
        {
            "departureInMinutes": 25,
            "departureInSeconds": 1520,
            "description": "Katajanokka via Meilahti",
            "sign": "Katajanokka",
            "route": "4"
        },
        {
            "departureInMinutes": 37,
            "departureInSeconds": 2240,
            "description": "Katajanokka via Meilahti",
            "sign": "Katajanokka",
            "route": "4"
        },
        {
            "departureInMinutes": 49,
            "departureInSeconds": 2960,
            "description": "Katajanokka via Meilahti",
            "sign": "Katajanokka",
            "route": "4"
        }
        ]"""

    }
}

