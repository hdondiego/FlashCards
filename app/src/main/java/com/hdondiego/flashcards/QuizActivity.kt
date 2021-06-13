package com.hdondiego.flashcards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.hdondiego.flashcards.FlashCardActivity.Companion.EXTRA_SETID
import com.hdondiego.flashcards.adapters.CardAdapter
import com.hdondiego.flashcards.data.FlashCard
import com.hdondiego.flashcards.data.FlashCardDao
import com.hdondiego.flashcards.data.FlashCardRepository
import com.hdondiego.flashcards.data.FlashCardsRoomDatabase
import com.hdondiego.flashcards.viewmodels.FlashCardViewModel
import com.hdondiego.flashcards.viewmodels.FlashCardViewModelFactory

class QuizActivity : AppCompatActivity() {
    //private var quizItems : ArrayList<QuizItem> = ArrayList()
    //private lateinit var recyclerView : RecyclerView
    //private lateinit var  viewAdapter : RecyclerView.Adapter<*>
    //private lateinit var viewManager : RecyclerView.LayoutManager

    //private lateinit var terms : ArrayList<String>
    //private lateinit var definitions : ArrayList<String>
    val TAG : String? = QuizActivity::class.simpleName

    private lateinit var dao: FlashCardDao
    private lateinit var repository: FlashCardRepository
    private lateinit var factory: FlashCardViewModelFactory
    private lateinit var flashCardViewModel: FlashCardViewModel

    //private lateinit var flashCards: MutableLiveData<List<FlashCard>>
    private lateinit var viewPager : ViewPager2

    //private var showingBack : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val setId = intent.getIntExtra(EXTRA_SETID, -1)
        dao = FlashCardsRoomDatabase.getInstance(this).flashCardDao()
        repository = FlashCardRepository(dao)
        factory = FlashCardViewModelFactory(repository)
        flashCardViewModel = ViewModelProvider(this, factory).get(FlashCardViewModel::class.java)
        flashCardViewModel.getCardsInSet(setId)
        //flashCards = flashCardViewModel.flashCards
        //Log.d(TAG, "flashCards size: ${flashCards.value!!.size}")

        //viewPager.cameraDistance = distance * scale
        //val pagerAdapter = ScreenSlidePagerAdapter(this, quizItems)

        val cardAdapter = CardAdapter() // quizItems
        flashCardViewModel.flashCards.observe(this, Observer { cards ->
            cards?.let { cardAdapter.setFlashCards(it) }
        })

        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter = cardAdapter
        viewPager.setPageTransformer(DepthPageTransformer())
    }
}
