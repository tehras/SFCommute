package com.github.tehras.sfcommute.caltrain

import com.github.tehras.sfcommute.caltrain.CaltrainScheduleScreen.Event.LoadData
import com.github.tehras.sfcommute.panel.PanelContainerScreen
import com.github.tehras.sfcommute.panel.asPanelOver
import com.github.tehras.sfcommute.service.caltrain.CaltrainService
import com.squareup.workflow.Snapshot
import com.squareup.workflow.StatefulWorkflow
import com.squareup.workflow.Workflow
import com.squareup.workflow.WorkflowAction.Companion.enterState
import com.squareup.workflow.WorkflowContext
import com.squareup.workflow.rx2.onSuccess
import com.squareup.workflow.ui.AlertContainerScreen
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

enum class CaltrainResult {
    CanceledStart,
    FinishedPlaying
}

typealias CaltrainScreen = AlertContainerScreen<PanelContainerScreen<*, *>>
typealias CaltrainWorkflow = Workflow<Unit, CaltrainResult, CaltrainScreen>

class RealCaltrainWorkflow @Inject constructor(private val caltrainService: CaltrainService) : CaltrainWorkflow,
    StatefulWorkflow<Unit, CaltrainState, CaltrainResult, CaltrainScreen>() {

    override fun initialState(
        input: Unit,
        snapshot: Snapshot?
    ): CaltrainState = snapshot?.let { CaltrainState.fromSnapshot(snapshot.bytes) } ?: CaltrainState.LoadScheduleData

    override fun compose(
        input: Unit,
        state: CaltrainState,
        context: WorkflowContext<CaltrainState, CaltrainResult>
    ): CaltrainScreen {
        return when (state) {
            is CaltrainState.Home -> {
                val eventHandler = context.onEvent<CaltrainScheduleScreen.Event> { event ->
                    when (event) {
                        LoadData -> enterState(CaltrainState.LoadScheduleData)
                    }
                }

                simpleScreen(
                    CaltrainScheduleScreen(schedule = state.routes)
                )
            }

            CaltrainState.LoadScheduleData -> {
                context.onSuccess(caltrainService.caltrainRoutes().subscribeOn(Schedulers.io())) { response ->
                    enterState(CaltrainState.Home(response))
                }

                simpleScreen(CaltrainLoadingScreen)
            }
        }
    }

    override fun snapshotState(state: CaltrainState): Snapshot = state.toSnapshot()

    private fun subflowScreen(
        base: Any,
        subflow: Any
    ): CaltrainScreen {
        return AlertContainerScreen(subflow.asPanelOver(base))
    }

    private fun simpleScreen(screen: Any): CaltrainScreen {
        return AlertContainerScreen(PanelContainerScreen<Any, Any>(screen))
    }
}