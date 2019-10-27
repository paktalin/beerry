package com.paktalin.beerapp.ui.beer_details

import android.content.res.ColorStateList
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.marginStart
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.paktalin.beerapp.Beer
import com.paktalin.beerapp.R
import com.paktalin.beerapp.addToFavorite
import com.paktalin.beerapp.removeFromFavorite
import com.paktalin.beerapp.ui.KEY_BEER
import com.paktalin.beerapp.ui.format
import com.paktalin.beerapp.ui.formatAbv
import com.paktalin.beerapp.ui.formatUnits
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_details.view.*
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
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        with(view) {
            (activity as AppCompatActivity).setSupportActionBar(toolbar as Toolbar)
            image_beer.post { setUpCollapsingToolbarMargin() }
            button_add_to_favorite.setUpButtonFavorite()
            setUpImage()
            setUpColors()
            setUpExpandButtons()
            setUpBeerTextData()
        }
        return view
    }

    private fun View.setUpImage() {
        Picasso.with(context).load(beer.imageUrl).error(context?.getDrawable(R.drawable.beer_icon))
            .into(image_beer)
    }

    private fun View.setUpCollapsingToolbarMargin() {
        val marginStart = image_beer.width + 2 * image_beer.marginStart
        collapsing_toolbar.expandedTitleMarginStart = marginStart
        parentFragment?.startPostponedEnterTransition()
    }

    private fun View.setUpBeerTextData() {
        collapsing_toolbar.title = beer.name
        val unspecified = context.resources.getString(R.string.unspecified)
        tv_abv_value.text = beer.abv?.formatAbv() ?: unspecified
        tv_ibu_value.text = beer.ibu?.format() ?: unspecified
        tv_ebc_value.text = beer.ebc?.format() ?: unspecified
        tv_first_brewed.text = beer.firstBrewed ?: unspecified
        tv_fg_value.text = beer.fg?.format() ?: unspecified
        tv_og_value.text = beer.og?.format() ?: unspecified
        tv_srm_value.text = beer.srm?.format() ?: unspecified
        tv_ph_value.text = beer.ph?.format() ?: unspecified
        tv_attenuation_value.text = beer.attenuation?.format() ?: unspecified
        tv_volume_value.text =
            beer.volume?.format()?.let { "$it ${beer.volumeUnit?.formatUnits().orEmpty()}" }
                ?: unspecified
        tv_boil_volume_value.text =
            beer.boilVolume?.format()?.let { "$it ${beer.boilVolumeUnit?.formatUnits().orEmpty()}" }
                ?: unspecified

        tv_description.text = beer.description
        brewers_tips_expanded.text = beer.brewersTips

        if (beer.tagline != null)
            tv_tagline.text = beer.tagline?.trim('.')
        else quote_left.visibility = View.INVISIBLE

        beer.foodPairings?.let { foodPairings ->
            food_pairing_expanded.adapter = FoodPairingsAdapter(foodPairings)
            food_pairing_expanded.adapter?.notifyDataSetChanged()
        }
    }

    private fun View.setUpExpandButtons() {
        button_expand_characteristics.isSelected = true
        button_expand_characteristics.setExpandableButton(characteristics_expanded)
        button_expand_brewers_tips.setExpandableButton(brewers_tips_expanded)
        button_expand_food_pairing.setExpandableButton(food_pairing_expanded)
    }

    private fun View.setExpandableButton(expandableView: View) {
        setOnClickListener {
            it.isSelected = !it.isSelected
            expandableView.visibility = if (it.isSelected) View.VISIBLE else View.GONE
            expandableView.parent.requestChildFocus(expandableView, expandableView)
        }
    }

    private fun View.setUpColors() {
        beer.colorSet?.apply {
            collapsing_toolbar.setExpandedTitleTextColor(ColorStateList.valueOf(textColor))
            collapsing_toolbar.setCollapsedTitleTextColor(textColor)
            view_color?.setBackgroundColor(backgroundColor)
            collapsing_toolbar.setContentScrimColor(backgroundColor)
        }
    }

    private fun FloatingActionButton.setUpButtonFavorite() {
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