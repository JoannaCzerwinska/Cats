package com.example.cats.ui.common.activities

import androidx.appcompat.app.AppCompatActivity
import com.example.cats.MyApplication

open class BaseActivity: AppCompatActivity() {

    val compositionRoot get() = (application as MyApplication).appCompositionRoot
}
