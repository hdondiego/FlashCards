package com.hdondiego.flashcards.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hdondiego.flashcards.FlashCardActivity
import com.hdondiego.flashcards.data.FlashCard
import com.hdondiego.flashcards.data.FlashCardRepository
import com.hdondiego.flashcards.data.FlashCardSetRepository
import com.hdondiego.flashcards.data.FlashCardsRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FlashCardViewModel(private val flashCardRepository: FlashCardRepository) : ViewModel() {
    //var flashCards = flashCardRepository.allFlashCards
    var flashCards: LiveData<List<FlashCard>>? = null

    fun getAllCardsInSet(setId: Int) = viewModelScope.launch(Dispatchers.IO) {
        flashCards = flashCardRepository.getAllCardsInSet(setId)
    }

    fun insertNewCard(flashCard: FlashCard) = viewModelScope.launch(Dispatchers.IO) {
        flashCardRepository.insertNewCard(flashCard)
    }

    fun updateCard(flashCard: FlashCard) = viewModelScope.launch(Dispatchers.IO) {
        flashCardRepository.updateCard(flashCard)
    }

    fun deleteCard(flashCard: FlashCard) = viewModelScope.launch(Dispatchers.IO) {
        flashCardRepository.deleteCard(flashCard)
    }

    /*fun deleteSet(setId: Int) = viewModelScope.launch(Dispatchers.IO) {
        val set: FlashCardSet = flashCardSetRepository.getSpecificSet(setId)
        flashCardSetRepository.deleteSet(set)
        ///val flashCardSet: FlashCardSet = FlashCardSet(set.value!!.setId, )
    }*/

    /*suspend fun getSpecificSet(setId: Int) = withContext(Dispatchers.IO) {
        flashCardSetRepository.getSpecificSet(setId)
    }*/
}

/*
class FlashCardViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var repository: FlashCardRepository
    lateinit var allFlashCardsInSet: LiveData<List<FlashCard>>
    private val flashCardDao = FlashCardsRoomDatabase.getDatabase(application).getFlashCardDao()
    //var setId: Int = FlashCardActivity.

    */
/*init {
        val flashCardDao
    }*//*


    fun setCardSetId(setId: Int){
        repository = FlashCardRepository(flashCardDao, setId)
        allFlashCardsInSet = repository.allFlashCards
    }

    fun insertNewCard(flashCard: FlashCard) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertNewCard(flashCard)
    }

    fun updateCard(flashCard: FlashCard) = viewModelScope.launch (Dispatchers.IO){
        repository.updateCard(flashCard)
    }

    fun deleteCard(flashCard: FlashCard) = viewModelScope.launch (Dispatchers.IO){
        repository.deleteCard(flashCard)
    }
}*/
