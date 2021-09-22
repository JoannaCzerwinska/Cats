package com.example.cats

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    // lateinit var will be set when onCreate is called (not when main activity is initialised)
    private lateinit var recyclerView: RecyclerView
    private lateinit var breedsAdapter: BreedAdapter
    private lateinit var api: ApiService

    private var breeds: ArrayList<BreedsItem> = arrayListOf()

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

        setUpRetrofit()
        getBreedNames()
    }

    private fun setUpRetrofit(){
        api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    private fun getBreedNames() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = api.getBreedNames()

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