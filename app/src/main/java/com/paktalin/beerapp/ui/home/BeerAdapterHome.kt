package com.paktalin.beerapp.ui.home

import android.view.View
import com.paktalin.beerapp.*
import com.paktalin.beerapp.ui.BeerAdapter
import com.paktalin.beerapp.ui.RecyclerViewHolder

class BeerAdapterHome(
    beers: MutableList<Beer>,
    private val loadMoreBeer: () -> Unit) : BeerAdapter(beers) {

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val beer = beers[position]
        with(holder) {
            if (position == itemCount - 1)
                loadMoreBeer() // reached the end, loading more

            with(buttonFavorite) {
                isSelected = beer.isFavorite
                alpha = if(beer.isFavorite) 1f else 0.6f
                visibility = View.VISIBLE
                setOnClickListener {
                    if (!isSelected)
                        context?.addToFavorite(beer)
                    else
                        context?.removeFromFavorite(beer)
                    beer.isFavorite = !beer.isFavorite
                    isSelected = !isSelected
                    alpha = if (isSelected) 1f else 0.6f
                }
            }
        }
    }
}