package com.example.cats.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cats.api.IApiService
import com.example.cats.utils.DefaultCoroutineDispatcherProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BreedAdapterViewMvc.Listener {

    @Inject
    lateinit var apiService: IApiService

    @Inject
    lateinit var defaultCoroutineDispatcherProvider: DefaultCoroutineDispatcherProvider

    // lateinit var will be set when onCreate is called (not when main activity is initialised)
    private lateinit var breedsAdapterViewMvc: BreedAdapterViewMvc

    private var job: Job? = null
    private var isDataLoaded = false

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        breedsAdapterViewMvc = BreedAdapterViewMvc(LayoutInflater.from(this), null)
        setContentView(breedsAdapterViewMvc.rootView)
    }

    override fun onStart() {
        super.onStart()
        breedsAdapterViewMvc.registerListener(this)

        if (!isDataLoaded) {
            getBreedNames()
        }
    }

    override fun onStop() {
        super.onStop()
        breedsAdapterViewMvc.unregisterListener(this)
        job?.cancel()
    }

    private fun getBreedNames() {
        job = CoroutineScope(defaultCoroutineDispatcherProvider.ioDispatcher()).launch {
            withContext(defaultCoroutineDispatcherProvider.mainDispatcher()) {
                try {
                    val response = apiService.getBreedNames()
                    if (response.isSuccessful && response.body() != null) {
                        breedsAdapterViewMvc.bindBreeds(response.body()!!)
                        isDataLoaded = true
                    }
                } catch (e: Exception) {
                    showErrorMessage(e)
                }
            }
        }
    }

    private fun showErrorMessage(e: Exception) {
        Toast.makeText(
            applicationContext,
            "Looks like something went wrong",
            Toast.LENGTH_SHORT
        ).show()

        Log.e(TAG, e.toString())
    }

    override fun loadBreedsData() {
        getBreedNames()
    }

    //    private fun showCatImages() {
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
