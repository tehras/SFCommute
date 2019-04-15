package com.github.tehras.sfcommute.panel

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View.MeasureSpec.EXACTLY
import android.view.View.MeasureSpec.makeMeasureSpec
import android.widget.FrameLayout
import com.github.tehras.sfcommute.R
import com.github.tehras.sfcommute.mainflow.display
import com.github.tehras.sfcommute.mainflow.isTablet
import com.squareup.workflow.ui.ModalContainer
import com.squareup.workflow.ui.ViewBinding
import java.lang.Math.min

object PanelContainer : ViewBinding<PanelContainerScreen<*, *>>
by ModalContainer.forContainerScreen(
    R.id.tic_tac_workflow_panel_container,
    modalDecorator = { panelBody ->
        PanelBodyWrapper(panelBody.context).apply { addView(panelBody) }
    })

/**
 * [FrameLayout] that calculates its size based on the screen size -- to fill the screen on
 * phones, or make a square based on the shorter screen dimension on tablets. Handy
 * for showing a `Dialog` window that is set to `WRAP_CONTENT`, like those created by
 * [ModalContainer.forContainerScreen].
 */
internal class PanelBodyWrapper
@JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : FrameLayout(context, attributeSet) {
    init {
        @Suppress("DEPRECATION")
        background = ColorDrawable(resources.getColor(R.color.panelBody))
    }

    /** For use only by [onMeasure]. Instantiated here to avoid allocation during measure. */
    private val displayMetrics = DisplayMetrics()

    override fun onMeasure(
        widthMeasureSpec: Int,
        heightMeasureSpec: Int
    ) {
        context.display.getMetrics(displayMetrics)
        val calculatedWidthSpec: Int
        val calculatedHeightSpec: Int

        if (context.isTablet) {
            val size = min(displayMetrics.widthPixels, displayMetrics.heightPixels)

            calculatedWidthSpec = makeMeasureSpec(size, EXACTLY)
            calculatedHeightSpec = makeMeasureSpec(size, EXACTLY)
        } else {
            calculatedWidthSpec = makeMeasureSpec(displayMetrics.widthPixels, EXACTLY)
            calculatedHeightSpec = makeMeasureSpec(displayMetrics.heightPixels, EXACTLY)
        }

        super.onMeasure(calculatedWidthSpec, calculatedHeightSpec)
    }
}