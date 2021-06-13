package com.hdondiego.flashcards.fragments

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.hdondiego.flashcards.QuizActivity
import com.hdondiego.flashcards.R

class BottomSheetFragment: BottomSheetDialogFragment() {
    val TAG : String? = BottomSheetFragment::class.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //val view: View = inflater.inflate(R.layout.bottom_sheet_layout, container,false)
        //termTextInput = view.findViewById(R.id.termTextInput)
        //defTextInput = view.findViewById(R.id.defTextInput)
        return inflater.inflate(R.layout.bottom_sheet_layout, container,false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogStyle)
        //dialog!!.window!!.setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        //dialog!!.window!!.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }
}