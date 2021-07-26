package com.aeroxr1.flowcasestudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aeroxr1.flowcasestudy.ui.blockingPopup.BlockingPopupDialogFragment
import com.aeroxr1.flowcasestudy.ui.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    fun openDialog(){
        BlockingPopupDialogFragment.newInstance().show(
            supportFragmentManager, "test"
        )
    }
}