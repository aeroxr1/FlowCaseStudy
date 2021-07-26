package com.aeroxr1.flowcasestudy.ui.blockingPopup

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BlockingPopupDialogViewModel @Inject constructor(appVersionDialogFragment: BlockingPopupViewModelDelegate) : ViewModel(), BlockingPopupViewModelDelegate by appVersionDialogFragment {
        //due stadi, download in progress e pulsante si/no

}