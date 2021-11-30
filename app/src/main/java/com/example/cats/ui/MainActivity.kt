package com.example.cats.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cats.R
import com.example.cats.api.IApiService
import com.example.cats.model.BreedsItem
import com.example.cats.utils.DefaultCoroutineDispatcherProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var apiService: IApiService
    @Inject
    lateinit var defaultCoroutineDispatcherProvider: DefaultCoroutineDispatcherProvider

    // lateinit var will be set when onCreate is called (not when main activity is initialised)
    private lateinit var recyclerView: RecyclerView
    private lateinit var breedsAdapter: BreedAdapter

    private var breeds: ArrayList<BreedsItem> = arrayListOf()
    private var job: Job? = null

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.breedsList)

        breedsAdapter = BreedAdapter()

        // responsible for measuring and positioning item views
        recyclerView.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

        recyclerView.adapter = breedsAdapter

        getBreedNames()
    }

    override fun onStop() {
        super.onStop()
        job?.cancel()
    }

    private fun getBreedNames() {
        job = CoroutineScope(defaultCoroutineDispatcherProvider.ioDispatcher()).launch {
            val response = apiService.getBreedNames()

            withContext(defaultCoroutineDispatcherProvider.mainDispatcher()) {
                try {
                    if (response.isSuccessful && response.body() != null) {
                        val responseBody = response.body()!!
                        Log.d(TAG, responseBody.toString())

                        breeds.addAll(responseBody)
                        breedsAdapter.setAdapterData(breeds)
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
