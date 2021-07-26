package com.aeroxr1.flowcasestudy.data

import android.util.Log
import javax.inject.Inject

interface DownloadStatus{

    fun progress(progress:Int)

    fun success()

    fun failed()

}

class DataRepository @Inject constructor() {

    fun startDownload(callback:DownloadStatus){
        Thread{
            (0..100).forEach{
                Thread.sleep(100)
                callback.progress(it)
                Log.d("DataRepository", "Progress $it")
            }
            callback.success()
        }.start()
    }

}