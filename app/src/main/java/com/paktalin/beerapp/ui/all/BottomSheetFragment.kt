package com.paktalin.beerapp.ui.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paktalin.beerapp.R

class BottomSheetFragment : BottomSheetDialogFragment() {
   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                             savedInstanceState: Bundle?): View {
       val view = inflater.inflate(R.layout.fragment_bottom_sheet_dialog, container, false)
       return view
   }
}