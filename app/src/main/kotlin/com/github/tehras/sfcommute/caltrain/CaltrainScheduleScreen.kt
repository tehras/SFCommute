package com.github.tehras.sfcommute.caltrain

data class CaltrainScheduleScreen(
    val onEvent: (Event) -> Unit = {},
    val schedule: List<String> = listOf(),
    val state: State = State.LOADING
) {
    sealed class Event {
        object LoadData : Event()
    }

    enum class State {
        LOADING, SUCCESS, ERROR
    }
}