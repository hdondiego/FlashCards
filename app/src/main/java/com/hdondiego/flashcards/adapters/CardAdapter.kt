package com.hdondiego.flashcards.adapters

import android.animation.*
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.hdondiego.flashcards.QuizItem
import com.hdondiego.flashcards.R
import com.hdondiego.flashcards.data.FlashCard

class CardAdapter(): RecyclerView.Adapter<CardAdapter.CardViewHolder>(){ // private var quizItems: ArrayList<QuizItem>
    private var flashCards = emptyList<FlashCard>()
    private lateinit var context: Context
    private lateinit var parent: ViewGroup

    inner class CardViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val frameLayout: FrameLayout = view.findViewById(R.id.frameLayout)
        val frontCard: FrameLayout = view.findViewById(R.id.frontCard)
        val backCard: FrameLayout = view.findViewById(R.id.backCard)

        val termTextView: TextView = view.findViewById(R.id.frontCardDetail)
        val defTextView: TextView = view.findViewById(R.id.backCardDetail)
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
            if (flashCards[position].front) { // quizItems[position].front
                val termFlip: ObjectAnimator = ObjectAnimator.ofFloat(holder.frontCard, "rotationY", 0f, 180f) // 0 90 0f 180f linearLayout frontCardView
                termFlip.duration = 1000
                //termFlip.interpolator = AccelerateDecelerateInterpolator()

                val termFadeOut = ObjectAnimator.ofFloat(holder.frontCard, "alpha", 1.0f, 0.0f) // linearLayout frontCardView
                termFadeOut.startDelay = 500
                termFadeOut.duration = 0

                val animSet1: AnimatorSet = AnimatorSet()
                animSet1.playTogether(termFadeOut, termFlip)

                val defFadeOut: ObjectAnimator = ObjectAnimator.ofFloat(holder.backCard, "alpha", 1.0f, 0.0f) // backCardView
                defFadeOut.duration = 0

                val defFlip: ObjectAnimator =
                    ObjectAnimator.ofFloat(holder.backCard, "rotationY", -180f, 0f) // linearLayout backCardView
                defFlip.duration = 1000
                //defFlip.interpolator = DecelerateInterpolator()

                val defFadeIn: ObjectAnimator =
                    ObjectAnimator.ofFloat(holder.backCard, "alpha", 0.0f, 1.0f) // linearLayout backCardView
                defFadeIn.startDelay = 500
                defFadeIn.duration = 0

                val animSet2: AnimatorSet = AnimatorSet()
                animSet2.playTogether(defFadeOut, defFlip, defFadeIn)

                val animSet3: AnimatorSet = AnimatorSet()
                animSet3.playTogether(animSet1, animSet2)

                flashCards[position].front = false // quizItems[position].front = false
                notifyItemChanged(position)
                animSet3.start()
            } else {
                val defFlip: ObjectAnimator = ObjectAnimator.ofFloat(holder.backCard, "rotationY", 0f, -180f) // linearLayout backCardView
                defFlip.duration = 1000
                //defFlip.interpolator = AccelerateDecelerateInterpolator()

                val defFadeOut: ObjectAnimator = ObjectAnimator.ofFloat(holder.backCard, "alpha", 1.0f, 0f) // linearLayout backCardView
                defFadeOut.startDelay = 500
                defFadeOut.duration = 0

                val animSet1: AnimatorSet = AnimatorSet()
                animSet1.playTogether(defFlip, defFadeOut)

                val termFadeOut: ObjectAnimator = ObjectAnimator.ofFloat(holder.frontCard, "alpha", 1.0f, 0.0f) // frontCardView
                termFadeOut.duration = 0

                val termFlip: ObjectAnimator = ObjectAnimator.ofFloat(holder.frontCard, "rotationY", 180f, 0f) // 0 90 0f 180f linearLayout frontCardView
                termFlip.duration = 1000
                //termFlip.interpolator = DecelerateInterpolator()

                val termFadeIn = ObjectAnimator.ofFloat(holder.frontCard, "alpha", 0.0f, 1.0f) // linearLayout frontCardView
                termFadeIn.startDelay = 500
                termFadeIn.duration = 0

                val animSet2: AnimatorSet = AnimatorSet()
                animSet2.playTogether(termFadeOut, termFlip, termFadeIn)

                val animSet3: AnimatorSet = AnimatorSet()
                animSet3.playTogether(animSet1, animSet2)

                flashCards[position].front = true // quizItems[position].front = true
                notifyItemChanged(position)
                animSet3.start()
            }
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