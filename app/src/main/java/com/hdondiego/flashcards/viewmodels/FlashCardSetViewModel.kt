package com.hdondiego.flashcards.viewmodels

import androidx.lifecycle.*
import com.hdondiego.flashcards.data.FlashCardSet
import com.hdondiego.flashcards.data.FlashCardSetRepository
import kotlinx.coroutines.*

class FlashCardSetViewModel(private val flashCardSetRepository: FlashCardSetRepository) : ViewModel() {
    val flashCardSets = flashCardSetRepository.allFlashCardSets
    //val returnedSet = MutableLiveData<FlashCardSet>()

    fun insertNewSet(flashCardSet: FlashCardSet) = viewModelScope.launch(Dispatchers.IO) {
        flashCardSetRepository.insertNewSet(flashCardSet)
    }

    fun updateSet(flashCardSet: FlashCardSet) = viewModelScope.launch(Dispatchers.IO) {
        flashCardSetRepository.updateSet(flashCardSet)
    }

    fun deleteSet(flashCardSet: FlashCardSet) = viewModelScope.launch(Dispatchers.IO) {
        flashCardSetRepository.deleteSet(flashCardSet)
    }

    /*
    fun getSpecificSet(setId: Int): MutableLiveData<FlashCardSet> {
        var returnedSet = MutableLiveData<FlashCardSet>()
        viewModelScope.launch(Dispatchers.IO) {
            returnedSet.postValue(flashCardSetRepository.getSpecificSet(setId))
        }
        return returnedSet
    }
     */
    // DO NOT REMOVE
    fun deleteSet(setId: Int) = viewModelScope.launch(Dispatchers.IO) {
        val set: FlashCardSet = flashCardSetRepository.getSet(setId)
        flashCardSetRepository.deleteSet(set)
        ///val flashCardSet: FlashCardSet = FlashCardSet(set.value!!.setId, )
    }

    // not needed
    /*fun deleteSet(setId: Int) = viewModelScope.launch(Dispatchers.IO) {
        launch {
            val set: FlashCardSet? = flashCardSetRepository.getSet(setId).value
            flashCardSetRepository.deleteSet(set)
        }
        ///val flashCardSet: FlashCardSet = FlashCardSet(set.value!!.setId, )
    }*/

    /*fun getSpecificSet(setId: Int) : FlashCardSet {
        var set : FlashCardSet = FlashCardSet(0,"")
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.IO) {
                set = flashCardSetRepository.getSpecificSet(setId)
            }

        }
        return set
    }*/

    // DO NOT REMOVE
    suspend fun getSet(setId: Int) : FlashCardSet = coroutineScope {
        flashCardSetRepository.getSet(setId)
    }

    // not needed
    /*fun getSet(setId: Int) : LiveData<FlashCardSet> = withContext(Dispatchers.IO) {
        flashCardSetRepository.getSet(setId)
    }*/

    //TEST
    //fun getSpecificSet(setId: Int) : FlashCardSet

    /*
    fun getSpecificSet(setId: Int) {
        viewModelScope.launch {
            val set = flashCardSetRepository.getSpecificSet(setId)
            //resultSet.value = set
            resultSet.postValue(set)
        }
    }
    */

    /*suspend fun getSpecificSet(setId: Int) : FlashCardSet = viewModelScope.launch {
        withContext(Dispatchers.IO){
            return flashCardSetRepository.getSpecificSet(setId)
        }
    }*/
}
