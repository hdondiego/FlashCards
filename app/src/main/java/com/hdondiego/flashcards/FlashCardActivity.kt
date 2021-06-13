package com.hdondiego.flashcards

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputLayout
import com.hdondiego.flashcards.MainActivity.Companion.TERM_DEF_REQUEST
import com.hdondiego.flashcards.adapters.FlashCardListAdapter
import com.hdondiego.flashcards.data.FlashCard
import com.hdondiego.flashcards.data.FlashCardRepository
import com.hdondiego.flashcards.data.FlashCardSetRepository
import com.hdondiego.flashcards.data.FlashCardsRoomDatabase
import com.hdondiego.flashcards.fragments.BottomSheetFragment
import com.hdondiego.flashcards.viewmodels.FlashCardSetViewModel
import com.hdondiego.flashcards.viewmodels.FlashCardSetViewModelFactory
import com.hdondiego.flashcards.viewmodels.FlashCardViewModel
import com.hdondiego.flashcards.viewmodels.FlashCardViewModelFactory
import kotlinx.android.synthetic.main.activity_flashcard.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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

    //private lateinit var btnAddTerm : Button
    //private lateinit var btnQuiz : Button
    //private lateinit var context : Context
    val TAG : String? = FlashCardActivity::class.simpleName

    // need this
    private lateinit var recyclerView: RecyclerView
    private lateinit var flashCardListAdapter: FlashCardListAdapter
    //private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var flashCardViewModel: FlashCardViewModel
    private var flashCardPos: Int = -1
    //private var layoutState: Parcelable? = null
    //val onItemUpdatedListener: FlashCardListAdapter.OnItemUpdatedListener
    //val onItemSelected: FlashCardListAdapter.OnItemSelectedListener
    private lateinit var termTextInput: TextInputLayout
    private lateinit var defTextInput: TextInputLayout

    private lateinit var btnCancel: Button
    private lateinit var btnSave: Button
    private lateinit var btnQuiz: ActionMenuItemView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flashcard)

        val intent = intent
        val setId = intent.getIntExtra(EXTRA_SETID, -1)
        Log.d(TAG, "setID: $setId")
        val setName = intent.getStringExtra(EXTRA_SETNAME)
        Toast.makeText(this, "setName: $setName\nsetId: $setId", Toast.LENGTH_LONG).show()

        val materialToolbar: MaterialToolbar = findViewById(R.id.topAppBar)
        materialToolbar.title = setName

        /*if (savedInstanceState != null){
            terms = savedInstanceState?.getStringArrayList("terms") as ArrayList<String>
            definitions = savedInstanceState.getStringArrayList("def") as ArrayList<String>
            layoutManager.onRestoreInstanceState(savedInstanceState.getParcelable<Parcelable>("layoutState"))

        } else {
            terms = ArrayList()
            definitions = ArrayList()
        }*/

        termTextInput = findViewById(R.id.termTextInput)
        defTextInput = findViewById(R.id.defTextInput)
        val mBottomSheetBehavior = BottomSheetBehavior.from(persBottomSheet).apply {
            peekHeight = 100
            this.state = BottomSheetBehavior.STATE_COLLAPSED // will make it visible, but only at the peek height
            
            /*if (state == BottomSheetBehavior.STATE_COLLAPSED){
                termTextInput.editText!!.text = Editable.Factory.getInstance().newEditable("")
                defTextInput.editText!!.text = Editable.Factory.getInstance().newEditable("")
            }*/
        }

        mBottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                /*when (newState){
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        termTextInput.editText!!.text = Editable.Factory.getInstance().newEditable("")
                        defTextInput.editText!!.text = Editable.Factory.getInstance().newEditable("")
                    }
                }*/
                if (newState == BottomSheetBehavior.STATE_COLLAPSED){
                    termTextInput.editText!!.text = Editable.Factory.getInstance().newEditable("")
                    defTextInput.editText!!.text = Editable.Factory.getInstance().newEditable("")

                    if (termTextInput.hasFocus()){
                        termTextInput.clearFocus()
                    } else if (defTextInput.hasFocus()){
                        defTextInput.clearFocus()
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

        })

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
        //var cardPosition: Int = flashCardListAdapter.itemCount

        btnCancel = findViewById(R.id.btnCancel)
        btnCancel.setOnClickListener {
            val imm: InputMethodManager = applicationContext.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            if (termTextInput.hasFocus()){
                //termTextInput.clearFocus()
                imm.hideSoftInputFromWindow(termTextInput.windowToken, 0)
            } else if (defTextInput.hasFocus()){
                //defTextInput.clearFocus()
                imm.hideSoftInputFromWindow(defTextInput.windowToken, 0)
            }
            
            mBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            termTextInput.editText!!.text = Editable.Factory.getInstance().newEditable("")
            defTextInput.editText!!.text = Editable.Factory.getInstance().newEditable("")
            flashCardPos = -1
        }

        btnSave = findViewById(R.id.btnSave)
        btnSave.setOnClickListener {
            val imm: InputMethodManager = applicationContext.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            if (termTextInput.hasFocus()){
                //termTextInput.clearFocus()
                imm.hideSoftInputFromWindow(termTextInput.windowToken, 0)
            } else if (defTextInput.hasFocus()){
                //defTextInput.clearFocus()
                imm.hideSoftInputFromWindow(defTextInput.windowToken, 0)
            }

            mBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            val tempTerm: String = termTextInput.editText!!.text.toString()
            val tempDef: String = defTextInput.editText!!.text.toString()
            Log.d(TAG, "btnSave flashCardPos: ${flashCardPos}")
            if (flashCardPos < 0){
                Log.d(TAG, "Adding a new FlashCard")
                val tempFlashCard: FlashCard = FlashCard(0, tempTerm, tempDef, true, setId)
                flashCardViewModel.insertNewCard(tempFlashCard)
            } else {
                Log.d(TAG, "Updating current FlashCard")
                val tempFlashCard: FlashCard = FlashCard(flashCardViewModel.flashCards.value!![flashCardPos].cardId, tempTerm, tempDef, true, setId)
                flashCardViewModel.updateCard(tempFlashCard)
                flashCardListAdapter.notifyDataSetChanged()
            }
            //termTextInput.editText!!.text = Editable.Factory.getInstance().newEditable("")
            //defTextInput.editText!!.text = Editable.Factory.getInstance().newEditable("")

            //var view: View = termTextInput.focus

        }

        btnQuiz = findViewById(R.id.btnQuiz)
        btnQuiz.setOnClickListener {
            val intent: Intent = Intent(this@FlashCardActivity, QuizActivity::class.java)
            intent.putExtra(FlashCardActivity.EXTRA_SETID, setId)
            Log.d(TAG, "setID: ${setId}")
            startActivity(intent)
        }

        flashCardListAdapter.setOnItemUpdatedListener(object: FlashCardListAdapter.OnItemUpdatedListener {
            override fun onItemUpdated(flashCard: FlashCard, position: Int) {
                Log.d(TAG, "flashCard term: ${flashCard.term}")
                flashCardViewModel.updateCard(flashCard)
            }

        })

        flashCardListAdapter.setOnItemSelectedListener(object: FlashCardListAdapter.OnItemSelectedListener {
            override fun onItemSelected(flashCard: FlashCard, position: Int) {
                Log.d(TAG, "flashCard pos ${position} selected")
                val bottomSheetDialog: BottomSheetDialog = BottomSheetDialog(application)
                //val bottomSheetView: View = LayoutInflater.from(application).inflate(R.layout.bottom_sheet_layout, findViewById<RelativeLayout>(R.id.botLinearLayout))

                //val bottomSheetFragment: BottomSheetFragment = BottomSheetFragment()
                //bottomSheetFragment.show(supportFragmentManager, "BottomSheetDialogFragment")

                /*bottomSheetView.findViewById<Button>(R.id.btnCancel).setOnClickListener {
                    bottomSheetDialog.cancel()
                }

                bottomSheetDialog.setContentView(bottomSheetView)
                bottomSheetDialog.show()*/

                if (mBottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED){
                    val flashCard = flashCardViewModel.flashCards.value!!.get(position)
                    termTextInput.editText!!.text = Editable.Factory.getInstance().newEditable(flashCard.term)
                    defTextInput.editText!!.text = Editable.Factory.getInstance().newEditable(flashCard.def)
                    mBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                } else if (mBottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED){
                    mBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(450)
                        val flashCard = flashCardViewModel.flashCards.value!!.get(position)
                        termTextInput.editText!!.text = Editable.Factory.getInstance().newEditable(flashCard.term)
                        defTextInput.editText!!.text = Editable.Factory.getInstance().newEditable(flashCard.def)
                        mBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                    }
                }
                flashCardPos = position
            }
        })


        // TextInputLayout Test - Leave here for now
        /*flashCardListAdapter.setOnItemSelectedListener(object: FlashCardListAdapter.OnItemSelectedListener{
            override fun onItemSelected(flashCard: FlashCard, position: Int) {
                val intent = Intent(this@FlashCardActivity, TextInputLayout::class.java)
                //Log.d(TAG, "setID: ${flashCardSet.setId}")
                //intent.putExtra(FlashCardActivity.EXTRA_SETID, flashCardSet.setId)
                //intent.putExtra(FlashCardActivity.EXTRA_SETNAME, flashCardSet.collectionName)
                startActivity(intent)
            }
        })*/

        //btnAddTerm = findViewById(R.id.btnAddTerm)
        //btnQuiz = findViewById(R.id.btnQuiz)

        /*btnAddTerm.setOnClickListener {
            //terms.add("")
            //definitions.add("")
            val flashCard = FlashCard(0, "", "", true, setId)
            *//*Log.d("Term Added", flashCard.toString())
            Log.d("Def Added", definitions.toString())*//*
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
        }*/

        /*btnQuiz.setOnClickListener{
            val intent = Intent(this, QuizActivity::class.java)
            //Log.d("Terms Send", terms.toString())
            //Log.d("Def Send", definitions.toString())
            intent.putExtra("terms_key", flashcards)
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
