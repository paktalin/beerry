package com.paktalin.beerapp.ui

import android.content.res.ColorStateList
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.paktalin.beerapp.Beer
import com.paktalin.beerapp.R
import com.paktalin.beerapp.addToFavorite
import com.paktalin.beerapp.removeFromFavorite
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detais.view.*

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
        (activity as AppCompatActivity).setSupportActionBar(view.toolbar as Toolbar)

        view.collapsing_toolbar.title = beer.name

        beer.colorSet?.apply {
            view.collapsing_toolbar.setExpandedTitleTextColor(ColorStateList.valueOf(textColor))
            view.collapsing_toolbar.setCollapsedTitleTextColor(textColor)
            view.view_color?.setBackgroundColor(backgroundColor)
            view.collapsing_toolbar.setContentScrimColor(backgroundColor)
        }

        with(view.button_add_to_favorite) {
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

        Picasso.with(context)
            .load(beer.imageUrl)
            .into(view.image_beer)
        return view
    }
}