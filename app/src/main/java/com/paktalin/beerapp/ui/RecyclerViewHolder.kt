package com.paktalin.beerapp.ui

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.item_beer.view.*

class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvName: MaterialTextView = itemView.tv_beer_name
    val imageView: ImageView = itemView.image_beer
    val tvAbv: TextView = itemView.tv_abv
    val tvIbu: TextView = itemView.tv_ibu
    val tvAbvTitle: TextView = itemView.tv_abv_title
    val tvIbuTitle: TextView = itemView.tv_ibu_title
    val layoutSpecs: ConstraintLayout = itemView.layout_specs
    val buttonFavorite: ImageButton = itemView.button_add_to_favorite
    val progress: ProgressBar = itemView.progress
    val touchView: MaterialCardView = itemView.touch_view
}