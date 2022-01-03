package com.hdondiego.flashcards.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hdondiego.flashcards.models.FlashCard
import com.hdondiego.flashcards.data.FlashCardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FlashCardViewModel(private val flashCardRepository: FlashCardRepository) : ViewModel() {
    //var flashCards = emptyList<FlashCard>()//flashCardRepository.getCardsInSet()
    //var flashCards: LiveData<List<FlashCard>>? = null

    //  DO NOT DELETE
    var flashCards: MutableLiveData<List<FlashCard>> = MutableLiveData<List<FlashCard>>() // originally LiveData<List<FlashCard>>

    // DO NOT REMOVE
    /*fun getCardsInSet(setId: Int) = viewModelScope.launch(Dispatchers.IO) {
        flashCards = flashCardRepository.getCardsInSet(setId)
    }*/

    // DO NOT REMOVE
    fun getCardsInSet(setId: Int) = viewModelScope.launch(Dispatchers.IO) {
        // DO NOT DELETE
        flashCards.postValue(flashCardRepository.getCardsInSet(setId))

        //flashCards = flashCardRepository.getCardsInSet(setId)
        //flashCards.value = flashCardRepository.getCardsInSet(setId) // cannot invoke setValue on a background thread
    }

    /*fun getCardsInSet(setId: Int) = viewModelScope.launch (Dispatchers.IO) {
        withContext(Dispatchers.IO) {
            flashCardRepository.getCardsInSet(setId)
        }
    }*/

    fun insertNewCard(flashCard: FlashCard) = viewModelScope.launch(Dispatchers.IO) {
        flashCardRepository.insertNewCard(flashCard)
        flashCards.postValue(flashCardRepository.getCardsInSet(flashCard.setId)) // for MutableLiveData - DO NOT DELETE
    }

    fun updateCard(flashCard: FlashCard) = viewModelScope.launch(Dispatchers.IO) {
        flashCardRepository.updateCard(flashCard)
        flashCards.postValue(flashCardRepository.getCardsInSet(flashCard.setId)) // for MutableLiveData - DO NOT DELETE
        //flashCards.value = flashCardRepository.getCardsInSet(flashCard.setId)
    }

    fun deleteCard(flashCard: FlashCard) = viewModelScope.launch(Dispatchers.IO) {
        //flashCardRepository.deleteCard(flashCard)
        flashCards.postValue(flashCardRepository.getCardsInSet(flashCard.setId)) // for MutableLiveData - DO NOT DELETE
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
