package com.paktalin.beerapp.ui.all

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paktalin.beerapp.Beer
import com.paktalin.beerapp.R
import kotlinx.android.synthetic.main.item_layout.view.*

class BeerAdapter(private val beers: MutableList<Beer>): RecyclerView.Adapter<RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return RecyclerViewHolder(v);    }

    override fun getItemCount(): Int {
        return beers.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.itemView.tv_beer_title.text = beers[position].name
    }
}