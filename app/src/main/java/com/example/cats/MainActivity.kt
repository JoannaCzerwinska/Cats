package com.example.cats

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    companion object {
        const val BASE_URL = "https://api.thecatapi.com/"
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getBreedName()
    }

    private fun getBreedName() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiService::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = retrofitBuilder.getBreedNames().awaitResponse()
                if (response.isSuccessful){
                    val responseData = response.body()!!
                    val myStringBuilder = StringBuilder()
                    Log.d(TAG, responseData.toString())

                    withContext(Dispatchers.Main){
                        for (data in responseData){
                            myStringBuilder.append(data.name)
                            myStringBuilder.append("\n")
                        }

                        findViewById<TextView>(R.id.breedName).text = myStringBuilder
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        applicationContext,
                        "Looks like something went wrong",
                        Toast.LENGTH_SHORT
                    )
                }
            }
        }
    }
}