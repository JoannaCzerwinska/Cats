package com.example.cats.di

import com.example.cats.api.IApiService
import com.example.cats.utils.CoroutineDispatcherProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun provideIApiService() : IApiService

    fun provideCoroutineDispatcherProvider(): CoroutineDispatcherProvider
}
