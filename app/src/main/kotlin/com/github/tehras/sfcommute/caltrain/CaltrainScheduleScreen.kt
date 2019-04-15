package com.github.tehras.sfcommute.caltrain

import com.github.tehras.sfcommute.service.caltrain.models.RoutesResponse

data class CaltrainScheduleScreen(
    val onEvent: (Event) -> Unit = {},
    val schedule: RoutesResponse
) {
    sealed class Event {
        object LoadData : Event()
    }
}

object CaltrainLoadingScreen