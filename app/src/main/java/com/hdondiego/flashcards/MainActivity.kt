package com.hdondiego.flashcards

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hdondiego.flashcards.adapters.FlashCardSetListAdapter
import com.hdondiego.flashcards.models.FlashCardSet
import com.hdondiego.flashcards.data.FlashCardSetRepository
import com.hdondiego.flashcards.data.FlashCardsRoomDatabase
import com.hdondiego.flashcards.fragments.CreateCardSetFragment
import com.hdondiego.flashcards.fragments.DeleteCardSetFragment
import com.hdondiego.flashcards.fragments.RenameCardSetFragment
import com.hdondiego.flashcards.viewmodels.FlashCardSetViewModel
import com.hdondiego.flashcards.viewmodels.FlashCardSetViewModelFactory
import kotlinx.coroutines.*
import kotlinx.datetime.Clock

class MainActivity : AppCompatActivity(),
    CreateCardSetFragment.OnCreateSetListener, RenameCardSetFragment.OnRenameSetListener,
    DeleteCardSetFragment.OnDeleteSetListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var flashCardSetListAdapter: FlashCardSetListAdapter
    private lateinit var flashCardSetViewModel: FlashCardSetViewModel
    private lateinit var fab: FloatingActionButton
    val TAG : String? = MainActivity::class.simpleName

    // shared preferences stuff goes here
    var sortSelection: Int = 3 // default: sort by oldest card set to newest card set created

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


        setSupportActionBar(findViewById(R.id.materialToolbar)) //R.id.toolBar

        recyclerView = findViewById(R.id.recyclerView)
        flashCardSetListAdapter = FlashCardSetListAdapter(this)
        flashCardSetListAdapter.setOnItemClickListener(object : FlashCardSetListAdapter.OnItemClickListener {
            override fun onItemClick(flashCardSet: FlashCardSet) {
                //val intent = Intent(this, FlashCardActivity::class.java)
                var selectedSet: FlashCardSet = flashCardSet
                selectedSet.lastViewed = Clock.System.now()
                flashCardSetViewModel.updateSet(selectedSet)
                /*updateQueriedSets(sortSelection) // future note: make sure that we are not calling notifyDataSetChanged (costs too much time to load when there are more sets in flashcardset table)
                if (sortSelection == 4 || sortSelection == 5) {
                    flashCardSetListAdapter.notifyDataSetChanged()
                }*/
                /*flashCardSetListAdapter.notifyDataSetChanged()*/
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
        //flashCardSetViewModel.getAllSets()

        loadPreference()

        // now moved to the postResume function
        updateQueriedSets(sortSelection)
        flashCardSetViewModel.flashCardSets.observe(this, Observer { flashCardSets ->
            // update the cached copy of the flashcards in the adapter
            flashCardSets.let { flashCardSetListAdapter.setFlashCardSet(it) }
        })

        flashCardSetViewModel.flashCardSetCounts.observe(this, Observer { flashCardSetCounts ->
            flashCardSetCounts.let { flashCardSetListAdapter.setFlashCardSetCounts(it) }
        })

        //updateQueriedSets(sortSelection)


        fab = findViewById(R.id.fab)
        fab.setOnClickListener {
            val createCardSetFragment = CreateCardSetFragment()
            createCardSetFragment.show(supportFragmentManager, "CreateCardSetFragment")
        }
    }

    // may need to revisit this
    /*override fun onPostResume() {
        if (sortSelection == 4 || sortSelection == 5) {
            updateQueriedSets(sortSelection) // future note: make sure that we are not calling notifyDataSetChanged (costs too much time to load when there are more sets in flashcardset table)
            flashCardSetListAdapter.notifyDataSetChanged()
        }
        super.onPostResume()
    }*/

    /*override fun onPause() {
        if (sortSelection == 4 || sortSelection == 5) {
            updateQueriedSets(sortSelection) // future note: make sure that we are not calling notifyDataSetChanged (costs too much time to load when there are more sets in flashcardset table)
            flashCardSetListAdapter.notifyDataSetChanged()
        }
        super.onPause()
    }*/

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (item.title == "Rename"){
            var renameCardSetFragment = RenameCardSetFragment(item.groupId)
            renameCardSetFragment.show(supportFragmentManager, "RenameCardSetFragment")
        } else if (item.title == "Delete"){
            var deleteCardSetFragment = DeleteCardSetFragment(item.groupId)
            deleteCardSetFragment.show(supportFragmentManager, "DeleteCardSetFragment")
        } else {
            return false
        }

        return true
    }

    // DO NOT DELETE - This code is for the original add Flashcard Set button
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //menuInflater.inflate(R.menu.menu_item, menu)
        menuInflater.inflate(R.menu.main_app_bar, menu)

        //val actionAdd = menu?.findItem(R.id.action_add_set)
        //val actionSort = menu?.findItem(R.id.btnSort)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
        /*R.id.action_add_set -> {
            val createCardSetFragment = CreateCardSetFragment()
            createCardSetFragment.show(supportFragmentManager, "CreateCardSetFragment")
            true
        }*/
            R.id.btnSort -> {
                //var sortSelection: Int = 0
                val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
                alertDialog.setTitle("Sort By")
                alertDialog.setSingleChoiceItems(R.array.sort, sortSelection) { dialog, which ->
                    when (which) {
                        0 -> Toast.makeText(this, "Alphabetical (A-Z) selected", Toast.LENGTH_SHORT).show()
                        1 -> Toast.makeText(this, "Alphabetical (Z-A) selected", Toast.LENGTH_SHORT).show()
                        2 -> Toast.makeText(this, "Date Created (Newest) selected", Toast.LENGTH_SHORT)
                            .show()
                        3 -> Toast.makeText(this, "Date Created (Oldest) selected", Toast.LENGTH_SHORT)
                            .show()
                        4 -> Toast.makeText(this, "Recently Viewed selected", Toast.LENGTH_SHORT).show()
                        5 -> Toast.makeText(this, "Idle Card Sets selected", Toast.LENGTH_SHORT).show()
                    }
                    which.also { sortSelection = it } // may need to rethink this part
                    //dialog.dismiss()
                }
                alertDialog.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
                alertDialog.setPositiveButton("Sort", DialogInterface.OnClickListener { dialog, which ->
                    Log.d(TAG, "which val from sort dialog: ${sortSelection} -> ${resources.getStringArray(R.array.sort)[sortSelection]}")

                    updateQueriedSets(sortSelection) // uncomment this

                    savePreference()
                    flashCardSetListAdapter.notifyDataSetChanged() // uncomment this
                    //dialog.dismiss()
                }).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /*override fun onAttachFragment(fragment: Fragment) {
        //super.onAttachFragment(fragment)
        if (fragment is CreateCardSetFragment){
            fragment.onAttach(this.applicationContext)
        }
    }*/

    override fun onCreateSet(flashCardSet: FlashCardSet) {
        flashCardSetViewModel.insertNewSet(flashCardSet)
        //original -> updateQueriedSets(sortSelection)

        flashCardSetListAdapter.notifyDataSetChanged()
    }

    override fun onRenameSet(flashCardSet: FlashCardSet) {
        flashCardSetViewModel.updateSet(flashCardSet)
        //original -> updateQueriedSets(sortSelection)
        flashCardSetListAdapter.notifyDataSetChanged()
    }

    override fun onDeleteSet(flashCardSet: FlashCardSet) {
        flashCardSetViewModel.deleteSet(flashCardSet)
        //original -> updateQueriedSets(sortSelection)
        flashCardSetListAdapter.notifyDataSetChanged()
    }

    fun savePreference() {
        var sharedPreferences: SharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        var editor: SharedPreferences.Editor = sharedPreferences.edit()

        editor.putInt(SORT_ORDER, sortSelection)
        editor.apply()
    }

    fun loadPreference() {
        var sharedPreferences: SharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        sortSelection = sharedPreferences.getInt(SORT_ORDER, 3) // default: sort by oldest card set to newest card set created
    }

    fun updateQueriedSets(sortVal: Int) {
        flashCardSetViewModel.changeSort(sortVal)
    }

    companion object{
        // the different numbers help distinguish between two different situations
        const val TERM_DEF_REQUEST = 1
        const val FLASH_CARD_REQUEST = 2
        const val SHARED_PREFS: String = "sharedPrefs"
        const val SORT_ORDER: String = "sortOrder"
    }
}
