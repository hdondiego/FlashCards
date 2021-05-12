package com.hdondiego.flashcards

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hdondiego.flashcards.adapters.FlashCardSetListAdapter
import com.hdondiego.flashcards.data.FlashCardSet
import com.hdondiego.flashcards.data.FlashCardSetRepository
import com.hdondiego.flashcards.data.FlashCardsRoomDatabase
import com.hdondiego.flashcards.fragments.CreateCardSetFragment
import com.hdondiego.flashcards.fragments.DeleteCardSetFragment
import com.hdondiego.flashcards.fragments.RenameCardSetFragment
import com.hdondiego.flashcards.viewmodels.FlashCardSetViewModel
import com.hdondiego.flashcards.viewmodels.FlashCardSetViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(),
    CreateCardSetFragment.OnCreateSetListener, RenameCardSetFragment.OnRenameSetListener,
    DeleteCardSetFragment.OnDeleteSetListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var flashCardSetListAdapter: FlashCardSetListAdapter
    private lateinit var flashCardSetViewModel: FlashCardSetViewModel

    //private lateinit var createCardSetFragment: CreateCardSetFragment
    //private lateinit var renameCardSetFragment: RenameCardSetFragment
    //private lateinit var deleteCardSetFragment: DeleteCardSetFragment

    val TAG : String? = MainActivity::class.simpleName

    //private lateinit var viewModel : FlashCardsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolBar))

        recyclerView = findViewById(R.id.recyclerView)
        flashCardSetListAdapter = FlashCardSetListAdapter(this)
        flashCardSetListAdapter.setOnItemClickListener(object : FlashCardSetListAdapter.OnItemClickListener {
            override fun onItemClick(flashCardSet: FlashCardSet) {
                //val intent = Intent(this, FlashCardActivity::class.java)
                val intent = Intent(this@MainActivity, FlashCardActivity::class.java)
                Log.d(TAG, "setID: ${flashCardSet.setId}")
                intent.putExtra(FlashCardActivity.EXTRA_SETID, flashCardSet.setId)
                intent.putExtra(FlashCardActivity.EXTRA_SETNAME, flashCardSet.collectionName)
                startActivity(intent)
            }
        })

        recyclerView.adapter = flashCardSetListAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val dao = FlashCardsRoomDatabase.getInstance(application).flashCardSetDao()
        val repository = FlashCardSetRepository(dao)
        val factory = FlashCardSetViewModelFactory(repository)
        flashCardSetViewModel = ViewModelProvider(this, factory).get(FlashCardSetViewModel::class.java)

        /*//flashCardsViewModel = ViewModelProvider(this).get(FlashCardsViewModel::class.java)
        flashCardSetViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)).get(
            FlashCardSetViewModel::class.java)*/
        flashCardSetViewModel.flashCardSets.observe(this, Observer { flashCardSets ->
            // update the cached copy of the flashcards in the adapter
            flashCardSets?.let { flashCardSetListAdapter.setFlashCardSet(it) }
        })

        //createCardSetFragment = CreateCardSetFragment()
        /*createCardSetFragment.setOnCreateSetListener(object: CreateCardSetFragment.OnCreateSetListener {
            override fun onCreateSet(flashCardSet: FlashCardSet) {
                flashCardSetViewModel.insertNewSet(flashCardSet)
            }

        })*/

        //renameCardSetFragment = RenameCardSetFragment()
        /*renameCardSetFragment.setOnRenameSetListener(object: RenameCardSetFragment.OnRenameSetListener {
            override fun onRenameSet(flashCardSet: FlashCardSet) {
                *//*(applicationContext as CoroutineScope).launch {
                    flashCardSetViewModel.getSpecificSet()
                }
                flashCardSet.setId*//*
                flashCardSetViewModel.updateSet(flashCardSet)
            }
        })*/

        //deleteCardSetFragment = DeleteCardSetFragment()
        /*deleteCardSetFragment.setOnDeleteSetListener(object: DeleteCardSetFragment.OnDeleteSetListener {
            override fun onDeleteSet(flashCardSet: FlashCardSet) {
                flashCardSetViewModel.deleteSet(flashCardSet)
            }
        })*/
        //registerForContextMenu(recyclerView)
        //viewModel = ViewModelProvider(this).get(FlashCardsViewModel::class.java)
    }

    /*private fun displayFlashCardSetList() {
        flashCardSetViewModel.flashCardSets.observe(this, Observer {

        })
    }*/

    override fun onContextItemSelected(item: MenuItem): Boolean {
        //return super.onContextItemSelected(item)

        //var set = MutableLiveData<FlashCardSet>()
        //flashCardSetViewModel.getSpecificSet(setId)

        /*var set: FlashCardSet = FlashCardSet(0, "")

        (this as CoroutineScope).launch {// context as CoroutineScope
            // applicationContext as CoroutineScope
            val temp: FlashCardSet = flashCardSetViewModel.getSpecificSet(item.groupId)
            set = temp
            //set = flashCardSetViewModel.specifiedSet
            //val set: LiveData<FlashCardSet> = flashCardSetViewModel.getSpecificSet(item.groupId)
        }*/

        if (item.title == "Rename"){
            //Toast.makeText(applicationContext, "Rename Card Set Name: ${item.title}", Toast.LENGTH_LONG).show()
            //Toast.makeText(this, "Rename Card Set Name: ${set.collectionName}", Toast.LENGTH_LONG).show()
            //val flashCardSet: LiveData<FlashCardSet> = flashCardSetViewModel.getSpecificSet(item.groupId)
            //val flashCardSet: FlashCardSet = flashCardSetViewModel.getSpecificSet(item.groupId)
            //val name: FlashCardSet? = flashCardSet.value
            //val n: String = flashCardSet.
            //val flashCardSet = flashCardSetViewModel.allFlashCardSets.

            //isCreated(true, set.value!!.collectionName)
            //isCreated(true, item.groupId)
            //val dialog = RenameCardSetFragment(item.groupId)

            var renameCardSetFragment = RenameCardSetFragment(item.groupId)
            //renameCardSetFragment.setCurrentSet(set)
            //renameCardSetFragment.editText.text = Editable.Factory.getInstance().newEditable(set.collectionName)
            renameCardSetFragment.show(supportFragmentManager, "RenameCardSetFragment")
        } else if (item.title == "Delete"){
            /*val delAD: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
            delAD.setTitle(R.string.are_you_sure)
            delAD.setMessage(R.string.permanently_delete)

            delAD.setPositiveButton(R.string.delete){ dialog, which->

                *//*(applicationContext as CoroutineScope).launch {
                    flashCardSetViewModel.getSpecificSet(item.groupId)
                    set = flashCardSetViewModel.specifiedSet
                    //val set: LiveData<FlashCardSet> = flashCardSetViewModel.getSpecificSet(item.groupId)
                }*//*
                flashCardSetViewModel.deleteSet(item.groupId)
            }

            delAD.setNegativeButton(R.string.cancel) { dialog, which ->
                dialog.dismiss()
            }

            val dAlertDialog: AlertDialog = delAD.create()
            dAlertDialog.show()*/
            var deleteCardSetFragment = DeleteCardSetFragment(item.groupId)
            //deleteCardSetFragment.setCurrentSet(set)
            //val dialog = DeleteCardSetFragment(item.groupId)
            deleteCardSetFragment.show(supportFragmentManager, "DeleteCardSetFragment")
        } else {
            return false
        }

        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)

        val actionAdd = menu?.findItem(R.id.action_add_set)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        R.id.action_add_set -> {
            //val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            /*builder.setTitle(R.string.create_flashcard_set)
            val dialogView = layoutInflater.inflate(R.layout.alertdialog_edittext, null)
            builder.setView(dialogView)
            val editText: EditText = dialogView.findViewById(R.id.editText)
            editText.addTextChangedListener(object : TextChangedWatcher(editText){
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //super.onTextChanged(p0, p1, p2, p3)
                    editText.error = null
                }
            })*/

            /*builder.setPositiveButton(R.string.create) { dialog, which ->
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
            alertDialog.show()*/
            //isCreated(false)
            val createCardSetFragment = CreateCardSetFragment()
            createCardSetFragment.show(supportFragmentManager, "CreateCardSetFragment")
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    /*override fun onAttachFragment(fragment: Fragment) {
        //super.onAttachFragment(fragment)
        if (fragment is CreateCardSetFragment){
            fragment.onAttach(this.applicationContext)
        }
    }*/

    override fun onCreateSet(flashCardSet: FlashCardSet) {
        flashCardSetViewModel.insertNewSet(flashCardSet)
    }

    override fun onRenameSet(flashCardSet: FlashCardSet) {
        flashCardSetViewModel.updateSet(flashCardSet)
    }

    override fun onDeleteSet(flashCardSet: FlashCardSet) {
        flashCardSetViewModel.deleteSet(flashCardSet)
    }

    /*fun isCreated(b: Boolean, setId: Int = 0) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
        val dialogView = layoutInflater.inflate(R.layout.alertdialog_edittext, null)
        builder.setView(dialogView)
        builder.setTitle(R.string.create_flashcard_set)
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

        if (b) {
            // if the set was already created
            //editText.text = name as Editable
            builder.setTitle(R.string.rename_flashcard_set)
            //(context as CoroutineScope)
            *//*(applicationContext as CoroutineScope).launch {
                val collectionName: String = flashCardSetViewModel.getSpecificSet(setId).collectionName
                editText.setText(collectionName)
            }*//*

            *//*CoroutineScope(this as CoroutineScope).launch {
                withContext(Dispatchers.IO){

                }
                val collectionName: String = flashCardSetViewModel.getSpecificSet(setId).collectionName
                editText.setText(collectionName)
            }*//*

            *//*(applicationContext as CoroutineScope).launch {
                val collectionName: String = flashCardSetViewModel.getSpecificSet(setId).collectionName
                editText.setText(collectionName)
            }*//*

            val context = MainScope()
            context.launch {
                val collectionName: String = flashCardSetViewModel.getSpecificSet(setId).collectionName
                editText.setText(collectionName)
            }
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }*/

    companion object{
        // the different numbers help distinguish between two different situations
        const val TERM_DEF_REQUEST = 1
        const val FLASH_CARD_REQUEST = 2
    }
}
