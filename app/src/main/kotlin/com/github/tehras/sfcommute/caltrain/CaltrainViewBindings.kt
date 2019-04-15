package com.github.tehras.sfcommute.caltrain

import com.squareup.workflow.ui.ViewRegistry
import com.squareup.workflow.ui.backstack.PushPopEffect

val CaltrainViewBindings = ViewRegistry(
    CaltrainScheduleCoordinator
) + PushPopEffect