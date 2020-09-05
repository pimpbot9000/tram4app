package com.example.tram4.main_activity

import com.example.tram4.api.models.DepartureInfo

data class Timetable(val departures: List<DepartureInfo>, val message: String)