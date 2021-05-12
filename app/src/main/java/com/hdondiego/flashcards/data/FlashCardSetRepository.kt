package com.hdondiego.flashcards.data

import androidx.lifecycle.LiveData
import com.hdondiego.flashcards.data.FlashCard
import com.hdondiego.flashcards.data.FlashCardDao
import com.hdondiego.flashcards.data.FlashCardSet
import com.hdondiego.flashcards.data.FlashCardSetDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FlashCardSetRepository(private val flashCardSetDao: FlashCardSetDao) {
    val allFlashCardSets = flashCardSetDao.getAllSets()

    suspend fun insertNewSet(flashCardSet: FlashCardSet) {
        flashCardSetDao.insertNewSet(flashCardSet)
    }

    suspend fun updateSet(flashCardSet: FlashCardSet) {
        flashCardSetDao.updateSet(flashCardSet)
    }

    suspend fun deleteSet(flashCardSet: FlashCardSet) {
        flashCardSetDao.deleteSet(flashCardSet)
    }

    /*
    suspend fun getSpecificSet(setId: Int): FlashCardSet {
        return flashCardSetDao.getSpecificSet(setId)
    }
    */
    suspend fun getSpecificSet(setId: Int): FlashCardSet = withContext(Dispatchers.IO){
        flashCardSetDao.getSpecificSet(setId)
    }
}

/*
class FlashCardSetRepository (private val flashCardSetDao: FlashCardSetDao) {
    */
/*private lateinit var dao: FlashCardsDao
    private lateinit var flashCardCollections: LiveData<List<FlashCardCollections>>*//*


    */
/*constructor(application: Application){
        var database: FlashCardsRoomDatabase = FlashCardsRoomDatabase.get(application)
        dao = database.flashCardsDao()
        flashCardCollections = dao.getAllSets()
    }*//*


    val allFlashCardSets: LiveData<List<FlashCardSet>> = flashCardSetDao.getAllSets()
    //val allFlashCards: LiveData<List<FlashCard>> = flashCardDao.getAllCards()

    suspend fun insertNewSet(flashCardSet: FlashCardSet) {
        flashCardSetDao.insertNewSet(flashCardSet)
    }

    suspend fun updateSet(flashCardSet: FlashCardSet){
        flashCardSetDao.updateSet(flashCardSet)
    }

    suspend fun deleteSet(flashCardSet: FlashCardSet){
        flashCardSetDao.deleteSet(flashCardSet)
    }

    suspend fun getSpecificSet(setId: Int): FlashCardSet {
        return flashCardSetDao.getSpecificSet(setId)
    }

    */
/*fun getAllSets() : LiveData<List<FlashCardCollections>>{
        return flashCardCollections
    }*//*


    */
/*companion object {
        private inner class

    }*//*

}*/
