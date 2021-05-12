package com.hdondiego.flashcards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.hdondiego.flashcards.adapters.CardAdapter

class QuizActivity : AppCompatActivity() {
    private var quizItems : ArrayList<QuizItem> = ArrayList()
    private lateinit var recyclerView : RecyclerView
    private lateinit var  viewAdapter : RecyclerView.Adapter<*>
    private lateinit var viewManager : RecyclerView.LayoutManager

    private lateinit var terms : ArrayList<String>
    private lateinit var definitions : ArrayList<String>
    private lateinit var viewPager : ViewPager2

    private var showingBack : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        terms = intent.getStringArrayListExtra("terms_key")
        definitions = intent.getStringArrayListExtra("def_key")

        for (i in 0 until terms.size) run {
            val quizItem = QuizItem()
            quizItem.term = terms[i]
            quizItem.def = definitions[i]
            quizItems.add(quizItem)
        }


        viewPager = findViewById(R.id.viewPager)
        //viewPager.cameraDistance = distance * scale
        //val pagerAdapter = ScreenSlidePagerAdapter(this, quizItems)
        val cardAdapter = CardAdapter(quizItems)
        //viewPager.adapter = pagerAdapter
        viewPager.adapter = cardAdapter

        viewPager.setPageTransformer(DepthPageTransformer())
    }

    /*override fun onBackPressed() {
        val retIntent = intent
        retIntent.putStringArrayListExtra("terms_key", terms)
        retIntent.putStringArrayListExtra("def_key", definitions)
        setResult(Activity.RESULT_CANCELED,retIntent)
        finish()
        //super.onBackPressed()
    }*/
}
