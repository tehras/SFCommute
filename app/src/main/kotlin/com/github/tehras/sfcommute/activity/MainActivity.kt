package com.github.tehras.sfcommute.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.tehras.base.dagger.components.findComponent
import com.github.tehras.sfcommute.R
import com.github.tehras.sfcommute.caltrain.CaltrainViewBindings
import com.github.tehras.sfcommute.mainflow.MainWorkflow
import com.github.tehras.sfcommute.panel.PanelContainer
import com.github.tehras.sfcommute.panel.PanelContainerScreen
import com.squareup.workflow.ui.*
import com.squareup.workflow.ui.backstack.BackStackContainer
import com.squareup.workflow.ui.backstack.PushPopEffect
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var workflow: MainWorkflow
    private lateinit var workflowViewModel: WorkflowActivityRunner<*, *>

    override fun onCreate(savedInstanceState: Bundle?) {
        findComponent<MainWorkflowComponentCreator>()
            .plusMainWorkflowComponent()
            .inject(this)

        super.onCreate(savedInstanceState)

        val restored = savedInstanceState?.getParcelable<PickledWorkflow>(WORKFLOW_BUNDLE_KEY)

        workflowViewModel = setContentWorkflow(viewRegistry, workflow, restored)
    }

    override fun onBackPressed() {
        if (!workflowViewModel.onBackPressed(this)) super.onBackPressed()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(WORKFLOW_BUNDLE_KEY, workflowViewModel.asParcelable())
    }

//    override fun onRetainCustomNonConfigurationInstance(): Any = component

    private companion object {
        const val WORKFLOW_BUNDLE_KEY = "workflow"

        val viewRegistry = ViewRegistry(
            BackStackContainer,
            ModalContainer.forAlertContainerScreen(),
            PanelContainer
        ) + CaltrainViewBindings + PushPopEffect
    }
}
