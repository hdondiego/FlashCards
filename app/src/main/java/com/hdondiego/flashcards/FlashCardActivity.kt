package com.hdondiego.flashcards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hdondiego.flashcards.adapters.FlashCardListAdapter
import com.hdondiego.flashcards.data.FlashCard
import com.hdondiego.flashcards.data.FlashCardRepository
import com.hdondiego.flashcards.data.FlashCardSetRepository
import com.hdondiego.flashcards.data.FlashCardsRoomDatabase
import com.hdondiego.flashcards.viewmodels.FlashCardSetViewModel
import com.hdondiego.flashcards.viewmodels.FlashCardSetViewModelFactory
import com.hdondiego.flashcards.viewmodels.FlashCardViewModel
import com.hdondiego.flashcards.viewmodels.FlashCardViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FlashCardActivity : AppCompatActivity() {
    /*
    * @PrimaryKey (autoGenerate = true) @ColumnInfo(name = "card_id") var cardId: Int = 0,
    @ForeignKey(entity = FlashCardSet::class, parentColumns = ["set_id"], childColumns = ["set_id"], onDelete = ForeignKey.CASCADE)
    var setId: Int,
    @ColumnInfo(name = "term") var term: String,
    @ColumnInfo(name = "definition") var def: String,
    @ColumnInfo(name = "front") var front: Boolean = true
    * */

    // private object
    // lateinit - Late Initialization, will later assign it to values
    // var - Mutable variable, can change its value

    /*var terms : ArrayList<String> = ArrayList()
    var definitions : ArrayList<String> = ArrayList()
    */

    // needed this, in the past
    //lateinit var terms: ArrayList<String>
    //lateinit var definitions: ArrayList<String>

    private lateinit var btnAddTerm : Button
    private lateinit var btnQuiz : Button
    //private lateinit var context : Context
    val TAG : String? = FlashCardActivity::class.simpleName

    // need this
    private lateinit var recyclerView: RecyclerView
    private lateinit var flashCardListAdapter: FlashCardListAdapter
    //private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var flashCardViewModel: FlashCardViewModel
    //private var layoutState: Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flashcard)

        val intent = intent
        val setId = intent.getIntExtra(EXTRA_SETID, -1)
        Log.d(TAG, "setID: $setId")
        val setName = intent.getStringExtra(EXTRA_SETNAME)
        Toast.makeText(this, "setName: $setName\nsetId: $setId", Toast.LENGTH_LONG).show()

        setSupportActionBar(findViewById(R.id.toolBar))
        title = setName

        btnAddTerm = findViewById(R.id.btnAddTerm)
        btnQuiz = findViewById(R.id.btnQuiz)

        //layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        /*if (savedInstanceState != null){
            terms = savedInstanceState?.getStringArrayList("terms") as ArrayList<String>
            definitions = savedInstanceState.getStringArrayList("def") as ArrayList<String>
            layoutManager.onRestoreInstanceState(savedInstanceState.getParcelable<Parcelable>("layoutState"))

        } else {
            terms = ArrayList()
            definitions = ArrayList()
        }*/

        // need this
        recyclerView = findViewById(R.id.recyclerView)
        flashCardListAdapter = FlashCardListAdapter(this) // terms, definition
        /*flashCardListAdapter.setOnItemUpdatedListener(object: FlashCardListAdapter.OnItemUpdatedListener {
            override fun onItemUpdated(flashCard: FlashCard, position: Int) {
                Log.d(TAG, "Trying to update")
                flashCardViewModel.updateCard(flashCard)
                flashCardListAdapter.notifyItemChanged(position)
                Log.d(TAG, "Flash Card Updated: ${flashCard.cardId}, ${flashCard.term}, ${flashCard.def}, ${flashCard.setId}")
            }
        })*/

        //val callback: ItemTouchHelper.Callback = TermDefTouchHelper(flashCardListAdapter)
        //val itemTouchHelper: ItemTouchHelper = ItemTouchHelper(callback)
        //flashCardListAdapter.setTouchHelper(itemTouchHelper)
        //itemTouchHelper.attachToRecyclerView(recyclerView)

        recyclerView.adapter = flashCardListAdapter
        //recyclerView.layoutManager = layoutManager
        recyclerView.layoutManager = LinearLayoutManager(this)

        //context = applicationContext

        // fix setCardId to 'it'
        val flashCardDao = FlashCardsRoomDatabase.getInstance(this).flashCardDao()
        val repository = FlashCardRepository(flashCardDao)
        val factory = FlashCardViewModelFactory(repository)
        flashCardViewModel = ViewModelProvider(this, factory).get(FlashCardViewModel::class.java)
        // ViewModelProvider.AndroidViewModelFactory(this.application)
        //flashCardViewModel.setCardSetId(setId)
        /*flashCardViewModel.allFlashCardsInSet.observe(this, Observer { flashCards ->
            flashCards?.let { flashCardListAdapter.setFlashCards(it) }
        })*/

        flashCardViewModel.getCardsInSet(setId)
        flashCardViewModel.flashCards.observe(this, Observer { cards ->
            cards?.let { flashCardListAdapter.setFlashCards(it) }

            
        })
        var cardPosition: Int = flashCardListAdapter.itemCount


        btnAddTerm = findViewById(R.id.btnAddTerm)
        btnQuiz = findViewById(R.id.btnQuiz)

        btnAddTerm.setOnClickListener {
            //terms.add("")
            //definitions.add("")
            val flashCard = FlashCard(0, "", "", true, setId)
            /*Log.d("Term Added", flashCard.toString())
            Log.d("Def Added", definitions.toString())*/
            Log.d(TAG, "num_flashcards (before): ${flashCardViewModel.flashCards.value?.size}")
            Log.d(TAG, "num_flashcards (before): ${flashCardListAdapter.itemCount}")
            flashCardViewModel.insertNewCard(flashCard)
            //val allCards = flashCardViewModel.allFlashCards
            Log.d(TAG, "num_flashcards (after): ${flashCardViewModel.flashCards.value?.size}")
            Log.d(TAG, "num_flashcards (after): ${flashCardListAdapter.itemCount}")
            //cardPosition++

            //flashCardViewModel.getCardsInSet(setId)
            //flashCardListAdapter.setFlashCards(flashCardViewModel.flashCards.value!!)

            //flashCardListAdapter.notifyItemInserted(cardPosition)
            flashCardListAdapter.notifyDataSetChanged()
        }

        /*btnQuiz.setOnClickListener{
            val intent = Intent(this, QuizActivity::class.java)
            Log.d("Terms Send", terms.toString())
            Log.d("Def Send", definitions.toString())
            intent.putExtra("terms_key", terms)
            intent.putExtra("def_key", definitions)
            startActivityForResult(intent, TERM_DEF_REQUEST)
        }*/
    }

    /*override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putStringArrayList("terms", terms)
        outState.putStringArrayList("def", definitions)

        val layoutState = layoutManager.onSaveInstanceState()
        outState.putParcelable("layoutState", layoutState)
    }

    override fun onResume() {
        super.onResume()

        if (layoutState != null){
            layoutManager.onRestoreInstanceState(layoutState)
            dynamicAdapter.notifyDataSetChanged()
        }
    }

    companion object{
        const val TERM_DEF_REQUEST = 1
    }*/

    companion object{
        //val EXTRA_CARDID: String = "com.hdondiego.flashcards.EXTRA_CARDID"
        val EXTRA_SETID: String = "com.hdondiego.flashcards.EXTRA_SETID"
        val EXTRA_SETNAME: String = "com.hdondiego.flashcards.EXTRA_SETNAME"
        //val EXTRA_TERM: String = "com.hdondiego.flashcards.EXTRA_TERM"
        //val EXTRA_DEFINITION: String = "com.hdondiego.flashcards.EXTRA_DEFINITION"
        //val EXTRA_FRONT: String = "com.hdondiego.flashcards.EXTRA_FRONT"
    }
}
