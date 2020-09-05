package com.example.tram4.mainactivity

import com.example.tram4.api.models.DepartureInfo

data class Timetable(val departures: List<DepartureInfo>, val message: String)