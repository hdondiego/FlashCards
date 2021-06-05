package com.hdondiego.flashcards.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hdondiego.flashcards.data.FlashCard
import com.hdondiego.flashcards.data.FlashCardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FlashCardViewModel(private val flashCardRepository: FlashCardRepository) : ViewModel() {
    //var flashCards = flashCardRepository.allFlashCards
    var flashCards: MutableLiveData<List<FlashCard>> = MutableLiveData<List<FlashCard>>() // originally LiveData<List<FlashCard>>

    // DO NOT REMOVE
    /*fun getCardsInSet(setId: Int) = viewModelScope.launch(Dispatchers.IO) {
        flashCards = flashCardRepository.getCardsInSet(setId)
    }*/

    // DO NOT REMOVE
    fun getCardsInSet(setId: Int) = viewModelScope.launch(Dispatchers.IO) {
        flashCards.postValue(flashCardRepository.getCardsInSet(setId))
        //flashCards.value = flashCardRepository.getCardsInSet(setId) // cannot invoke setValue on a background thread
    }

    /*fun getCardsInSet(setId: Int) = viewModelScope.launch (Dispatchers.IO) {
        withContext(Dispatchers.IO) {
            flashCardRepository.getCardsInSet(setId)
        }
    }*/

    fun insertNewCard(flashCard: FlashCard) = viewModelScope.launch(Dispatchers.IO) {
        flashCardRepository.insertNewCard(flashCard)
        flashCards.postValue(flashCardRepository.getCardsInSet(flashCard.setId))
    }

    fun updateCard(flashCard: FlashCard) = viewModelScope.launch(Dispatchers.IO) {
        flashCardRepository.updateCard(flashCard)
        flashCards.postValue(flashCardRepository.getCardsInSet(flashCard.setId))
    }

    fun deleteCard(flashCard: FlashCard) = viewModelScope.launch(Dispatchers.IO) {
        flashCardRepository.deleteCard(flashCard)
        flashCards.postValue(flashCardRepository.getCardsInSet(flashCard.setId))
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
