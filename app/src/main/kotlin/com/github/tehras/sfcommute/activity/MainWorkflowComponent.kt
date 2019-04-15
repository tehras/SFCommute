package com.github.tehras.sfcommute.activity

import com.github.tehras.base.dagger.components.SubComponentCreator
import com.github.tehras.dagger.scopes.ActivityScope
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [MainWorkflowModule::class])
interface MainWorkflowComponent {
    fun inject(activity: MainActivity)
}

interface MainWorkflowComponentCreator : SubComponentCreator {
    fun plusMainWorkflowComponent(): MainWorkflowComponent
}