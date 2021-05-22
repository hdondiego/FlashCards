package com.hdondiego.flashcards.adapters

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.hdondiego.flashcards.ItemTouchHelperAdapter
import com.hdondiego.flashcards.R
import com.hdondiego.flashcards.TextChangedWatcher
import com.hdondiego.flashcards.data.FlashCard
import com.hdondiego.flashcards.data.FlashCardDao
import com.hdondiego.flashcards.data.FlashCardRepository
import com.hdondiego.flashcards.data.FlashCardsRoomDatabase
import com.hdondiego.flashcards.viewmodels.FlashCardViewModel
import com.hdondiego.flashcards.viewmodels.FlashCardViewModelFactory

class FlashCardListAdapter(context: Context) : // private var terms: ArrayList<String>, private var definitions: ArrayList<String>
    RecyclerView.Adapter<FlashCardListAdapter.DynamicViewHolder>(){//,
    //ItemTouchHelperAdapter {

    //private val inflater = LayoutInflater.from(context)
    private var flashCards = emptyList<FlashCard>()
    private lateinit var mListener: OnItemUpdatedListener
    val TAG : String? = FlashCardListAdapter::class.simpleName
    //val dao: FlashCardDao = FlashCardsRoomDatabase.getInstance(context).flashCardDao()
    //val repository: FlashCardRepository = FlashCardRepository(dao)
    //val factory: FlashCardViewModelFactory = FlashCardViewModelFactory(repository)
    //val flashCardViewModel: FlashCardViewModel = ViewModelProvider(this, factory).get(FlashCardViewModel::class.java)
    //val flashCardViewModel: FlashCardViewModel = ViewModelProvider(this, factory).get(FlashCardViewModel::class.java)
    //lateinit var mTouchHelper: ItemTouchHelper // fix the item touch helper

    interface OnItemUpdatedListener {
        fun onItemUpdated(flashCard: FlashCard, position: Int)
    }

    fun setOnItemUpdatedListener(listener: OnItemUpdatedListener){
        // TextWatcher
        mListener = listener
    }

    inner class DynamicViewHolder : RecyclerView.ViewHolder{//, TextWatcher{//,
        //View.OnTouchListener,
        //GestureDetector.OnGestureListener {

        var termEditText: EditText
        var defEditText: EditText
        //var termEditTextListener: FlashCardEditTextListener = FlashCardEditTextListener(adapterPosition, true)
        //var defEditTextListener: FlashCardEditTextListener = FlashCardEditTextListener(adapterPosition, false)
        //var running: Boolean = false
        //private var mGestureDetector: GestureDetector

        //constructor(view: View, mListener: OnItemUpdatedListener) : super(view){
        constructor(view: View) : super(view) {
            termEditText = view.findViewById(R.id.termEditText)
            defEditText = view.findViewById(R.id.defEditText)

            //termEditText.addTextChangedListener(termEditTextListener)
            //defEditText.addTextChangedListener(defEditTextListener)
            /*termEditText.setOnFocusChangeListener { view, hasFocus ->
                if (!hasFocus){
                    Log.d(TAG, "This (${adapterPosition}) term edittext no longer has focus")
                    val termET: EditText = view as EditText
                    val term: String = termET.text.toString()

                    val position: Int = adapterPosition
                    val flashCard = FlashCard(flashCards[position].cardId, term, flashCards[position].def, true, flashCards[position].setId)

                    if ((mListener != null) && (position != RecyclerView.NO_POSITION)){
                        mListener.onItemUpdated(flashCard, position)
                    }
                }
            }*/

            /*termEditText.addTextChangedListener(object: TextWatcher{
                override fun afterTextChanged(p0: Editable?) {
                    *//*Log.d(TAG, "The term edittext recently changed")
                    if (!running){
                        running = true
                        val term = p0.toString()
                        Log.d(TAG, "term text: $term")

                        val position: Int = adapterPosition
                        val flashCard = FlashCard(position, term, flashCards[position].def, true, flashCards[position].setId)

                        if ((mListener != null) && (position != RecyclerView.NO_POSITION)){
                            mListener.onItemUpdated(flashCard)
                        }
                        running = false
                    }*//*

                    //Log.d(TAG, "The term edittext recently changed")
                    val term = p0.toString()
                    Log.d(TAG, "term text: $term")

                    val position: Int = adapterPosition
                    val flashCard = FlashCard(position, term, flashCards[position].def, true, flashCards[position].setId)

                    if ((mListener != null) && (position != RecyclerView.NO_POSITION)){
                        mListener.onItemUpdated(flashCard, position)
                    }

                    Log.d(TAG, "Term Edit Text pos: ${position} afterTextChanged called - ${flashCard.term}")

                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    Log.d(TAG, "I'm beforeTextChanged and I'm not going to do anything...")

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    Log.d(TAG, "I'm onTextChanged and I'm not going to do anything...")
                }
            })*/

            /*defEditText.setOnFocusChangeListener { view, hasFocus ->
                *//*if (!hasFocus){
                    Log.d(TAG, "This (${adapterPosition}) term edittext no longer has focus")
                    val defET: EditText = view as EditText
                    val definition: String = defET.text.toString()

                    val position: Int = adapterPosition
                    val flashCard = FlashCard(flashCards[position].cardId, flashCards[position].term, definition, true, flashCards[position].setId)

                    if ((mListener != null) && (position != RecyclerView.NO_POSITION)){
                        mListener.onItemUpdated(flashCard, position)
                    }
                }*//*
                val editText: EditText = view as EditText

                if (hasFocus){
                    editText.addTextChangedListener(object: TextWatcher{
                        override fun afterTextChanged(p0: Editable?) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                    })
                } else {
                    //editText.removeTextChangedListener()
                }
            }*/

            /*defEditText.addTextChangedListener(object: TextWatcher{
                override fun afterTextChanged(p0: Editable?) {
                    Log.d(TAG, "The definition edittext recently changed")
                    if (!running){
                        running = true
                        val definition = p0.toString()
                        Log.d(TAG, "def text: $definition")

                        val position: Int = adapterPosition
                        val flashCard = FlashCard(position, flashCards[position].term, definition, true, flashCards[position].setId)

                        if ((mListener != null) && (position != RecyclerView.NO_POSITION)){
                            mListener.onItemUpdated(flashCard)
                        }
                        running = false
                    }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    Log.d(TAG, "I'm beforeTextChanged and I'm not going to do anything...")
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    Log.d(TAG, "I'm afterTextChanged and I'm not going to do anything...")
                }
            })*/
            //mGestureDetector = GestureDetector(itemView.context, this)
            //itemView.setOnTouchListener(this)
        }

        /*override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            // can't use GestureDetector without the onTouch interface
            mGestureDetector.onTouchEvent(event)
            return true
        }

        override fun onShowPress(p0: MotionEvent?) {
            // work on this (not really)
        }

        // acts like an onClickListener
        override fun onSingleTapUp(p0: MotionEvent?): Boolean {
            // return true if it doesn't fling
            return false
        }

        override fun onDown(p0: MotionEvent?): Boolean {
            return false
        }

        override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
            // if it doesn't fling, return true
            return false
        }

        override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
            return false
        }

        override fun onLongPress(p0: MotionEvent?) {
            mTouchHelper.startDrag(this)
        }*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DynamicViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.dynamic_item_row, parent, false)
        //return DynamicViewHolder(view, mListener)
        return DynamicViewHolder(view)
    }

    override fun onBindViewHolder(holder: DynamicViewHolder, position: Int) {
        holder.termEditText.text = Editable.Factory.getInstance().newEditable(flashCards[position].term)
        holder.defEditText.text = Editable.Factory.getInstance().newEditable(flashCards[position].def)
    }

    override fun getItemCount(): Int {
        return flashCards.size
    }

    /*override fun onItemMove(fromPosition: Int, toPosition: Int) {
        Log.d("Start terms", terms.toString())
        Log.d("Start def", definitions.toString())

        val fromTerm = terms[fromPosition]
        val fromDef = definitions[fromPosition]

        terms.remove(fromTerm)
        definitions.remove(fromDef)

        terms.add(toPosition, fromTerm)
        definitions.add(toPosition, fromDef)

        Log.d("After Term Moved", terms.toString())
        Log.d("After Def Moved", definitions.toString())

        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemSwiped(position: Int) {
        Log.d("Start terms", terms.toString())
        Log.d("Start def", definitions.toString())

        terms.removeAt(position)
        definitions.removeAt(position)


        Log.d("After Term Deletion", terms.toString())
        Log.d("After Def Deletion", definitions.toString())

        notifyItemRemoved(position)
        //notifyItemRangeChanged(position, terms.size)
    }

    fun setTouchHelper(touchHelper: ItemTouchHelper){
        mTouchHelper = touchHelper
    }*/

    fun setFlashCards(flashCards: List<FlashCard>){
        this.flashCards = flashCards
        notifyDataSetChanged()
    }

    /*class FocusListener : View.OnFocusChangeListener{
        override fun onFocusChange(v: View?, hasFocus: Boolean) {
            if(!hasFocus){
                *//*when(v?.id){
                    R.id.termEditText ->
                        val editText
                }*//*
                val editText = v as EditText
            }
        }
    }*/


    /*inner class FlashCardEditTextListener(val position: Int, val isTerm: Boolean = false): TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            TODO("Not yet implemented")
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            TODO("Not yet implemented")
        }

        override fun afterTextChanged(s: Editable?) {
            val editTextString = s.toString()
            Log.d(TAG, "term text: $editTextString")

            if (isTerm){
                val flashCard = FlashCard(flashCards[position].cardId, editTextString, flashCards[position].def, true, flashCards[position].setId)
                Log.d(TAG, "Term: ${editTextString}\t")
            } else {
                val flashCard = FlashCard(flashCards[position].cardId, flashCards[position].term, editTextString, true, flashCards[position].setId)
            }

            *//*if ((mListener != null) && (position != RecyclerView.NO_POSITION)){
                mListener.onItemUpdated(flashCard, position)
            }*//*

            Log.d(TAG, "Term Edit Text pos: ${position} afterTextChanged called - ${flashCard.term}")
        }
    }*/
}