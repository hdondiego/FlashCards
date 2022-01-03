package com.hdondiego.flashcards.adapters

import android.content.Context
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hdondiego.flashcards.R
import com.hdondiego.flashcards.models.FlashCardSet

class FlashCardSetListAdapter internal constructor(val context: Context):
        RecyclerView.Adapter<FlashCardSetListAdapter.FlashCardSetViewHolder>(){

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var flashCardSets = emptyList<FlashCardSet>() // Cached copy of flashcard sets
    private var flashCardSetCounts = emptyList<Int>()
    private lateinit var mListener: OnItemClickListener
    val TAG : String? = FlashCardSetListAdapter::class.simpleName

    interface OnItemClickListener {
        //fun onItemClick(position: Int)
        fun onItemClick(flashCardSet: FlashCardSet)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }

    inner class FlashCardSetViewHolder: RecyclerView.ViewHolder, View.OnCreateContextMenuListener {//, View.OnClickListener{
        //val setNameLayout: LinearLayout
        //val setNameItemView: TextView

        val setNameLayout: LinearLayout//FrameLayout
        val setNameItemView: TextView
        val setCountItemView: TextView
        val setCreationDateItemView: TextView
        val setLastViewedDateItemView: TextView

        constructor(itemView: View, mListener: OnItemClickListener): super(itemView) { // listener
            //itemView.setOnClickListener(this)
            setNameLayout = itemView.findViewById(R.id.set_name_layout)
            setNameItemView = itemView.findViewById(R.id.flashcardset_name)
            setCountItemView = itemView.findViewById(R.id.flashcardset_count)
            setCreationDateItemView = itemView.findViewById(R.id.flashcardset_creation)
            setLastViewedDateItemView = itemView.findViewById(R.id.flashcardset_lastviewed)
            setNameLayout.setOnCreateContextMenuListener(this)
            setNameLayout.setOnClickListener(object: View.OnClickListener{
                override fun onClick(p0: View?) {
                    /*if (listener != null){
                        val position: Int = adapterPosition
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position)
                        }
                    }*/

                    val position: Int = adapterPosition
                    // checking if mListener is null b/c there isn't a guarantee that the setOnItemClickListener will be called

                    // checking for NO_POSITION (the value is -1) b/c it checks that we didn't click on an item with an invalid position
                    // could be invalid if we click on an item that is still in its deletion animation, and it would be invalid since
                    // the item is no longer a part of the data
                    if ((mListener != null) && (position != RecyclerView.NO_POSITION)){
                        mListener.onItemClick(flashCardSets[position])
                    }
                }
            })
        }

        override fun onCreateContextMenu(menu: ContextMenu?, view: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            menu?.setHeaderTitle(flashCardSets[adapterPosition].collectionName)
            /*menu?.add(this.adapterPosition, view!!.id, 0, "Rename")
            menu?.add(this.adapterPosition, view!!.id, 1, "Delete")*/
            menu?.add(flashCardSets[adapterPosition].setId, view!!.id, 0, "Rename")
            menu?.add(flashCardSets[adapterPosition].setId, view!!.id, 1, "Delete")
        }

        /*override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo) {
            //menu.setHeaderTitle()
            menu.add(this.adapterPosition, v.id, 0, "Rename")
            menu.add(this.adapterPosition, v.id, 1, "Delete")
        }*/



        /*override fun onClick(v: View) {
            cardSetClickListener.onItemClick(adapterPosition, v)
        }*/
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashCardSetViewHolder {
        //val itemView = inflater.inflate(R.layout.set_item_row, parent, false)
        val itemView = inflater.inflate(R.layout.set_row, parent, false) //R.layout.flashcardset_item_row
        return FlashCardSetViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: FlashCardSetViewHolder, position: Int) {
        holder.setNameItemView.text = flashCardSets[position].collectionName
        //val cardCount: Int = mCaller.retrieveCount(flashCardSets[position].setId)
        Log.d(TAG, "cardAmount string-array length: ${context.resources.getStringArray(R.array.card_amount).size}")

        /*if (position >= flashCardSetCounts.size) {
            Log.d(TAG, "flashCardSetCounts pos ${position}: 0")
            //holder.setCountItemView.text = Integer.toString(0)
            var cardCountItem: String = context.resources.getStringArray(R.array.card_amount)[1]
            //Log.d(TAG, "cardAmount string-array length: ${cardCountItem.length}")
            var countMsg: String = String.format(cardCountItem, 0)
            holder.setCountItemView.text = cardCountItem
        } else if (position) {
            Log.d(TAG, "flashCardSetCounts pos ${position}: ${flashCardSetCounts.get(position)}")
            //holder.setCountItemView.text = Integer.toString(flashCardSetCounts.get(position))//flashCardSetCounts[position].toString()
            var cardCountItem: String = Resources.getSystem().getStringArray(R.array.card_amount)[1]
            //var countMsg: String = String.format(cardCountItem, flashCardSetCounts[position])
        }*/
        //holder.setCountItemView.text = Integer.toString(2)//flashCardSetCounts[position].toString()
                // trying to call FlashCardSet query to get the flashcardset count of cards
        // already created query and added to repository and view model
        // already creater RetrieveCountCaller to make viewmodel call
        // must somehow receive the output here

        val cardCountItem: String
        val countMsg: String
        if (position >= flashCardSetCounts.size) {//flashCardSetCounts[position] == 0) {
            // if there are zero cards in set
            cardCountItem = context.resources.getStringArray(R.array.card_amount)[1]
            countMsg = String.format(cardCountItem, Integer.toString(0)) // 0 Cards
            holder.setCountItemView.text = countMsg
        } else if (flashCardSetCounts[position] == 1){
            // if there is only one card in set
            cardCountItem = context.resources.getStringArray(R.array.card_amount)[0]
            countMsg = String.format(cardCountItem, Integer.toString(1)) // 1 Card
            holder.setCountItemView.text = countMsg
        } else {
            // if there is more than 1 card in set
            cardCountItem = context.resources.getStringArray(R.array.card_amount)[1]
            Log.d(TAG, "num of cards in ${position} pos: ${flashCardSetCounts[position]}")
            countMsg = String.format(cardCountItem, flashCardSetCounts[position])
            holder.setCountItemView.text = countMsg
        }

        holder.setCreationDateItemView.text = flashCardSets[position].dateCreated.epochSeconds.toString()
        holder.setLastViewedDateItemView.text = flashCardSets[position].lastViewed.epochSeconds.toString() // rename dateModified in FlashCardSet to lastViewed
    }

    override fun getItemCount(): Int {
        return flashCardSets.size
    }

    internal fun setFlashCardSet(flashCardSets: List<FlashCardSet>){
        this.flashCardSets = flashCardSets
        notifyDataSetChanged()
    }

    internal fun setFlashCardSetCounts(flashCardSetCounts: List<Int>){
        this.flashCardSetCounts = flashCardSetCounts
        notifyDataSetChanged()
    }

    /*fun setOnItemClickListener (cardSetClickListener: CardSetClickListener){
        FlashCardSetListAdapter.cardSetClickListener = cardSetClickListener
    }

    interface CardSetClickListener {
        fun onItemClick(position: Int, v: View)
    }*/
}