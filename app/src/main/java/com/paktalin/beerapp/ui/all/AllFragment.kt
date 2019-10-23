package com.paktalin.beerapp.ui.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paktalin.beerapp.R
import kotlinx.android.synthetic.main.fragment_all.view.*

class AllFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_all, container, false)
        root.recycler_view_all.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        root.recycler_view_all.adapter = BeerAdapter()
        return root
    }
}