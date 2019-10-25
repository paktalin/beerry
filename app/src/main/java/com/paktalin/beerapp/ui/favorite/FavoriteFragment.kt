package com.paktalin.beerapp.ui.favorite

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.paktalin.beerapp.Beer
import com.paktalin.beerapp.R
import com.paktalin.beerapp.getFavoriteBeers
import com.paktalin.beerapp.ui.home.BeerAdapter
import kotlinx.android.synthetic.main.fragment_home.view.*

class FavoriteFragment : Fragment() {

    var beers = mutableListOf<Beer>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        beers = context.getFavoriteBeers()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_favorite, container, false)
        root.recycler_view_all.adapter = BeerAdapter(beers)
        return root
    }
}