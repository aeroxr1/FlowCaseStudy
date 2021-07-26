package com.aeroxr1.flowcasestudy.analytics

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

interface AnalyticsHelper{
    fun log(msg:String)
}


@Singleton
class MyAnalyticsHelper @Inject constructor() :AnalyticsHelper{

    private var counter:Int = 0

    override fun log(msg: String) {
        Log.w("MyAnalyticsHelper", "$counter,$msg")
        counter++
    }

}