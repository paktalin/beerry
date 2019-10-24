package com.paktalin.beerapp.ui.all

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paktalin.beerapp.Beer
import com.paktalin.beerapp.R
import com.squareup.picasso.Picasso
import java.text.DecimalFormat

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
        holder.tvAbv.text = if (beer.abv != null) DecimalFormat("##.#'%'").format(beer.abv) else {
            holder.tvAbvTitle.gone(); null
        }
        holder.tvIbu.text = if (beer.ibu != null) DecimalFormat("###").format(beer.ibu) else {
            holder.tvIbuTitle.gone(); null
        }
        Picasso
            .with(context)
            .load(beer.imageUrl)
            .into(holder.image)
    }

    private fun View.gone() {
        visibility = View.GONE
    }
}