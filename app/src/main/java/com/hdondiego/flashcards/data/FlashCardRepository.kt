package com.hdondiego.flashcards.data

import com.hdondiego.flashcards.models.FlashCard

class FlashCardRepository(private val flashCardDao: FlashCardDao) {
    //val allFlashCards = flashCardDao.getAllCardsInSet()
    //var allFlashCards: LiveData<List<FlashCard>>? = null

    // DO NOT REMOVE
    /*suspend fun getCardsInSet(setId: Int) : LiveData<List<FlashCard>>{
        //allFlashCards = flashCardDao.getAllCardsInSet(setId)
        return flashCardDao.getCardsInSet(setId)
    }*/

    suspend fun getCardsInSet(setId: Int) : List<FlashCard> {
        //allFlashCards = flashCardDao.getAllCardsInSet(setId)
        return flashCardDao.getCardsInSet(setId)
    }

    suspend fun insertNewCard(flashCard: FlashCard) {
        flashCardDao.insertNewCard(flashCard)
    }

    suspend fun updateCard(flashCard: FlashCard) {
        flashCardDao.updateCard(flashCard)
    }

    suspend fun deleteCard(flashCard: FlashCard) {
        flashCardDao.deleteCard(flashCard)
    }
}

/*
class FlashCardRepository (private val flashCardDao: FlashCardDao, setId: Int){

    val allFlashCards: LiveData<List<FlashCard>> = flashCardDao.getAllCardsInSet(setId)

    suspend fun insertNewCard(flashCard: FlashCard) {
        flashCardDao.insertNewCard(flashCard)
    }

    suspend fun updateCard(flashCard: FlashCard){
        flashCardDao.updateCard(flashCard)
    }

    suspend fun deleteCard(flashCard: FlashCard){
        flashCardDao.deleteCard(flashCard)
    }
}*/
