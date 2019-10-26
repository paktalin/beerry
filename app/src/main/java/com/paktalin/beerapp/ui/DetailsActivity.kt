package com.paktalin.beerapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.paktalin.beerapp.KEY_IMAGE_URL
import com.paktalin.beerapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_beer_detais.*

class DetailsActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beer_detais)


        val imageUrl = intent.getStringExtra(KEY_IMAGE_URL)

        Picasso.with(this)
            .load(imageUrl)
            .into(image_beer)
    }
}