package com.paktalin.beerapp.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.paktalin.beerapp.Beer
import com.paktalin.beerapp.R
import com.paktalin.beerapp.getFavoriteBeers
import com.paktalin.beerapp.server.BeerLoader
import com.paktalin.beerapp.server.BeerLoader.Companion.BEER_PER_PAGE
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.layout_no_connection.view.*
import kotlinx.android.synthetic.main.layout_no_matches.view.*

private const val KEY_BEER_FILTER = "beer_filter"
private const val KEY_IS_LAST_PAGE = "is_last_page"

class HomeFragment : Fragment() {
    private var beers = mutableListOf<Beer>()
    private var beerFilter: BeerFilter? = null
    private var isLastPage = false
    private var favoriteBeers = mutableListOf<Beer>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        favoriteBeers = context.getFavoriteBeers()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        savedInstanceState?.restoreState()
        reloadBeer()
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        root.recycler_view_all.adapter = BeerAdapterHome(beers) { loadMoreBeer() }
        root.button_filter.setOnClickListener { showFilterDialog() }
        return root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY_BEER_FILTER, beerFilter)
        outState.putBoolean(KEY_IS_LAST_PAGE, isLastPage)
    }

    private fun Bundle.restoreState() {
        beerFilter = getSerializable(KEY_BEER_FILTER) as BeerFilter?
        isLastPage = getBoolean(KEY_IS_LAST_PAGE, false)
    }

    private fun showFilterDialog() {
        val filterFragment = FilterFragment.newInstance(beerFilter) { beerFilter ->
            this.beerFilter = beerFilter
            reloadBeer()
        }
        activity?.supportFragmentManager?.let { manager ->
            filterFragment.show(manager, filterFragment.tag)
        }
        view?.progress?.visibility = View.GONE
    }

    private fun reloadBeer() {
        isLastPage = false
        beers.clear()
        view?.recycler_view_all?.adapter?.notifyDataSetChanged()
        view?.progress?.visibility = View.VISIBLE

        BeerLoader(beerFilter).loadBeers({ newBeers ->
            view?.progress?.visibility = View.GONE
            if (newBeers.size < BEER_PER_PAGE)
                isLastPage = true
            if (newBeers.size == 0) {
                view?.layout_no_matches?.visibility = View.VISIBLE
                layout_no_matches?.button_filter_again?.setOnClickListener { showFilterDialog() }
                view?.recycler_view_all?.adapter?.notifyDataSetChanged()
                return@loadBeers
            }
            view?.layout_no_matches?.visibility = View.GONE
            onBeerLoaded(newBeers, 0)
        }, {onRequestFail()}, favoriteBeers)
    }

    private fun onRequestFail() {
        view?.progress?.visibility = View.GONE
        view?.layout_no_connection?.visibility = View.VISIBLE
        layout_no_connection?.button_back?.setOnClickListener {
            view?.layout_no_connection?.visibility = View.GONE
            beerFilter = null
            reloadBeer()
        }
    }

    private fun loadMoreBeer() {
        if (isLastPage) return

        val itemsCount = beers.size
        val page = BeerLoader.nextPage(itemsCount)
        BeerLoader(beerFilter).loadBeers(
            { newBeers -> onBeerLoaded(newBeers, itemsCount) },
            {onRequestFail()},
            favoriteBeers,
            page
        )
    }

    private fun onBeerLoaded(newBeers: MutableList<Beer>, index: Int) {
        beers.addAll(index, newBeers)
        view?.recycler_view_all?.adapter?.notifyItemInserted(index)
    }
}