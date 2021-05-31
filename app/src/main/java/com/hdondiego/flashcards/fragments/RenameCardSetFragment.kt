package com.hdondiego.flashcards.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.hdondiego.flashcards.R
import com.hdondiego.flashcards.data.FlashCardSet
import com.hdondiego.flashcards.data.FlashCardSetDao
import com.hdondiego.flashcards.data.FlashCardSetRepository
import com.hdondiego.flashcards.data.FlashCardsRoomDatabase
import com.hdondiego.flashcards.viewmodels.FlashCardSetViewModel
import com.hdondiego.flashcards.viewmodels.FlashCardSetViewModelFactory
import kotlinx.coroutines.*

class RenameCardSetFragment(val setID: Int) : DialogFragment() { //val setId: Int
    private lateinit var flashCardSetViewModel: FlashCardSetViewModel
    //private val flashCardSetViewModel: FlashCardSetViewModel by viewModels()
    val TAG : String? = RenameCardSetFragment::class.simpleName
    lateinit var editText: EditText
    //private var set: MutableLiveData<FlashCardSet> = MutableLiveData<FlashCardSet>()
    //private var set = LiveData<FlashCardSet>()
    private lateinit var mListener: OnRenameSetListener
    //val dialogView: View = LayoutInflater.from(activity).inflate(R.layout.alertdialog_edittext,null)

    lateinit var flashCardSetDao: FlashCardSetDao
    lateinit var repository: FlashCardSetRepository
    lateinit var factory: FlashCardSetViewModelFactory
    private var set: FlashCardSet = FlashCardSet(0,"")

    interface OnRenameSetListener {
        fun onRenameSet(flashCardSet: FlashCardSet)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            mListener = context as OnRenameSetListener
        } catch (e: ClassCastException) {
            Log.d(TAG, "Activity doesn't implement an OnRenameSetListener interface.")
        }
    }

    /*fun setOnRenameSetListener(listener: OnRenameSetListener){
        mListener = listener
    }*/

    

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //super.onViewCreated(view, savedInstanceState)
        *//*flashCardSetDao = FlashCardsRoomDatabase.getInstance(activity!!.applicationContext).flashCardSetDao()
        repository = FlashCardSetRepository(flashCardSetDao)
        factory = FlashCardSetViewModelFactory(repository)
        flashCardSetViewModel = ViewModelProvider(this, factory).get(FlashCardSetViewModel::class.java)*//*

        //val dialogView = LayoutInflater.from(activity).inflate(R.layout.alertdialog_edittext,null)
        //editText = v.findViewById(R.id.editText) // v <- dialogView

        *//*flashCardSetViewModel.getSpecificSet(setID).observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "setID: ${setID}")
            //set = flashCardSetViewModel.getSpecificSet(setID)
            //set.postValue(flashCardSetViewModel.getSpecificSet(setID))
            //Log.d(TAG, "collectionName: ${set.value!!.collectionName}")
            val toast = Toast.makeText(activity, "CollectionName: ${it.collectionName}", Toast.LENGTH_LONG)
            toast.show()
            //editText.text = Editable.Factory.getInstance().newEditable(set.value!!.collectionName)
        })*//*
    }*/

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //return super.onCreateDialog(savedInstanceState)
        flashCardSetDao = FlashCardsRoomDatabase.getInstance(requireActivity().applicationContext).flashCardSetDao()
        repository = FlashCardSetRepository(flashCardSetDao)
        factory = FlashCardSetViewModelFactory(repository)
        flashCardSetViewModel = ViewModelProvider(this, factory).get(FlashCardSetViewModel::class.java)
        //flashCardSetViewModel = ViewModelProvider(this).get(FlashCardSetViewModel::class.java)
        //flashCardSetViewModel = ViewModelProvider(this).get(FlashCardSetViewModel::class.java)

        // viewmodel works, but you don't see the created set right after clicking Create
        /*val dao = FlashCardsRoomDatabase.getInstance(activity!!.applicationContext).flashCardSetDao()
        val repository = FlashCardSetRepository(dao)
        val factory = FlashCardSetViewModelFactory(repository)
        flashCardSetViewModel = ViewModelProvider(this, factory).get(FlashCardSetViewModel::class.java)
        */
        val builder = AlertDialog.Builder(activity)
        //val dialogView = layoutInflater.inflate(R.layout.alertdialog_edittext, null)
        val dialogView = LayoutInflater.from(activity).inflate(R.layout.alertdialog_edittext,null)
        editText = dialogView.findViewById(R.id.editText)

        /*flashCardSetViewModel.getSpecificSet(setID).observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "setID: ${setID}")
            set = flashCardSetViewModel.getSpecificSet(setID)
            //set.postValue(flashCardSetViewModel.getSpecificSet(setID))
            Log.d(TAG, "collectionName: ${set.value!!.collectionName}")
            editText.text = Editable.Factory.getInstance().newEditable(set.value!!.collectionName)
        })*/

        /*
        flashCardSetViewModel.getSpecificSet(setID).observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "setID: ${setID}")
            //set = flashCardSetViewModel.getSpecificSet(setID)
            //set.postValue(flashCardSetViewModel.getSpecificSet(setID))
            //Log.d(TAG, "collectionName: ${set.value!!.collectionName}")
            editText.text = Editable.Factory.getInstance().newEditable(it.collectionName)
            val toast = Toast.makeText(activity, "CollectionName: ${it.collectionName}", Toast.LENGTH_LONG)
            toast.show()
            //editText.text = Editable.Factory.getInstance().newEditable(set.value!!.collectionName)
        })
         */

        // FIX
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            Log.d(TAG, "setID: ${setID}")
            set = flashCardSetViewModel.getSet(setID)
            //set.postValue(flashCardSetViewModel.getSpecificSet(setID))
            //Log.d(TAG, "collectionName: ${set.value!!.collectionName}")
            Log.d(TAG, "collectionName: ${set.collectionName}")
            //editText.text = Editable.Factory.getInstance().newEditable(set.value!!.collectionName)
            editText.text = Editable.Factory.getInstance().newEditable(set.collectionName)
        }

        //editText.text = Editable.Factory.getInstance().newEditable("Sample")
        builder.setTitle(R.string.rename_flashcard_set)
            .setView(dialogView) // v <- dialogView
            .setPositiveButton(
                R.string.rename,
                DialogInterface.OnClickListener { dialog, id ->
                    //val input = editText.text.toString()
                    //val flashCardSet = FlashCardSet(0, input)
                    //set.value!!.collectionName = editText.text.toString()
                    set.collectionName = editText.text.toString()
                    //flashCardSetViewModel.insertNewSet(flashCardSet) // pass the input to create set
                    /*if(mListener != null){
                        mListener.onRenameSet(set)
                    }*/
                    mListener.onRenameSet(set) //set.value!!

                    //set = MutableLiveData<FlashCardSet>()
                    set = FlashCardSet(0, "")
                })
            .setNegativeButton(
                R.string.cancel,
                DialogInterface.OnClickListener{ dialog, id ->
                    editText.text = Editable.Factory.getInstance().newEditable("")
                    //set = MutableLiveData<FlashCardSet>()
                    set = FlashCardSet(0, "")
                    dialog.dismiss()
                })



        return builder.show()

        /*return activity?.let {
            val builder = AlertDialog.Builder(it)
            val dialogView = layoutInflater.inflate(R.layout.alertdialog_edittext, null)
            editText = dialogView.findViewById(R.id.editText)

            val context = MainScope()
            context.launch {
                val set = flashCardSetViewModel.getSpecificSet(setId)
                val collectionName: String = set.collectionName
                editText.setText(collectionName)
            }

            builder.setTitle(R.string.rename_flashcard_set)
                .setView(dialogView)
                .setPositiveButton(
                    R.string.rename,
                    DialogInterface.OnClickListener { dialog, id ->
                        val input = editText.text.toString()
                        //val flashCardSet = FlashCardSet(0, input)
                        set.collectionName = input
                        set.dateModified = Date().time
                        flashCardSetViewModel.updateSet(set) // pass the input to create set
                    })
                .setNegativeButton(
                    R.string.cancel,
                    DialogInterface.OnClickListener{ dialog, id ->
                        dialog.dismiss()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")*/
    }
}