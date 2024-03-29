package com.paktalin.beerapp.ui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.paktalin.beerapp.Beer
import com.paktalin.beerapp.R
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

const val KEY_BEER = "beer"

open class BeerAdapter(
    protected val beers: MutableList<Beer>
) : RecyclerView.Adapter<RecyclerViewHolder>() {

    protected var context: Context? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_beer, parent, false)
        return RecyclerViewHolder(view); }

    override fun getItemCount(): Int {
        return beers.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val beer = beers[position]
        with(holder) {
            progress.visibility = View.VISIBLE
            tvName.text = beer.name
            setSpec(tvAbvTitle, tvAbv, beer.abv?.formatAbv())
            setSpec(tvIbuTitle, tvIbu, beer.ibu?.format())
            setLayoutColor(this, beer)
            setImage(beer.imageUrl, holder)
            buttonFavorite.visibility = View.GONE
            touchView.setOnClickListener { openDetailsWithTransition(beer) }
        }
    }

    private fun RecyclerViewHolder.openDetailsWithTransition(beer: Beer) {
        imageView.transitionName = context?.resources?.getString(R.string.beer_image_transition_name)
        buttonFavorite.transitionName = context?.resources?.getString(R.string.favorite_transition_name)
        layoutSpecs.transitionName = context?.resources?.getString(R.string.color_transition_name)

        val extras = FragmentNavigatorExtras(
            imageView to imageView.transitionName,
            buttonFavorite to buttonFavorite.transitionName,
            layoutSpecs to layoutSpecs.transitionName
        )
        val args = Bundle().apply { putSerializable(KEY_BEER, beer) }
        (context as AppCompatActivity).findNavController(R.id.nav_host_fragment)
            .navigate(R.id.navigation_details, args, null, extras)
    }

    private fun setImage(imageUrl: String?, holder: RecyclerViewHolder) {
        Picasso.with(context)
            .load(imageUrl)
            .networkPolicy(NetworkPolicy.OFFLINE)
            .error(context?.getDrawable(R.drawable.beer_icon))
            .into(holder.imageView, object : Callback {
                override fun onSuccess() {
                    holder.progress.visibility = View.GONE
                }

                override fun onError() {
                    holder.progress.visibility = View.GONE
                    Picasso.with(context)
                        .load(imageUrl)
                        .error(context?.getDrawable(R.drawable.beer_icon))
                        .into(holder.imageView)
                }
            })
    }

    private fun setSpec(tvTitle: TextView, tv: TextView, string: String?) {
        tv.text = string
        tvTitle.visibility = if (string == null) View.GONE else View.VISIBLE
    }

    private fun setLayoutColor(holder: RecyclerViewHolder, beer: Beer) {
        val textColor: Int?
        val titleTextColor: Int?
        // if background is dark, set light text colors and higher contrast text color for labels

        val ebc = beer.ebc
        if (ebc == null || ebc > 33.0) {
            textColor = Color.WHITE
            titleTextColor = Color.BLACK
        } else {
            textColor = Color.BLACK
            titleTextColor = Color.BLACK
        }
        context?.let { context ->

            val layoutColors = context.resources.getIntArray(R.array.ebc_colors)
            val layoutColorId = when (beer.ebc) {
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
            holder.layoutSpecs.setBackgroundColor(layoutColorId)
            holder.tvAbv.setTextColor(textColor)
            holder.tvIbu.setTextColor(textColor)
            holder.tvAbvTitle.setTextColor(titleTextColor)
            holder.tvIbuTitle.setTextColor(titleTextColor)
            beer.colorSet = BeerColorSet(layoutColorId, textColor, titleTextColor)
        }
    }
}