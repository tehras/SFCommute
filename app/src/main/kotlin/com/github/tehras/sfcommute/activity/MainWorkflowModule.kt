package com.github.tehras.sfcommute.activity

import com.github.tehras.sfcommute.caltrain.RealCaltrainWorkflow
import com.github.tehras.sfcommute.mainflow.MainWorkflow
import dagger.Module
import dagger.Provides

@Module
object MainWorkflowModule {
    @Provides
    @JvmStatic
    fun providesMainWorkflow(caltrainWorkflow: RealCaltrainWorkflow): MainWorkflow = MainWorkflow(caltrainWorkflow)
}