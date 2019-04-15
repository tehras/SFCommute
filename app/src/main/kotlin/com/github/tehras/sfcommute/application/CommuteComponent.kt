package com.github.tehras.sfcommute.application

import android.app.Application
import com.github.tehras.base.dagger.components.MainComponent
import com.github.tehras.base.moshi.MoshiModule
import com.github.tehras.dagger.modules.AppModule
import com.github.tehras.dagger.scopes.ApplicationScope
import com.github.tehras.sfcommute.activity.MainWorkflowComponentCreator
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        AppModule::class,
        MoshiModule::class
    ]
)
interface CommuteComponent :
    MainComponent,
    UiComponents,
    MainWorkflowComponentCreator,
    Injectors {

    fun inject(application: CommuteApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): CommuteComponent
    }
}