package com.hdondiego.flashcards.adapters

import android.animation.*
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hdondiego.flashcards.R
import com.hdondiego.flashcards.models.FlashCard

class CardAdapter(): RecyclerView.Adapter<CardAdapter.CardViewHolder>(){ // private var quizItems: ArrayList<QuizItem>
    val TAG : String? = CardAdapter::class.simpleName
    private var flashCards = emptyList<FlashCard>()
    private lateinit var context: Context
    private lateinit var parent: ViewGroup
    private lateinit var mListener: OnFlashCardUpdatedListener

    inner class CardViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val frameLayout: FrameLayout = view.findViewById(R.id.frameLayout)
        val frontCard: FrameLayout = view.findViewById(R.id.frontCard)
        val backCard: FrameLayout = view.findViewById(R.id.backCard)

        val termTextView: TextView = view.findViewById(R.id.frontCardDetail)
        val defTextView: TextView = view.findViewById(R.id.backCardDetail)
    }

    interface OnFlashCardUpdatedListener {
        fun onItemUpdated(flashCard: FlashCard, position: Int)
    }

    fun setFlashCardUpdatedListener(mListener: OnFlashCardUpdatedListener){
        this.mListener = mListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {//CardViewHolder {
        context = parent.context
        this.parent = parent
        //val view: View = LayoutInflater.from(parent.context).inflate(R.layout.fragment_card, parent, false)
        val scale: Float = parent.resources.displayMetrics.density
        val distance = 8000
        //val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row2, parent, false)
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        view.cameraDistance = distance * scale
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        //val testSet: AnimatorSet = AnimatorInflater.loadAnimator(context, R.animator.card_flip_left_in) as AnimatorSet
        holder.termTextView.text = flashCards[position].term // holder.termTextView.text = quizItems[position].term
        holder.defTextView.text = flashCards[position].def // holder.defTextView.text = quizItems[position].def

        holder.frameLayout.setOnClickListener {
            Log.d(TAG, "Card Flip triggered on position ${position}")
            if (flashCards[position].front) {
                // flip from Term/Front to Definition/Back
                val frontToBackStart: Animator = AnimatorInflater.loadAnimator(context, R.animator.front_to_back_start);
                frontToBackStart.setTarget(holder.frontCard);

                val frontToBackEnd: Animator = AnimatorInflater.loadAnimator(context, R.animator.front_to_back_end)
                frontToBackEnd.setTarget(holder.backCard)

                val frontToBackAnimation: AnimatorSet = AnimatorSet()
                frontToBackAnimation.playTogether(frontToBackStart, frontToBackEnd)

                flashCards[position].front = false

                frontToBackAnimation.start()
            } else {
                // flip from Definition/Back to Term/Front
                val backToFrontStart: Animator = AnimatorInflater.loadAnimator(context, R.animator.back_to_front_start)
                backToFrontStart.setTarget(holder.backCard)

                val backToFrontEnd: Animator = AnimatorInflater.loadAnimator(context, R.animator.back_to_front_end)
                backToFrontEnd.setTarget(holder.frontCard)

                val backToFrontAnimation: AnimatorSet = AnimatorSet()
                backToFrontAnimation.playTogether(backToFrontStart, backToFrontEnd)

                flashCards[position].front = true

                backToFrontAnimation.start()
            }
            Log.d(TAG,
                "flashCards: \n${flashCards.forEachIndexed { index, flashCard -> Log.d(TAG, "pos ${index}: ${flashCard.front}") } }"
            )
        }
    }

    fun setFlashCards(flashCards: List<FlashCard>) {
        this.flashCards = flashCards
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return flashCards.size // quizItems.size
    }
}