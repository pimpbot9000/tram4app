package pyramidihuijaus.app.tram4.main_activity

import pyramidihuijaus.app.tram4.api.models.DepartureInfo

data class Timetable(val departures: List<DepartureInfo>, val message: String)