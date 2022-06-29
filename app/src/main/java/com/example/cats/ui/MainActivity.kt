package com.example.cats.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.example.cats.api.IApiService
import com.example.cats.breeds.FetchBreedsUseCase
import com.example.cats.ui.common.activities.BaseActivity
import com.example.cats.utils.DefaultCoroutineDispatcherProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    @Inject
    lateinit var apiService: IApiService

    @Inject
    lateinit var defaultCoroutineDispatcherProvider: DefaultCoroutineDispatcherProvider

    // lateinit var will be set when onCreate is called (not when main activity is initialised)
    lateinit var breedsAdapter: BreedAdapter
    lateinit var fetchBreedsUseCase: FetchBreedsUseCase

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    var isDataLoaded = false

    companion object {
        const val TAG = "MainActivity Response Body"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        breedsAdapter = BreedAdapter(LayoutInflater.from(this), null)
        setContentView(breedsAdapter.rootView)

        fetchBreedsUseCase = compositionRoot.fetchBreedsUseCase
    }

    override fun onStart() {
        super.onStart()
        if (!isDataLoaded) {
            loadBreedsData()
        }
    }

    override fun onStop() {
        super.onStop()
        coroutineScope.coroutineContext.cancelChildren()
    }

    private fun loadBreedsData() {
        coroutineScope.launch {
            try {
                when (val response = fetchBreedsUseCase.getBreedNames()) {
                    is FetchBreedsUseCase.Result.Success -> {
                        breedsAdapter.bindBreeds(response.breedNames)
                        isDataLoaded = true
                    }
                    is FetchBreedsUseCase.Result.Failure -> showErrorMessage()
                }
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
        }
    }

    private fun showErrorMessage() {
        Toast.makeText(
            applicationContext,
            "Looks like something went wrong",
            Toast.LENGTH_SHORT
        ).show()
    }

//        private fun showCatImages() {
//        val responseBody = response.body()!!
//        Log.d(TAG, responseBody.toString())
//
//        // TODO display multiple images (not just the first one)
//        val imageUrl = responseBody[0].image.url
//        val breedName = responseBody[0].name
//
//        Picasso.with(baseContext).load(imageUrl).into(findViewById<ImageView>(R.id.catImage))
//        findViewById<TextView>(R.id.breedName).text = breedName
//    }
}
