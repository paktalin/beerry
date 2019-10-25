package com.paktalin.beerapp.ui.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appyvet.materialrangebar.RangeBar
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paktalin.beerapp.R
import kotlinx.android.synthetic.main.fragment_filter.view.*

class FilterFragment : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_filter, container, false)

        with(view.abv_rangebar) {
            val max = "${tickEnd.toInt()}+"
            view.tv_abv_selection.text = resources.getString(R.string.abv_selection, tickStart.toInt().toString(), max)
            setPinTextFormatter { string -> if (string == tickEnd.toInt().toString()) max else string }
            setOnRangeBarChangeListener(changeListener { left, right ->
                view.tv_abv_selection.text = resources.getString(R.string.abv_selection, left, right)
            })
        }
        with(view.ibu_rangebar) {
            val max = "${tickEnd.toInt()}+"
            view.tv_ibu_selection.text = resources.getString(R.string.ibu_selection, tickStart.toInt().toString(), max)
            setPinTextFormatter { string -> if (string == tickEnd.toInt().toString()) max else string }
            setOnRangeBarChangeListener(changeListener { left, right ->
                view.tv_ibu_selection.text = resources.getString(R.string.ibu_selection, left, right)
            })
        }
        with(view.ebc_rangebar) {
            val max = "${tickEnd.toInt()}+"
            view.tv_ebc_selection.text = resources.getString(R.string.ebc_selection, tickStart.toInt().toString(), max)
            setPinTextFormatter { string -> if (string == tickEnd.toInt().toString()) max else string }
            setOnRangeBarChangeListener(changeListener { left, right ->
                view.tv_ebc_selection.text = resources.getString(R.string.ebc_selection, left, right)
            })
        }

        return view
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