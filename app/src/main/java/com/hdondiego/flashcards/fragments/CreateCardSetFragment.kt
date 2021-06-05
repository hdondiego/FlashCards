package com.hdondiego.flashcards.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.hdondiego.flashcards.R
import com.hdondiego.flashcards.data.FlashCardSet
import com.hdondiego.flashcards.data.FlashCardSetRepository
import com.hdondiego.flashcards.data.FlashCardsRoomDatabase
import com.hdondiego.flashcards.viewmodels.FlashCardSetViewModel
import com.hdondiego.flashcards.viewmodels.FlashCardSetViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateCardSetFragment : DialogFragment() {
    /*val createListener: CreateListener

    interface CreateListener {
        fun create()
    }

    val*/

    val TAG : String? = CreateCardSetFragment::class.simpleName

    //private lateinit var flashCardSetViewModel: FlashCardSetViewModel
    private lateinit var editText: EditText
    private lateinit var mListener: OnCreateSetListener

    interface OnCreateSetListener {
        fun onCreateSet(flashCardSet: FlashCardSet)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            mListener = context as OnCreateSetListener
        } catch (e: ClassCastException) {
            Log.d(TAG, "Activity doesn't implement an OnCreateSetListener interface.")
        }
    }
    /*fun setOnCreateSetListener(listener: OnCreateSetListener){
        mListener = listener
    }*/

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //return super.onCreateDialog(savedInstanceState)
        //flashCardSetViewModel = ViewModelProvider(this).get(FlashCardSetViewModel::class.java)
        //flashCardSetViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(context)).get(
        //FlashCardSetViewModel::class.java)

        //flashCardSetViewModel = ViewModelProvider(this).get(FlashCardSetViewModel::class.java)
        //flashCardSetViewModel = ViewModelProvider(ViewModelStoreOwner()).get(FlashCardSetViewModel::class.java)

        // viewmodel works, but you don't see the created set right after clicking Create
        /*val dao = FlashCardsRoomDatabase.getInstance(activity!!.applicationContext).flashCardSetDao
        val repository = FlashCardSetRepository(dao)
        val factory = FlashCardSetViewModelFactory(repository)
        flashCardSetViewModel = ViewModelProvider(this, factory).get(FlashCardSetViewModel::class.java)
*/
        //val builder = AlertDialog.Builder(it)
        val mainActivity = activity
        val builder = AlertDialog.Builder(activity)
        //val dialogView = layoutInflater.inflate(R.layout.alertdialog_edittext, null)
        val dialogView = LayoutInflater.from(activity).inflate(R.layout.alertdialog_edittext,null)
        editText = dialogView.findViewById(R.id.editText)



        //editText.requestFocus()
//        val scope = CoroutineScope(Dispatchers.IO)
//        scope.launch {  }

        builder.setTitle(R.string.create_flashcard_set)
            .setView(dialogView)
            .setPositiveButton(
                R.string.create,
                DialogInterface.OnClickListener { dialog, id ->
                    val input = editText.text.toString()
                    val flashCardSet = FlashCardSet(0, input)
                    //flashCardSetViewModel.insertNewSet(flashCardSet) // pass the input to create set
                    /*if(mListener != null){
                        mListener.onCreateSet(flashCardSet)
                    }*/
                    mListener.onCreateSet(flashCardSet)
                })
            .setNegativeButton(
                R.string.cancel,
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.dismiss()
                })

        //val inpMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        //inpMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        val dialog = builder.create()

        editText.requestFocus()
        dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        //val imm: InputMethodManager = getSystemService(context.INPUT_METHOD_SERVICE) as InputMethodManager
        /*editText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
                dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
            }
        }*/
        return dialog
    }

        /*return activity?.let {
            val builder = AlertDialog.Builder(it)
            val dialogView = layoutInflater.inflate(R.layout.alertdialog_edittext, null)
            editText = dialogView.findViewById(R.id.editText)



            builder.setTitle(R.string.create_flashcard_set)
                .setView(dialogView)
                .setPositiveButton(
                    R.string.create,
                    DialogInterface.OnClickListener { dialog, id ->
                        val input = editText.text.toString()
                        val flashCardSet = FlashCardSet(0, input)
                        flashCardSetViewModel.insertNewSet(flashCardSet) // pass the input to create set
                    })
                .setNegativeButton(
                    R.string.cancel,
                    DialogInterface.OnClickListener{ dialog, id ->
                        dialog.dismiss()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }*/
}