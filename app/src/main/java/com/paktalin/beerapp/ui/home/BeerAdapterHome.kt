package com.paktalin.beerapp.ui.home

import android.view.View
import com.paktalin.beerapp.Beer
import com.paktalin.beerapp.addToFavorite
import com.paktalin.beerapp.removeFromFavorite

class BeerAdapterHome(
    beers: MutableList<Beer>,
    private val loadMoreBeer: () -> Unit
) : BeerAdapter(beers) {

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val beer = beers[position]
        with(holder) {
            if (position == itemCount - 1)
                loadMoreBeer() // reached the end, loading more

            if(beer.isFavorite) {
                buttonFavorite.alpha = 1f
                buttonFavorite.isSelected = true
            }
            buttonFavorite.visibility = View.VISIBLE
            buttonFavorite.setOnClickListener {
                if (!it.isSelected)
                    context?.addToFavorite(beer)
                else
                    context?.removeFromFavorite(beer)
                beer.isFavorite = !beer.isFavorite
                it.isSelected = !it.isSelected
                it.alpha = if (it.isSelected) 1f else 0.6f
            }
        }
    }
}