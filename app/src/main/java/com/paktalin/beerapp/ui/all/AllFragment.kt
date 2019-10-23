package com.paktalin.beerapp.ui.all

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
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
        root.recycler_view_all.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        root.recycler_view_all.addItemDecoration(
            MarginItemDecoration(
                resources.getDimension(R.dimen.default_padding).toInt()
            )
        )
        return root
    }

    private fun loadBeers() {
        val stringRequest = JsonArrayRequest(
            Request.Method.GET, "https://api.punkapi.com/v2/beers", null,
            Response.Listener<JSONArray> { response ->
                for (i in 0 until response.length()) {
                    val beer = Beer(response.getJSONObject(i))
                    beers.add(beer)
                }
                view?.recycler_view_all?.adapter = BeerAdapter(beers)
                println()
            },
            Response.ErrorListener { e ->
                println()
            })

        BackendVolley.instance?.addToRequestQueue(stringRequest, "")
    }
}