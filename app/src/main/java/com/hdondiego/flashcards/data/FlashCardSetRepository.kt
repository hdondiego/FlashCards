package com.hdondiego.flashcards.data

import androidx.lifecycle.*
import com.hdondiego.flashcards.FlashCardActivity
import com.hdondiego.flashcards.MainActivity
import com.hdondiego.flashcards.models.FlashCardSet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FlashCardSetRepository(private val flashCardSetDao: FlashCardSetDao) {
    var sortSelection: Int = 0
    val sortConfig: MutableLiveData<Int> = MutableLiveData<Int>()
    val flashCardSets: LiveData<List<FlashCardSet>> = Transformations.switchMap(sortConfig) {
        when (it) {
            0 -> {
                flashCardSetDao.getAllSetsAZ()
            }
            1 -> {
                flashCardSetDao.getAllSetsZA()
            }
            2 -> {
                flashCardSetDao.getAllSetsDateCreatedNewest()
            }
            3 -> {
                flashCardSetDao.getAllSetsDateCreatedOldest()
            }
            4 -> {
                flashCardSetDao.getAllSetsLastViewedRecently()
            }
            5 -> {
                flashCardSetDao.getAllSetsLastViewedIdle()
            }
            else -> flashCardSetDao.getAllSetsDateCreatedOldest()
        }
    }

    val flashCardSetCounts: LiveData<List<Int>> = Transformations.switchMap(sortConfig) {
        when (it) {
            0 -> {
                flashCardSetDao.getCardCountsAZ()
            }
            1 -> {
                flashCardSetDao.getCardCountsZA()
            }
            2 -> {
                flashCardSetDao.getCardCountsDateCreatedNewest()
            }
            3 -> {
                flashCardSetDao.getCardCountsDateCreatedOldest()
            }
            4 -> {
                flashCardSetDao.getCardCountsLastViewedRecently()
            }
            5 -> {
                flashCardSetDao.getCardCountsLastViewedIdle()
            }
            else -> flashCardSetDao.getCardCountsDateCreatedOldest()
        }
    }

    fun changeSort(sortVal: Int) {
        sortConfig.value = sortVal
    }

    suspend fun insertNewSet(flashCardSet: FlashCardSet) {
        flashCardSetDao.insertNewSet(flashCardSet)
    }

    suspend fun updateSet(flashCardSet: FlashCardSet) {
        flashCardSetDao.updateSet(flashCardSet)
    }

    suspend fun deleteSet(flashCardSet: FlashCardSet) {
        flashCardSetDao.deleteSet(flashCardSet)
    }

    init {
        sortConfig.value = 0
    }

    suspend fun getSet(setId: Int): FlashCardSet = withContext(Dispatchers.IO){
        flashCardSetDao.getSet(setId)
    }
}