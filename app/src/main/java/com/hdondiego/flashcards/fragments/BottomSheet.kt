package com.hdondiego.flashcards.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.hdondiego.flashcards.R

class BottomSheet(context: Context) : BottomSheetDialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.bottom_sheet_layout)
        super.onCreate(savedInstanceState)
    }
}
