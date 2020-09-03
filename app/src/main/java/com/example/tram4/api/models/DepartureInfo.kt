package com.example.tram4.api.models

import com.google.gson.annotations.SerializedName

data class DepartureInfo(

    @field:SerializedName("departureInMinutes")
    val departureInMinutes: Int,

    @field:SerializedName("departureInSeconds")
    val departureInSeconds: Int,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("sign")
    val sign: String,

    @field:SerializedName("route")
    val route: String

)