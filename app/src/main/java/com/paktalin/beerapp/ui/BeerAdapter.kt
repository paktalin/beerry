package com.paktalin.beerapp.ui

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.paktalin.beerapp.Beer
import com.paktalin.beerapp.R
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import java.text.DecimalFormat

open class BeerAdapter(
    protected val beers: MutableList<Beer>) : RecyclerView.Adapter<RecyclerViewHolder>() {

    protected var context: Context? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return RecyclerViewHolder(view); }

    override fun getItemCount(): Int {
        return beers.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val beer = beers[position]
        with(holder) {
            progress .visibility = View.VISIBLE
            tvName.text = beer.name
            setSpec(tvAbvTitle, tvAbv, beer.abv, "##.#'%'")
            setSpec(tvIbuTitle, tvIbu, beer.ibu, "###")
            setLayoutColor(beer.ebc, this)
            setImage(beer.imageUrl, holder)
            buttonFavorite.visibility = View.GONE
        }
    }

    private fun setImage(imageUrl: String, holder: RecyclerViewHolder) {
        Picasso.with(context)
            .load(imageUrl)
            .networkPolicy(NetworkPolicy.OFFLINE)
            .into(holder.imageView, object : Callback {
                override fun onSuccess() {
                    holder.progress.visibility = View.GONE
                }
                override fun onError() {
                    holder.progress.visibility = View.GONE
                    Picasso.with(context)
                        .load(imageUrl)
                        .into(holder.imageView)
                }
            })
    }

    private fun setSpec(tvTitle: TextView, tv: TextView, value: Double?, pattern: String) {
        if (value == null || value.isNaN()) {
            // remove text and its label
            tv.text = null
            tvTitle.visibility = View.GONE
        } else {
            tv.text = DecimalFormat(pattern).format(value)
            tvTitle.visibility = View.VISIBLE
        }
    }

    private fun setLayoutColor(ebc: Double?, holder: RecyclerViewHolder) {
        val textColor: Int?
        val titleTextColor: Int?
        // if background is dark, set light text colors and higher contrast text color for labels
        if (ebc == null || ebc.isNaN() || ebc > 33.0) {
            textColor = Color.WHITE
            titleTextColor = Color.BLACK
        } else {
            textColor = Color.BLACK
            titleTextColor = Color.BLACK
        }
        context?.let { context ->

            val layoutColors = context.resources.getIntArray(R.array.ebc_colors)
            val layoutColor = when (ebc) {
                null -> ContextCompat.getColor(context, R.color.colorSecondaryLight)
                in 0.0..4.0 -> layoutColors[0]
                in 4.0..6.0 -> layoutColors[1]
                in 6.0..8.0 -> layoutColors[2]
                in 8.0..12.0 -> layoutColors[3]
                in 12.0..16.0 -> layoutColors[4]
                in 16.0..20.0 -> layoutColors[5]
                in 20.0..26.0 -> layoutColors[6]
                in 26.0..33.0 -> layoutColors[7]
                in 33.0..39.0 -> layoutColors[8]
                in 39.0..47.0 -> layoutColors[9]
                in 47.0..57.0 -> layoutColors[10]
                in 57.0..69.0 -> layoutColors[11]
                in 69.0..Double.POSITIVE_INFINITY -> layoutColors[12]
                else -> ContextCompat.getColor(context, R.color.colorSecondaryLight)
            }
            holder.layoutSpecs.setBackgroundColor(layoutColor)
            holder.tvAbv.setTextColor(textColor)
            holder.tvIbu.setTextColor(textColor)
            holder.tvAbvTitle.setTextColor(titleTextColor)
            holder.tvIbuTitle.setTextColor(titleTextColor)
        }
    }
}