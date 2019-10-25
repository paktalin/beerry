package com.paktalin.beerapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.appyvet.materialrangebar.RangeBar
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paktalin.beerapp.BeerFilter
import com.paktalin.beerapp.R
import kotlinx.android.synthetic.main.fragment_filter.*
import kotlinx.android.synthetic.main.fragment_filter.view.*

private const val KEY_BEER_FILTER = "beer_filter"

class FilterFragment: BottomSheetDialogFragment() {
    private var onComplete: (BeerFilter) -> Unit = {}
    private var filter: BeerFilter? = null

    companion object {
        fun newInstance(beerFilter: BeerFilter?, onComplete: (BeerFilter) -> Unit): FilterFragment {
            val filterFragment = FilterFragment()
            filterFragment.arguments?.putSerializable(KEY_BEER_FILTER, beerFilter)
            filterFragment.arguments = Bundle().apply { putSerializable(KEY_BEER_FILTER, beerFilter) }
            filterFragment.onComplete = onComplete
            return filterFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        filter = arguments?.getSerializable(KEY_BEER_FILTER) as BeerFilter?
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_filter, container, false)
        setUpAbv(view)
        setUpIbu(view)
        setUpEbc(view)

        view.button_submit_filter.setOnClickListener {
            val range: (rangeBar: RangeBar) -> IntRange = {
                val rightIndex =
                    if (it.rightIndex == it.tickEnd.toInt()) Int.MAX_VALUE else it.rightIndex
                it.leftIndex..rightIndex
            }
            onComplete(
                BeerFilter(
                    range(view.abv_rangebar),
                    range(ibu_rangebar),
                    range(view.ebc_rangebar)
                )
            )
            this.dismiss()
        }

        return view
    }

    private fun setUpAbv(view: View) {
        setUpSpec(view.abv_rangebar, view.tv_abv_selection, filter?.abvRange) {l, r ->
            resources.getString(R.string.abv_selection, l, r)
        }
    }

    private fun setUpIbu(view: View) {
        setUpSpec(view.ibu_rangebar, view.tv_ibu_selection, filter?.ibuRange) {l, r ->
            resources.getString(R.string.ibu_selection, l, r)
        }
    }

    private fun setUpEbc(view: View) {
        setUpSpec(view.ebc_rangebar, view.tv_ebc_selection, filter?.ebcRange) { l, r ->
            resources.getString(R.string.ebc_selection, l, r) }
    }

    private fun setUpSpec(
        rangeBar: RangeBar,
        tvSelection: TextView,
        initialRange: IntRange?,
        text: (left: String, right: String) -> String
    ) {
        with(rangeBar) {
            initialRange?.apply { setRangePinsByIndices(first, if (last == Int.MAX_VALUE) tickEnd.toInt() else last) }
            val max = "${tickEnd.toInt()}+"
            tvSelection.text = text(leftPinValue, max)
            setPinTextFormatter { value -> if (value == tickEnd.toInt().toString()) max else value }
            setOnRangeBarChangeListener(changeListener { left, right ->
                tvSelection.text = text(left, right)
            })
        }
    }

    private fun changeListener(onChange: (left: String, right: String) -> Unit): RangeBar.OnRangeBarChangeListener {
        return object : RangeBar.OnRangeBarChangeListener {
            override fun onTouchEnded(rangeBar: RangeBar?) {}

            override fun onRangeChangeListener(
                rangeBar: RangeBar?,
                leftPinIndex: Int,
                rightPinIndex: Int,
                leftPinValue: String?,
                rightPinValue: String?
            ) {
                if (leftPinValue != null && rightPinValue != null)
                    onChange(leftPinValue, rightPinValue)
                else
                    onChange(leftPinIndex.toString(), rightPinIndex.toString())
            }

            override fun onTouchStarted(rangeBar: RangeBar?) {}
        }
    }
}