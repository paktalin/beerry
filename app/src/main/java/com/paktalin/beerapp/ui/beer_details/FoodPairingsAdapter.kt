package com.paktalin.beerapp.ui.beer_details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paktalin.beerapp.R
import kotlinx.android.synthetic.main.item_food_pairing.view.*

class FoodPairingsAdapter(private val pairings: MutableList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food_pairing, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pairings.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.food_pairing_item.text = pairings[position]
        holder.itemView.divider.visibility = if (position == itemCount-1) View.GONE else View.VISIBLE
    }

    private inner class RecyclerViewHolder(view: View): RecyclerView.ViewHolder(view)
}