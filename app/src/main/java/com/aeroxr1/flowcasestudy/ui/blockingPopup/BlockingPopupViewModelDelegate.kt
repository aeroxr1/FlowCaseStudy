package com.aeroxr1.flowcasestudy.ui.blockingPopup


import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton


interface BlockingPopupViewModelDelegate {
    val okVersion: Flow<Boolean>
    fun pressOk()
}


@Singleton
class MyBlockingPopupViewModelDelegate @Inject constructor() : BlockingPopupViewModelDelegate{

    // SIDE EFFECTS: Error messages
    // Guard against too many error messages by limiting to 3, keeping the oldest.
    private val _okVersion = Channel<Boolean>(1, BufferOverflow.DROP_LATEST)
    override val okVersion: Flow<Boolean> = _okVersion.receiveAsFlow()

    override fun pressOk(){
        _okVersion.trySend(true)
    }

}
