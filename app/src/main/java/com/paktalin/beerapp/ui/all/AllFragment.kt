package com.paktalin.beerapp.ui.all

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.volley.toolbox.JsonArrayRequest
import com.paktalin.beerapp.Beer
import com.paktalin.beerapp.R
import com.paktalin.beerapp.server.BackendVolley
import com.paktalin.beerapp.ui.MarginItemDecoration
import kotlinx.android.synthetic.main.fragment_all.view.*
import org.json.JSONArray

class AllFragment : Fragment() {

    var beers = mutableListOf<Beer>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loadBeers()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_all, container, false)

        with(root.recycler_view_all) {
            addItemDecoration(
                MarginItemDecoration(
                    resources.getDimension(R.dimen.default_padding).toInt(),
                    2
                )
            )
            adapter = context?.let { BeerAdapter(beers, it) }
        }
        return root
    }

    private fun loadBeers() {
        val url = "https://api.punkapi.com/v2/beers"
        val request = JsonArrayRequest(url,
            { processResponse(it) },
            {
                val data = BackendVolley.instance?.cache()?.get(url)?.data
                data?.let { processResponse(JSONArray(String(data))) }
            }
        )
        BackendVolley.instance?.addToRequestQueue(request, url)
    }

    private fun processResponse(jsonArray: JSONArray) {
        for (i in 0 until jsonArray.length()) {
            val beer = Beer(jsonArray.getJSONObject(i))
            beers.add(beer)
        }
        view?.recycler_view_all?.adapter?.notifyDataSetChanged()
    }
}