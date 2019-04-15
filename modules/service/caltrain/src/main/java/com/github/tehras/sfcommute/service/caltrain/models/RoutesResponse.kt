package com.github.tehras.sfcommute.service.caltrain.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RoutesResponse(
    val routes: List<Route>
)

@JsonClass(generateAdapter = true)
data class Route(
    val name: String,
    @Json(name = "stops_served_by_route")
    val stopsServedByRoute: List<StopsServedByRoute>
)

@JsonClass(generateAdapter = true)
data class StopsServedByRoute(
    @Json(name = "stop_name")
    val stopName: String,
    @Json(name = "stop_onestop_id")
    val stopId: String
)