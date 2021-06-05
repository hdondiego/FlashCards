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
import com.google.android.material.floatingactionbutton.FloatingActionButton
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
    private lateinit var fab: FloatingActionButton
    val TAG : String? = MainActivity::class.simpleName

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

        fab = findViewById(R.id.fab)
        fab.setOnClickListener {
            val createCardSetFragment = CreateCardSetFragment()
            createCardSetFragment.show(supportFragmentManager, "CreateCardSetFragment")
        }
    }

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
    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)

        val actionAdd = menu?.findItem(R.id.action_add_set)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        R.id.action_add_set -> {
            val createCardSetFragment = CreateCardSetFragment()
            createCardSetFragment.show(supportFragmentManager, "CreateCardSetFragment")
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }*/

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

    companion object{
        // the different numbers help distinguish between two different situations
        const val TERM_DEF_REQUEST = 1
        const val FLASH_CARD_REQUEST = 2
    }
}
