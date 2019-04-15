package com.github.tehras.sfcommute.caltrain

import android.view.View
import com.github.tehras.sfcommute.R
import com.squareup.coordinators.Coordinator
import com.squareup.workflow.ui.LayoutBinding
import com.squareup.workflow.ui.ViewBinding
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import timber.log.Timber

internal class CaltrainLoadingCoordinator(
    private val screens: Observable<out CaltrainLoadingScreen>
) : Coordinator() {
    private val subs = CompositeDisposable()

    override fun attach(view: View) {
        super.attach(view)

        subs += screens
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Timber.d("response :: $it")
            }
    }

    override fun detach(view: View) {
        subs.clear()
        super.detach(view)
    }

    companion object : ViewBinding<CaltrainLoadingScreen> by LayoutBinding.of(
        R.layout.caltrain_loading_schedule_view, ::CaltrainLoadingCoordinator
    )
}