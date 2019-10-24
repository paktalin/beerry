package com.paktalin.beerapp.ui.all

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
        // set abv
        if (beer.abv == null || beer.abv.isNaN()) {
            // remove text and its label
            holder.tvAbv.text = null
            holder.tvAbvTitle.visibility = View.GONE
        } else {
            holder.tvAbv.text = DecimalFormat("##.#'%'").format(beer.abv)
            holder.tvAbvTitle.visibility = View.VISIBLE
        }
        // set ibu
        if (beer.ibu == null || beer.ibu.isNaN()) {
            // remove text and its label
            holder.tvIbu.text = null
            holder.tvIbuTitle.visibility = View.GONE
        } else {
            holder.tvIbu.text = DecimalFormat("###").format(beer.ibu)
            holder.tvIbuTitle.visibility = View.VISIBLE
        }
        setLayoutColor(beer.ebc, holder)
        // TODO cache image
        Picasso
            .with(context)
            .load(beer.imageUrl)
            .into(holder.image)
    }

    private fun setLayoutColor(ebc: Double?, holder: RecyclerViewHolder) {
        val textColor: Int?
        val titleTextColor: Int?
        if (ebc == null || ebc.isNaN() || ebc > 33.0) {
            textColor = Color.WHITE
            titleTextColor = Color.BLACK
        } else {
            textColor = ContextCompat.getColor(context, R.color.colorBackground)
            titleTextColor = ContextCompat.getColor(context, R.color.colorBackground)
        }
        val layoutColors = context.resources.getIntArray(R.array.ebc_colors)
        val layoutColor = when(ebc) {
            null                                -> ContextCompat.getColor(context, R.color.colorNoData)
            in 0.0..4.0                         -> layoutColors[0]
            in 4.0..6.0                         -> layoutColors[1]
            in 6.0..8.0                         -> layoutColors[2]
            in 8.0..12.0                        -> layoutColors[3]
            in 12.0..16.0                       -> layoutColors[4]
            in 16.0..20.0                       -> layoutColors[5]
            in 20.0..26.0                       -> layoutColors[6]
            in 26.0..33.0                       -> layoutColors[7]
            in 33.0..39.0                       -> layoutColors[8]
            in 39.0..47.0                       -> layoutColors[9]
            in 47.0..57.0                       -> layoutColors[10]
            in 57.0..69.0                       -> layoutColors[11]
            in 69.0..Double.POSITIVE_INFINITY   -> layoutColors[12]
            else -> ContextCompat.getColor(context, R.color.colorNoData)
        }
        holder.layoutSpecs.setBackgroundColor(layoutColor)
        holder.tvAbv.setTextColor(textColor)
        holder.tvIbu.setTextColor(textColor)
        holder.tvAbvTitle.setTextColor(titleTextColor)
        holder.tvIbuTitle.setTextColor(titleTextColor)
    }
}