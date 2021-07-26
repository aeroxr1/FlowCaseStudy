package com.aeroxr1.flowcasestudy.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeroxr1.flowcasestudy.analytics.AnalyticsHelper
import com.aeroxr1.flowcasestudy.data.DataRepository
import com.aeroxr1.flowcasestudy.data.DownloadStatus
import com.aeroxr1.flowcasestudy.ui.blockingPopup.BlockingPopupViewModelDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val dataRepository: DataRepository,
    val analyticsHelper: AnalyticsHelper,
    val blockingPopupViewModelDelegate: BlockingPopupViewModelDelegate
) : ViewModel() {

    val progressStatus: LiveData<ProgressStatus>
        get() = _progressStatus
    private val _progressStatus = MutableLiveData<ProgressStatus>()

    // SIDE EFFECTS: Navigation actions
    private val _navigationActions = Channel<MainNavigationAction>(capacity = Channel.CONFLATED)
    // Exposed with receiveAsFlow to make sure that only one observer receives updates.
    val navigationActions = _navigationActions.receiveAsFlow()

    val appVersionCheck:Boolean = true

    fun pressStartButton(){
        analyticsHelper.log("pressStartButton()")
        viewModelScope.launch(Dispatchers.Main) {
            supervisorScope {
                _progressStatus.value = ProgressStatus()

                if(appVersionCheck) {
                    //check version and go on
                    analyticsHelper.log("check version and go on")
                    _navigationActions.tryOffer(MainNavigationAction.OpenAppVersionDialogAction)
                    analyticsHelper.log("end try offer")
                    withContext(Dispatchers.IO){
                        blockingPopupViewModelDelegate.okVersion.collect {
                            analyticsHelper.log("okVersion check")
                            if(it){
                                downloadProcess()
                            }
                        }
                    }
                } else {
                    downloadProcess()
                }

            }
        }
        analyticsHelper.log("end pressStartButton")
    }

    private fun downloadProcess() {
        dataRepository.startDownload(object : DownloadStatus {
            override fun progress(progress: Int) {
                _progressStatus.postValue(ProgressStatus(progress = progress))
            }

            override fun success() {

                analyticsHelper.log("dataRepository success")
                analyticsHelper.log("dataRepository success")
                _progressStatus.postValue(ProgressStatus(visible = false))
            }

            override fun failed() {
                _progressStatus.postValue(ProgressStatus(visible = false))
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        analyticsHelper.log("onCleared()")
    }
}

data class ProgressStatus(val visible:Boolean = true, val isIndeterminate:Boolean = false, val progress:Int = 0)

sealed class MainNavigationAction {
    object OpenAppVersionDialogAction : MainNavigationAction()
}

/**
 * Tries to send an element to a Channel and ignores the exception.
 */
fun <E> SendChannel<E>.tryOffer(element: E): Boolean = try {
    offer(element)
} catch (t: Throwable) {
    false // Ignore
}