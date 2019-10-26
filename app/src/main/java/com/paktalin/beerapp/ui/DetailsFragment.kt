package com.paktalin.beerapp.ui

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.paktalin.beerapp.KEY_IMAGE_URL
import com.paktalin.beerapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detais.*
import kotlinx.android.synthetic.main.fragment_detais.view.*

class DetailsFragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detais, container, false)

        Picasso.with(context)
            .load("https://images.punkapi.com/v2/80.png")
            .into(view.image_beer)
        return view
    }
}