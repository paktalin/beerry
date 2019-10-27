package com.paktalin.beerapp.ui.favorite

import android.content.Context
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.paktalin.beerapp.Beer
import com.paktalin.beerapp.R
import com.paktalin.beerapp.getFavoriteBeers
import com.paktalin.beerapp.ui.BeerAdapter
import kotlinx.android.synthetic.main.fragment_favorite.view.*
import kotlinx.android.synthetic.main.fragment_home.view.recycler_view_all
import kotlinx.android.synthetic.main.layout_no_favorites.view.*

class FavoriteFragment : Fragment() {

    var beers = mutableListOf<Beer>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        beers = context.getFavoriteBeers().apply { forEach { beer -> beer.isFavorite = true } }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        view.recycler_view_all.adapter = BeerAdapter(beers)

        if(beers.isEmpty()) {
            view.layout_no_favorites.visibility = View.VISIBLE
            view.layout_no_favorites.button_browse.setOnClickListener { activity?.findNavController(R.id.nav_host_fragment)?.navigate(R.id.navigation_home) }
        } else
            view.layout_no_favorites.visibility = View.GONE

        return view
    }
}