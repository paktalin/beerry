package com.paktalin.beerapp.ui.all

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.paktalin.beerapp.Beer
import com.paktalin.beerapp.R
import com.paktalin.beerapp.server.BeerLoader
import kotlinx.android.synthetic.main.fragment_all.view.*

class AllFragment : Fragment() {

    private var beers = mutableListOf<Beer>()
    private var beerLoader: BeerLoader? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        beerLoader = BeerLoader()
        loadNewBeer()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_all, container, false)
        root.recycler_view_all.adapter = BeerAdapter(beers) { loadNewBeer() }
        root.button_filter.setOnClickListener {
            val filterFragment = FilterFragment { abvRange, ibuRange, ebcRange ->
                beerLoader = BeerLoader(abvRange, ibuRange, ebcRange)
                reloadBeer()
            }
            activity?.supportFragmentManager?.let { manager ->
                filterFragment.show(manager, filterFragment.tag)
            }
        }
        return root
    }

    private fun reloadBeer() {
        beers.clear()
        beerLoader?.loadBeers({ newBeers ->
            beers.addAll(0, newBeers)
            view?.recycler_view_all?.adapter?.notifyDataSetChanged()
        })

    }

    private fun loadNewBeer() {
        val itemsCount = beers.size
        val page = BeerLoader.nextPage(itemsCount)

        val onSuccess = { newBeers: MutableList<Beer> ->
            beers.addAll(itemsCount, newBeers)
            view?.recycler_view_all?.adapter?.notifyItemInserted(itemsCount)
        }
        beerLoader?.loadBeers({ newBeers -> onSuccess(newBeers) }, page)
    }
}

