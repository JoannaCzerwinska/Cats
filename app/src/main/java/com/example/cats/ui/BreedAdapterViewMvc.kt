package com.example.cats.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cats.R
import com.example.cats.model.BreedsItem

class BreedAdapterViewMvc(
    layoutInflater: LayoutInflater,
    parent: ViewGroup?
) {

    interface Listener {
        fun loadBreedsData()
    }

    private val recyclerView: RecyclerView
    private val breedsAdapter: BreedAdapter
    private val listeners = HashSet<Listener>()
    private val context: Context get() = rootView.context

    val rootView: View = layoutInflater.inflate(R.layout.activity_main, parent, false)

    init {

        recyclerView = findViewById(R.id.breedsList)
        breedsAdapter = BreedAdapter()

        // responsible for measuring and positioning item views
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        for (listener in listeners) {
            listener.loadBreedsData()
        }

        recyclerView.adapter = breedsAdapter
    }

    fun bindBreeds(breeds: List<BreedsItem>){
        breedsAdapter.setAdapterData(breeds)
    }

    fun registerListener(listener: Listener) {
        listeners.add(listener)
    }

    fun unregisterListener(listener: Listener) {
        listeners.remove(listener)
    }

    fun <T : View?> findViewById(@IdRes id: Int): T {
        return rootView.findViewById<T>(id)
    }

    class BreedAdapter :
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

        internal fun setAdapterData(newBreedsList: List<BreedsItem>) {
            breeds = ArrayList(newBreedsList)
            notifyDataSetChanged()
        }
    }
}
