package com.aeroxr1.flowcasestudy.ui.blockingPopup

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface BlockingPopupModule {
    @Binds
    fun MyBlockingPopupViewModelDelegate.bindAppVersionViewModelDelegate(): BlockingPopupViewModelDelegate
}