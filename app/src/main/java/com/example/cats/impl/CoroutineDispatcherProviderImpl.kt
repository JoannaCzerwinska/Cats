package com.example.cats.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DefaultCoroutineDispatcherProvider @Inject constructor(): CoroutineDispatcherProvider {
    override fun mainDispatcher(): CoroutineDispatcher {
        return Dispatchers.Main
    }

    override fun ioDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}
