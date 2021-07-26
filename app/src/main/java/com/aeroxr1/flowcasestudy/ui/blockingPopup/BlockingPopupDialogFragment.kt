package com.aeroxr1.flowcasestudy.ui.blockingPopup

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BlockingPopupDialogFragment : AppCompatDialogFragment() {

    companion object {
        fun newInstance() = BlockingPopupDialogFragment()
    }

    private lateinit var viewModel: BlockingPopupDialogViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        viewModel = ViewModelProvider(this).get(BlockingPopupDialogViewModel::class.java)
        return AlertDialog.Builder(requireContext())
            .setMessage("messaggio da customizzare")
            .setPositiveButton("ok") { _,_ -> viewModel.pressOk()}
            .create()
    }



}