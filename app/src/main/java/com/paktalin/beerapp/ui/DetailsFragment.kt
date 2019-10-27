package com.paktalin.beerapp.ui

import android.content.res.ColorStateList
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.paktalin.beerapp.Beer
import com.paktalin.beerapp.R
import com.paktalin.beerapp.addToFavorite
import com.paktalin.beerapp.removeFromFavorite
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detais.view.*
import kotlinx.android.synthetic.main.layout_brewers_tips.view.*
import kotlinx.android.synthetic.main.layout_characteristics.view.*
import kotlinx.android.synthetic.main.layout_food_pairing.view.*


class DetailsFragment : Fragment() {

    private lateinit var beer: Beer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        beer = arguments?.getSerializable(KEY_BEER) as Beer
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detais, container, false)
        with(view) {
            (activity as AppCompatActivity).setSupportActionBar(toolbar as Toolbar)
            collapsing_toolbar.title = beer.name
            Picasso.with(context).load(beer.imageUrl).into(image_beer)

            setUpColors(view)
            setUpButtonFavorite(button_add_to_favorite)
            setUpExpandButtons(view)
        }
        return view
    }

    private fun setUpExpandButtons(view: View) {
        with(view) {
            button_expand_characteristics.isSelected = true
            button_expand_characteristics.setExpandableButton(characteristics_expanded)
            button_expand_brewers_tips.setExpandableButton(brewers_tips_expanded)
            button_expand_food_pairing.setExpandableButton(food_pairing_expanded)
        }
    }

    private fun ImageButton.setExpandableButton(expandableView: View) {
        setOnClickListener {
            it.isSelected = !it.isSelected
            expandableView.visibility = if (it.isSelected) View.VISIBLE else View.GONE
            expandableView.parent.requestChildFocus(expandableView, expandableView)
        }
    }

    private fun setUpColors(view: View) {
        beer.colorSet?.apply {
            view.collapsing_toolbar.setExpandedTitleTextColor(ColorStateList.valueOf(textColor))
            view.collapsing_toolbar.setCollapsedTitleTextColor(textColor)
            view.view_color?.setBackgroundColor(backgroundColor)
            view.collapsing_toolbar.setContentScrimColor(backgroundColor)
        }
    }

    private fun setUpButtonFavorite(button: FloatingActionButton) {
        with(button) {
            isSelected = beer.isFavorite
            setOnClickListener {
                if (!isSelected)
                    context?.addToFavorite(beer)
                else
                    context?.removeFromFavorite(beer)
                beer.isFavorite = !beer.isFavorite
                isSelected = !isSelected
            }
        }
    }
}