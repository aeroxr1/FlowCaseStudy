package com.aeroxr1.flowcasestudy.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.aeroxr1.flowcasestudy.MainActivity
import com.aeroxr1.flowcasestudy.R
import com.aeroxr1.flowcasestudy.analytics.AnalyticsHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    @Inject lateinit var analyticsHelper: AnalyticsHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val start = view.findViewById<Button>(R.id.button)
        start.setOnClickListener {
            viewModel.pressStartButton()
        }

        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        progressBar.isVisible = false
        viewModel.progressStatus.observe(viewLifecycleOwner, Observer { progressStatus ->
            progressBar.progress = progressStatus.progress
            progressBar.isVisible = progressStatus.visible
            start.isEnabled = !progressStatus.visible
        })

        // Observe navigation events
        launchAndRepeatWithViewLifecycle {
            viewModel.navigationActions.collect { action ->
                when (action) {
                    is MainNavigationAction.OpenAppVersionDialogAction -> (requireActivity() as MainActivity).openDialog()
                }
            }
        }
    }
}


/**
 * Launches a new coroutine and repeats `block` every time the Fragment's viewLifecycleOwner
 * is in and out of `minActiveState` lifecycle state.
 */
inline fun Fragment.launchAndRepeatWithViewLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.() -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(minActiveState) {
            block()
        }
    }
}