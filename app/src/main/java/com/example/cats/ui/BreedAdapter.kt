package com.example.cats.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cats.R
import com.example.cats.model.BreedsItem

class BreedAdapter() :
    RecyclerView.Adapter<BreedAdapter.ViewHolder>() {

    private var breeds: List<BreedsItem> = arrayListOf()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val breedName: TextView = itemView.findViewById(R.id.breedName)
//        val catImage: ImageView = view.findViewById(R.id.catImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.breed_display, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = breeds.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val breed = breeds[position]

        viewHolder.breedName.text = breed.name
//        viewHolder.catImage.visibility = View.VISIBLE
    }

    internal fun setAdapterData(newBreedsList: List<BreedsItem>){
        breeds = newBreedsList
        notifyDataSetChanged()
    }
}
