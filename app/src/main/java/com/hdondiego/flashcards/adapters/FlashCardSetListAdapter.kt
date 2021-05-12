package com.hdondiego.flashcards.adapters

import android.content.Context
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hdondiego.flashcards.R
import com.hdondiego.flashcards.data.FlashCardSet

class FlashCardSetListAdapter internal constructor(context: Context):
        RecyclerView.Adapter<FlashCardSetListAdapter.FlashCardSetViewHolder>(){

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var flashCardSets = emptyList<FlashCardSet>() // Cached copy of flashcard sets
    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        //fun onItemClick(position: Int)
        fun onItemClick(flashCardSet: FlashCardSet)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }

    inner class FlashCardSetViewHolder : RecyclerView.ViewHolder, View.OnCreateContextMenuListener {//, View.OnClickListener{
        val setNameLayout: LinearLayout
        val setNameItemView: TextView

        constructor(itemView: View, mListener: OnItemClickListener): super(itemView) { // listener
            //itemView.setOnClickListener(this)
            setNameLayout = itemView.findViewById(R.id.set_name_layout)
            setNameItemView = itemView.findViewById(R.id.flashcardset_name)
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
        val itemView = inflater.inflate(R.layout.set_item_row, parent, false)
        return FlashCardSetViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: FlashCardSetViewHolder, position: Int) {
        holder.setNameItemView.text = flashCardSets[position].collectionName
    }

    override fun getItemCount(): Int {
        return flashCardSets.size
    }

    internal fun setFlashCardSet(flashCardSets: List<FlashCardSet>){
        this.flashCardSets = flashCardSets
        notifyDataSetChanged()
    }

    /*fun setOnItemClickListener (cardSetClickListener: CardSetClickListener){
        FlashCardSetListAdapter.cardSetClickListener = cardSetClickListener
    }

    interface CardSetClickListener {
        fun onItemClick(position: Int, v: View)
    }*/
}