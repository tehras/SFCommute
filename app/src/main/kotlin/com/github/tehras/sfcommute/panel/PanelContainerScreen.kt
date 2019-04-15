package com.github.tehras.sfcommute.panel

import com.squareup.workflow.ui.BackStackScreen
import com.squareup.workflow.ui.HasModals

data class PanelContainerScreen<B : Any, T : Any>(
    override val baseScreen: B,
    override val modals: List<BackStackScreen<T>> = emptyList()
) : HasModals<B, BackStackScreen<T>> {
    constructor(
        baseScreen: B,
        panelBody: T
    ) : this(baseScreen, listOf(BackStackScreen(panelBody)))
}

fun <B : Any, T : Any> BackStackScreen<T>.asPanelOver(baseScreen: B): PanelContainerScreen<B, T> {
    return PanelContainerScreen(baseScreen, listOf(this))
}

fun <B : Any, T : Any> T.asPanelOver(baseScreen: B): PanelContainerScreen<B, T> {
    return PanelContainerScreen(baseScreen, this)
}