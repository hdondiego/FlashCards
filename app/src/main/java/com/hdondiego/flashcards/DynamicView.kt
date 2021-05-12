package com.hdondiego.flashcards

import android.content.Context
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ScrollView

class DynamicView{
    private var context : Context

    constructor(ctx : Context){
        context = ctx
    }

    fun termEditText (recContext: Context, pos: Int) : EditText {
        //val lParams : ViewGroup.LayoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        val termEditText : EditText = EditText(recContext)
        termEditText.hint = "Term"
        termEditText.setSingleLine(false)
        termEditText.width = 500
        termEditText.id = pos
        return termEditText
    }

    fun defEditText (recContext: Context, pos: Int) : EditText {
        //val lParams : ViewGroup.LayoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        val defEditText : EditText = EditText(recContext)
        defEditText.hint = "Definition"
        defEditText.setSingleLine(false)
        defEditText.width = 500
        defEditText.id = pos
        return defEditText
    }
}