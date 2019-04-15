package com.github.tehras.sfcommute.caltrain

import android.view.View
import com.github.tehras.sfcommute.R
import com.squareup.coordinators.Coordinator
import com.squareup.workflow.ui.LayoutBinding
import com.squareup.workflow.ui.ViewBinding
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

internal class CaltrainScheduleCoordinator(
    private val screens: Observable<out CaltrainScheduleScreen>
) : Coordinator() {
    private val subs = CompositeDisposable()

    override fun attach(view: View) {
        super.attach(view)
    }

    override fun detach(view: View) {
        subs.clear()
        super.detach(view)
    }

    companion object : ViewBinding<CaltrainScheduleScreen> by LayoutBinding.of(
        R.layout.caltrain_schedule_view, ::CaltrainScheduleCoordinator
    )
}