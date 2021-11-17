package com.example.cats.utils

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CoroutineContextProvider @Inject constructor() {
    val main: CoroutineContext by lazy { Dispatchers.Main }
    val io: CoroutineContext by lazy { Dispatchers.IO }
}
