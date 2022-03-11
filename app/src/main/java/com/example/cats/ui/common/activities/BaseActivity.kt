package com.example.cats.ui.common.activities

import androidx.appcompat.app.AppCompatActivity
import com.example.cats.MyApplication
import com.example.cats.common.composition.ActivityCompositionRoot

open class BaseActivity: AppCompatActivity() {

    private val appCompositionRoot get() = (application as MyApplication).appCompositionRoot

    val compositionRoot by lazy {
        ActivityCompositionRoot(appCompositionRoot)
    }
}
