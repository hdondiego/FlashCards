package com.hdondiego.flashcards

import android.content.Context
import android.text.TextUtils
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import android.view.LayoutInflater

class CardSetAlertDialog(context: Context):AlertDialog(context){
    /*val builder: AlertDialog.Builder = AlertDialog.Builder(context)
    //builder.setTitle("")

    fun isCreated(b: Boolean){
        if (b) {
            // if the set was already created
            builder.setTitle(R.string.edit_flashcard_set)
        } else {
            // if the set needs to be created
            builder.setTitle(R.string.create_flashcard_set)
        }

        val dialogView: LayoutInflater = LayoutInflater.inflate(R.layout.alertdialog_edittext, null)
        builder.setView(dialogView)
        val editText: EditText = dialogView.findViewById(R.id.editText)
        editText.addTextChangedListener(object : TextChangedWatcher(editText){
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //super.onTextChanged(p0, p1, p2, p3)
                editText.error = null
            }
        })

        builder.setPositiveButton(R.string.create) { dialog, which ->
            if (TextUtils.isEmpty(editText.text.toString())){
                editText.error = "The flashcard set name field cannot be empty."
            } else {
                val input = editText.text.toString()
                val flashCardSet = FlashCardSet(0, input)
                //flashCardSet.collectionName = input
                flashCardSetViewModel.insertNewSet(flashCardSet) // pass the input to create set
            }

        }

        builder.setNegativeButton(R.string.cancel) { dialog, which ->
            dialog.dismiss()
        }

        val alertDialog = builder.create()
    }*/
}