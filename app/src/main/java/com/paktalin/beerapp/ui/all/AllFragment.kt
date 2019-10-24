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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        BeerLoader().loadBeers { beers ->
            this.beers.addAll(0, beers)
            view?.recycler_view_all?.adapter?.notifyDataSetChanged()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_all, container, false)

        root.recycler_view_all.adapter = context?.let { BeerAdapter(beers, it) }
        root.button_filter.setOnClickListener {
            val bottomSheetFragment = BottomSheetFragment()
            activity?.supportFragmentManager?.let { manager ->
                bottomSheetFragment.show(manager, bottomSheetFragment.tag)
            }
        }
        return root
    }
}