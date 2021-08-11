package com.example.cats

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    companion object {
        const val BASE_URL = "https://api.thecatapi.com/"
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

        val retrofitData = retrofitBuilder.getBreedNames()

        retrofitData.enqueue(object : Callback<List<BreedsItem>?> {
            override fun onResponse(
                call: Call<List<BreedsItem>?>,
                response: Response<List<BreedsItem>?>
            ) {
                val responseBody = response.body()!!
                val myStringBuilder = StringBuilder()

                for (data in responseBody){
                    myStringBuilder.append(data.name)
                    myStringBuilder.append("\n")
                }

                // use synthetic binding; if not enabled add 'kotlin-android-extensions' - HAS BEEN DEPRECATED
                findViewById<TextView>(R.id.breedName).text = myStringBuilder
            }

            override fun onFailure(call: Call<List<BreedsItem>?>, t: Throwable) {
                Log.d(TAG, "onFailure" + t.message)
            }
        })
    }
}