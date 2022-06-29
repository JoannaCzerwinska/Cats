package com.example.cats.presenter

import android.util.Log
import com.example.cats.contract.IBreedsPresenter
import com.example.cats.contract.IBreedsView
import com.example.cats.model.Vote
import com.example.cats.services.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class BreedsPresenter(breedsView : IBreedsView) : IBreedsPresenter {
    private var view : IBreedsView = breedsView
    private var job: Job? = null
    var apiService = RetrofitInstance.api

    companion object {
        const val TAG = "BreedsPresenter"
    }

    override fun getBreeds() {
        job = CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = apiService.getBreedNames()
                val body = response.body()

                if (response.isSuccessful && body != null) {
                    view.displayBreeds(response)
                } else {
                    handleErrors(response.code(), response.message())
                }
            } catch (e: Exception) {
                Log.e(TAG, e.message.toString())
            }
        }
    }

    override fun getInfoByBreedName(searchString: String) {
        job = CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = apiService.getInfoByBreedName(searchString)
                val body = response.body()

                if (response.isSuccessful && body != null) {
                    view.displaySpecificBreedInfo(response)
                } else {
                    handleErrors(response.code(), response.message())
                }
            } catch (e: Exception) {
                Log.e(TAG, e.message.toString())
            }
        }
    }

    override fun uploadVote(imageId : String, value : Int) {
        job = CoroutineScope(Dispatchers.Main).launch{
            try {
                val response = apiService.uploadAVote(Vote(imageId = imageId, value = value))

                if (response.isSuccessful && response.body() != null){
                    view.displaySuccessfulVote(response)
                } else {
                    handleErrors(response.code(), response.message())
                }
            } catch (e: Exception) {
                Log.e(TAG, e.message.toString())
            }
        }
    }

    override fun deleteVote(voteId: String) {
        job = CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = apiService.deleteVote(voteId)
                Log.e(TAG, response.toString())

                if (response.isSuccessful && response.body() != null){
                    view.displaySuccessfulDeletedVote(response)
                } else {
                    handleErrors(response.code(), response.message())
                }
            } catch (e : Exception){
                Log.e(TAG, e.message.toString())
            }
        }
    }

    private fun handleErrors(code : Int, message : String) {
        if (code == 404) {
            view.displayError("Error code $code")
        } else {
            view.displayError("Other error: $message")
        }
    }

    override fun destroyView() {
        job?.cancel()
    }
}
