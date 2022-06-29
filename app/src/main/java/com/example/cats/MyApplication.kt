package com.example.cats

import android.app.Application
import com.example.cats.common.composition.AppCompositionRoot
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application() {

    lateinit var appCompositionRoot : AppCompositionRoot

    override fun onCreate() {
        appCompositionRoot = AppCompositionRoot()
        super.onCreate()
    }
}
