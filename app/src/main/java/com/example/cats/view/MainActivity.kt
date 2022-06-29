package com.example.cats.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cats.R
import com.example.cats.contract.IBreedsView
import com.example.cats.model.Breed
import com.example.cats.model.BreedsByName
import com.example.cats.model.VoteDeletedResponse
import com.example.cats.model.VoteResponse
import com.example.cats.presenter.BreedsPresenter
import retrofit2.Response


class MainActivity : AppCompatActivity(), IBreedsView {
    private lateinit var breedsPresenter : BreedsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        breedsPresenter = BreedsPresenter(this)

        val voteButton : Button = findViewById(R.id.voteButton)
        voteButton.setOnClickListener{
            breedsPresenter.uploadVote(imageId = "asf2", value = 1)
        }

        retrieveBreedNames()
    }

    override fun onDestroy(){
        super.onDestroy()
        breedsPresenter.destroyView()
    }

    private fun retrieveBreedNames(){
//        breedsPresenter.deleteVote("1")
        breedsPresenter.getBreeds()
//        breedsPresenter.getInfoByBreedName("Siberian")
    }

    override fun displayBreeds(breeds: Response<List<Breed>>) {
        val breedName = findViewById<TextView>(R.id.breedName)
        val breedDescription = findViewById<TextView>(R.id.breedDescription)

        val body = breeds.body()!!
        val stringBuilder = StringBuilder()

        for (breed : Breed in body){
            stringBuilder.append(breed.name)
            stringBuilder.append("\n")
            breedName.text = stringBuilder

            stringBuilder.append(breed.description)
            stringBuilder.append("\n")
            stringBuilder.append("\n")
            breedDescription.text = stringBuilder
        }
    }

    override fun displaySpecificBreedInfo(breeds: Response<List<BreedsByName>>) {
        val breedName = findViewById<TextView>(R.id.breedName)
        val breedDescription = findViewById<TextView>(R.id.breedDescription)

        val body = breeds.body()!!
        breedName.text = body[0].name
        breedDescription.text = body[0].origin
    }

    override fun displayError(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    override fun displaySuccessfulVote(voteResponse: Response<VoteResponse>) {
        Toast.makeText(applicationContext, voteResponse.body()!!.message, Toast.LENGTH_LONG).show()

        val myIntent = Intent(this@MainActivity, NewActivity::class.java)
        this@MainActivity.startActivity(myIntent)
    }

    override fun displaySuccessfulDeletedVote(voteDeletedResponse: Response<VoteDeletedResponse>) {
        Toast.makeText(applicationContext, voteDeletedResponse.body()!!.message, Toast.LENGTH_LONG).show()
    }
}
