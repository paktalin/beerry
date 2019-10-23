package com.paktalin.beerapp.ui.all

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paktalin.beerapp.Beer
import com.paktalin.beerapp.R
import com.squareup.picasso.Picasso

class BeerAdapter(
    private val beers: MutableList<Beer>,
    private val context: Context
) : RecyclerView.Adapter<RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return RecyclerViewHolder(v); }

    override fun getItemCount(): Int {
        return beers.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val beer = beers[position]
        holder.tvName.text = beer.name
        Picasso
            .with(context)
            .load(beer.imageUrl)
            .into(holder.image)
    }
}