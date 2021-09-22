package com.example.cats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BreedAdapter(private val breeds: List<BreedsItem>) :
    RecyclerView.Adapter<BreedAdapter.ViewHolder>() {

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
}
