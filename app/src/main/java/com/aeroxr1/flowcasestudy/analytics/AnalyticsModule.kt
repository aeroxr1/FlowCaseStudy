package com.aeroxr1.flowcasestudy.analytics

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface AnalyticsModule {
    @Binds
    fun MyAnalyticsHelper.bindAnalyticsHelper(): AnalyticsHelper
}