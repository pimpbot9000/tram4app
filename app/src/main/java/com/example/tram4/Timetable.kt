package com.example.tram4

import com.example.tram4.api.models.DepartureInfo

data class Timetable(val departures: List<DepartureInfo>, val message: String)