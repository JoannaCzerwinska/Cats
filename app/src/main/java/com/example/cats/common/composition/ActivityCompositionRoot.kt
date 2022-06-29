package com.example.cats.common.composition

import com.example.cats.breeds.FetchBreedsUseCase

class ActivityCompositionRoot(
    private val appCompositionRoot: AppCompositionRoot
) {

    private val apiService get() = appCompositionRoot.apiService

    // get() always gets a new instance of the use case
    val fetchBreedsUseCase get() = FetchBreedsUseCase(apiService)
}
