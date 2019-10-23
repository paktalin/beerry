package com.paktalin.beerapp.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(private val space: Int, private val spanCount: Int) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        if (parent.getChildAdapterPosition(view) % spanCount == 0)
            with(outRect) {
                left = space
                right = space
                bottom = space
                top = 0

            }
        else
            with(outRect) {
                left = 0
                right = space
                bottom = space
                top = 0
            }
    }
}