package com.example.cats.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cats.BreedAdapter
import com.example.cats.R
import com.example.cats.api.IApiService
import com.example.cats.model.BreedsItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var apiService: IApiService

    // lateinit var will be set when onCreate is called (not when main activity is initialised)
    private lateinit var recyclerView: RecyclerView
    private lateinit var breedsAdapter: BreedAdapter

    private var breeds: ArrayList<BreedsItem> = arrayListOf()
    private var job: Job? = null

    companion object {
        const val BASE_URL = "https://api.thecatapi.com/"
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.breedsList)

        breedsAdapter = BreedAdapter(breeds)

        // responsible for measuring and positioning item views
        recyclerView.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

        recyclerView.adapter = breedsAdapter

        getBreedNames()
    }

    private fun getBreedNames() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = apiService.getBreedNames()

            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful && response.body() != null) {
                        val responseBody = response.body()!!
                        Log.d(TAG, responseBody.toString())

                        breeds.addAll(responseBody)
                        breedsAdapter.notifyDataSetChanged()
                    }
                } catch (e: Exception) {
                    showErrorMessage(e)
                    job?.cancel()
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