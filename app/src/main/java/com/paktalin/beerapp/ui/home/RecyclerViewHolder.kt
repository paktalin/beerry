package com.paktalin.beerapp.ui.home

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.item_layout.view.*

class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvName: MaterialTextView = itemView.tv_beer_name
    val imageView: ImageView = itemView.image_beer
    val tvAbv: TextView = itemView.tv_abv
    val tvIbu: TextView = itemView.tv_ibu
    val tvAbvTitle: TextView = itemView.tv_abv_title
    val tvIbuTitle: TextView = itemView.tv_ibu_title
    val layoutSpecs: ConstraintLayout = itemView.layout_specs
}