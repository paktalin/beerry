package com.paktalin.beerapp.ui.all

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.paktalin.beerapp.Beer
import com.paktalin.beerapp.BeerFilter
import com.paktalin.beerapp.R
import com.paktalin.beerapp.server.BeerLoader
import kotlinx.android.synthetic.main.fragment_all.view.*

class AllFragment : Fragment() {

    private var beers = mutableListOf<Beer>()
    private var beerFilter: BeerFilter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loadNewBeer()
    }
    // TODO restore state on rotation

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_all, container, false)
        root.recycler_view_all.adapter = BeerAdapter(beers) { loadNewBeer() }
        root.button_filter.setOnClickListener { showFilterDialog() }
        return root
    }

    private fun showFilterDialog() {
        val filterFragment = FilterFragment(beerFilter) { beerFilter ->
            this.beerFilter = beerFilter
            reloadBeer()
        }

        activity?.supportFragmentManager?.let { manager ->
            filterFragment.show(manager, filterFragment.tag)
        }
    }

    private fun reloadBeer() {
        beers.clear()
        BeerLoader(beerFilter).loadBeers({ newBeers ->
            beers.addAll(0, newBeers)
            view?.recycler_view_all?.adapter?.notifyDataSetChanged()
        })

    }

    private fun loadNewBeer() {
        val itemsCount = beers.size

        val page = BeerLoader.nextPage(itemsCount)
        if (itemsCount != 0 && itemsCount < (page-1)*BeerLoader.BEER_PER_PAGE)
            return

        val onSuccess = { newBeers: MutableList<Beer> ->
            beers.addAll(itemsCount, newBeers)
            view?.recycler_view_all?.adapter?.notifyItemInserted(itemsCount)
        }
        BeerLoader(beerFilter).loadBeers({ newBeers -> onSuccess(newBeers) }, page)
    }
}

