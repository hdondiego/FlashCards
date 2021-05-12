package com.hdondiego.flashcards.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hdondiego.flashcards.R
import com.hdondiego.flashcards.data.FlashCardSet
import com.hdondiego.flashcards.data.FlashCardSetDao
import com.hdondiego.flashcards.data.FlashCardSetRepository
import com.hdondiego.flashcards.data.FlashCardsRoomDatabase
import com.hdondiego.flashcards.viewmodels.FlashCardSetViewModel
import com.hdondiego.flashcards.viewmodels.FlashCardSetViewModelFactory
import kotlinx.coroutines.*
import org.w3c.dom.Text

class DeleteCardSetFragment(val setID: Int) : DialogFragment(){ //val setId: Int
    //private lateinit var flashCardSetViewModel: FlashCardSetViewModel
    val TAG : String? = DeleteCardSetFragment::class.simpleName
    private var set: FlashCardSet = FlashCardSet(0, "")
    private lateinit var flashCardSet: FlashCardSet
    var flashCardSetName: String = ""
    private lateinit var mListener: OnDeleteSetListener
    //private var fragmentView: View? = null
    //val dao: FlashCardSetDao = FlashCardsRoomDatabase.getInstance(activity!!.applicationContext).flashCardSetDao() //activity!!.applicationContext
    //val repository: FlashCardSetRepository = FlashCardSetRepository(dao)
    //val factory: FlashCardSetViewModelFactory = FlashCardSetViewModelFactory(repository)
    //val flashCardSetViewModel: FlashCardSetViewModel = ViewModelProvider(this, factory).get(FlashCardSetViewModel::class.java)
    /*val strDeleteFormat: String = resources.getString(R.string.deleteWarningMessage)
    var strDeleteMessage: String = ""*/
    //var textViewLayout: View = LayoutInflater.from(activity).inflate(R.layout.alertdialog_textview, null)
    //var deleteMsgEditText: EditText = textViewLayout.findViewById(R.id.deleteWarningTextView)

    interface OnDeleteSetListener {
        fun onDeleteSet(flashCardSet: FlashCardSet)
    }


    /*
    init {

        //setFlashCardSetRef(setID)
        /*
        flashCardSetViewModel.getSpecificSet(setID).observe(this, Observer {
            strDeleteMessage = String.format(strDeleteFormat, it.collectionName)
        })
         */
    }
     */

    /*
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        flashCardSetViewModel.getSpecificSet(setID).observe(this, Observer {
            flashCardSet = it
        })
        Log.d(TAG, "onActivityCreated - flashCardSet collectionName: ${flashCardSet.collectionName}")
    }*/

    /*fun setFlashCardSetRef(setID: Int){
        //flashCardSet = flashCardSetViewModel.getSpecificSet(setID)
        Log.d(TAG, "Observer: About to call observe")
        flashCardSetViewModel.getSpecificSet(setID).observe(this, Observer {
            Log.d(TAG, "Observer: ${it.collectionName} set")
            strDeleteMessage = String.format(strDeleteFormat, it.collectionName)
        })
    }*/

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            mListener = context as OnDeleteSetListener
        } catch (e: ClassCastException) {
            Log.d(TAG, "Activity doesn't implement an OnDeleteSetListener interface.")
        }
    }

    /*fun setOnDeleteSetListener(listener: OnDeleteSetListener){
        mListener = listener
    }*/

    /*fun setCurrentSet(currentSet: FlashCardSet){
        set = currentSet
        flashCardSetName = currentSet.collectionName
    }*/

    /*override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { // was -> View?
        var deleteWarningTextView: TextView? = fragmentView?.findViewById(R.id.deleteWarningTextView)
        flashCardSetViewModel.getSpecificSet(setID)
        Log.d(TAG, "Observer: Started observe")
        flashCardSetViewModel.resultSet.observe(viewLifecycleOwner, Observer { // owner: this
            //Log.d(TAG, "Observer: ${flashCardSetViewModel.resultSet.value?.collectionName}")
            Log.d(TAG, "Observer: ${it.collectionName} set")
            //val temp = flashCardSetViewModel.resultSet.value?.collectionName?.toCharArray()
            //val temp = it.collectionName.toCharArray()
            //flashCardSetName = temp?.copyOf().toString()
            //flashCardSetName = temp.copyOf().toString()
            //builder.setMessage("All flashcards in ${flashCardSetViewModel.resultSet.value?.collectionName} will be permanently deleted.")
            val strDeleteFormat: String = resources.getString(R.string.deleteWarningMessage)
            deleteWarningTextView?.setText(String.format(strDeleteFormat,flashCardSetViewModel.resultSet.value?.collectionName))
        })
        Log.d(TAG, "Observer: Finished observe")
        //deleteWarningTextView.setText(flashCardSetViewModel.)
        return fragmentView
    }

    override fun onDestroy() {
        fragmentView = null
        super.onDestroy()
    }*/

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //return super.onCreateDialog(savedInstanceState)
        //flashCardSetViewModel = ViewModelProvider(this).get(FlashCardSetViewModel::class.java)
        //flashCardSetViewModel = ViewModelProvider(this).get(FlashCardSetViewModel::class.java)

        val strDeleteFormat: String = resources.getString(R.string.deleteWarningMessage)
        var strDeleteMessage: String = ""

        // viewmodel works, but you don't see the created set right after clicking Create
        val dao = FlashCardsRoomDatabase.getInstance(activity!!.applicationContext).flashCardSetDao()
        val repository = FlashCardSetRepository(dao)
        val factory = FlashCardSetViewModelFactory(repository)
        val flashCardSetViewModel = ViewModelProvider(this, factory).get(FlashCardSetViewModel::class.java)
        val textView = TextView(activity)
        //fragmentView = LayoutInflater.from(activity).inflate(R.layout.alertdialog_textview,null)


        // use launch method instead
        /*scope.async {
            Log.d(TAG, "setID: ${setID}")
            set = flashCardSetViewModel.getSpecificSet(setID)
            flashCardSetName = set.collectionName.toCharArray().copyOf().toString()
            Log.d(TAG, "scope collectionName: ${set.collectionName}")
            //set.await()
        }*/


        //Log.d(TAG, "outer collectionName: ${flashCardSetName}")
        //Log.d(TAG, "outer collectionName: ${set.collectionName}")

        val builder = AlertDialog.Builder(activity)
        //val dialogView = LayoutInflater.from(activity).inflate(R.layout.alertdialog_edittext,)
        //val textView = dialogView.findViewById<TextView>(R.id.deleteWarningTextView)

        builder.setTitle(R.string.are_you_sure)

        /*
        Log.d(TAG, "Observer: About to call getSpecifiedSet")
        flashCardSetViewModel.getSpecificSet(setID)
        Log.d(TAG, "Observer: About to call observe")
        flashCardSetViewModel.resultSet.observe(viewLifecycleOwner, Observer { // owner: this
            //Log.d(TAG, "Observer: ${flashCardSetViewModel.resultSet.value?.collectionName}")
            Log.d(TAG, "Observer: ${it.collectionName} set")
            //val temp = flashCardSetViewModel.resultSet.value?.collectionName?.toCharArray()
            val temp = it.collectionName.toCharArray()
            //flashCardSetName = temp?.copyOf().toString()
            flashCardSetName = temp.copyOf().toString()
            //builder.setMessage("All flashcards in ${flashCardSetViewModel.resultSet.value?.collectionName} will be permanently deleted.")
        })
        Log.d(TAG, "Observer: Called observe")
        */





        //val strDeleteFormat: String = resources.getString(R.string.deleteWarningMessage)
        //var strDeleteMessage: String = ""

        /*Log.d(TAG, "Observer: About to call observe")
        flashCardSetViewModel.getSpecificSet(setID).observe(this, Observer {
            Log.d(TAG, "Observer: ${it.collectionName} set")
            strDeleteMessage = String.format(strDeleteFormat, it.collectionName)
        })
        Log.d(TAG, "Observer: Called observe")*/

        //Log.d(TAG, "strDeleteMessage: ${strDeleteMessage}")

        //builder.setView(fragmentView)
        //builder.setMessage("All flashcards in ${flashCardSetName} will be permanently deleted.") //setMessage("All flashcards in ${set.collectionName.toString()} will be permanently deleted.")
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            Log.d(TAG, "setID: ${setID}")
            set = flashCardSetViewModel.getSpecificSet(setID).copy()
            var collectionName = set.collectionName.toString()
            strDeleteMessage = String.format(strDeleteFormat, collectionName)
            textView.setText(strDeleteMessage)
            /*GlobalScope.launch(Dispatchers.Main){
                Log.d(TAG, "GS strDeleteMessage: ${strDeleteMessage}")
                builder.setMessage(collectionName)//strDeleteMessage.toCharArray())
            }*/
            Log.d(TAG, "collectionName: ${set.collectionName}")
            Log.d(TAG, "strDeleteMessage: ${strDeleteMessage}")
        }
        builder.setView(textView)
        builder.setPositiveButton(
                R.string.delete,
                DialogInterface.OnClickListener { dialog, id ->
                    flashCardSetViewModel.deleteSet(setID) // pass the input to create set
                    /*if (mListener != null){
                        mListener.onDeleteSet(set)
                    }*/
                    //mListener.onDeleteSet(set)

                    //set = FlashCardSet(0,"")
                })
            .setNegativeButton(
                R.string.cancel,
                DialogInterface.OnClickListener{ dialog, id ->
                    //set = FlashCardSet(0,"")
                    dialog.dismiss()
                })

        Log.d(TAG, "strDeleteMessage: ${strDeleteMessage}")
        return builder.show()

        /*return activity?.let {
            val builder = AlertDialog.Builder(it)

            builder.setTitle(R.string.are_you_sure)
                .setMessage(R.string.permanently_delete)
                .setPositiveButton(
                    R.string.delete,
                    DialogInterface.OnClickListener { dialog, id ->
                        flashCardSetViewModel.deleteSet(setId) // pass the input to create set
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