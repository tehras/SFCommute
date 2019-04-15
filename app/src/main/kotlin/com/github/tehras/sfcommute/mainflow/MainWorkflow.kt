package com.github.tehras.sfcommute.mainflow

import com.github.tehras.sfcommute.caltrain.CaltrainScreen
import com.github.tehras.sfcommute.caltrain.CaltrainWorkflow
import com.squareup.workflow.Snapshot
import com.squareup.workflow.StatefulWorkflow
import com.squareup.workflow.WorkflowAction.Companion.enterState
import com.squareup.workflow.WorkflowContext
import com.squareup.workflow.compose
import javax.inject.Inject

class MainWorkflow @Inject constructor(private val caltrainWorkflow: CaltrainWorkflow) :
    StatefulWorkflow<Unit, MainState, Unit, CaltrainScreen>() {

    override fun initialState(
        input: Unit,
        snapshot: Snapshot?
    ): MainState = snapshot?.let { MainState.fromSnapshot(snapshot.bytes) }
        ?: MainState.CalTrainHome

    override fun compose(
        input: Unit,
        state: MainState,
        context: WorkflowContext<MainState, Unit>
    ): CaltrainScreen = when (state) {
        is MainState.CalTrainHome -> context.compose(caltrainWorkflow) { enterState(MainState.CalTrainHome) }
    }

    override fun snapshotState(state: MainState): Snapshot = state.toSnapshot()
}
