package com.example.cats.utils

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatcherProvider {
    fun mainDispatcher(): CoroutineDispatcher
    fun ioDispatcher(): CoroutineDispatcher
}
