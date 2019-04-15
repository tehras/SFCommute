package com.github.tehras.sfcommute.application

import android.app.Application
import android.os.StrictMode
import com.github.tehras.base.dagger.components.ComponentProvider
import com.github.tehras.base.dagger.components.DaggerApplication
import com.github.tehras.base.log.CrashReportingTree
import com.github.tehras.sfcommute.BuildConfig
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import timber.log.Timber

class CommuteApplication : Application(), DaggerApplication, ComponentProvider<CommuteComponent> {
    private lateinit var appComponent: CommuteComponent
    override fun getComponent(): CommuteComponent = appComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerCommuteComponent.builder()
            .application(this)
            .build()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())

            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build()
            )
        } else {
            Timber.plant(CrashReportingTree())

            AppCenter.start(
                this,
                "fcb64968-1337-4077-bd45-7362c113854f",
                Analytics::class.java, Crashes::class.java
            )
        }

        appComponent.inject(this)

    }
}

