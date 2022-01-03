package com.hdondiego.flashcards.viewmodels

import android.provider.MediaStore
import androidx.lifecycle.*
import com.hdondiego.flashcards.models.FlashCardSet
import com.hdondiego.flashcards.data.FlashCardSetRepository
import kotlinx.coroutines.*

class FlashCardSetViewModel(private val flashCardSetRepository: FlashCardSetRepository) : ViewModel() {
    val flashCardSets: LiveData<List<FlashCardSet>> = flashCardSetRepository.flashCardSets //MutableLiveData<List<FlashCardSet>>()
    val flashCardSetCounts: LiveData<List<Int>> = flashCardSetRepository.flashCardSetCounts

    fun insertNewSet(flashCardSet: FlashCardSet) = viewModelScope.launch(Dispatchers.IO) {
        flashCardSetRepository.insertNewSet(flashCardSet)
    }

    fun updateSet(flashCardSet: FlashCardSet) = viewModelScope.launch(Dispatchers.IO) {
        flashCardSetRepository.updateSet(flashCardSet)
    }

    fun deleteSet(flashCardSet: FlashCardSet) = viewModelScope.launch(Dispatchers.IO) {
        flashCardSetRepository.deleteSet(flashCardSet)
    }

    fun changeSort(sortVal: Int) {
        flashCardSetRepository.changeSort(sortVal)
    }

    // DO NOT REMOVE
    fun deleteSet(setId: Int) = viewModelScope.launch(Dispatchers.IO) {
        val set: FlashCardSet = flashCardSetRepository.getSet(setId)
        flashCardSetRepository.deleteSet(set)
    }

    // DO NOT REMOVE
    suspend fun getSet(setId: Int) : FlashCardSet = coroutineScope {
        flashCardSetRepository.getSet(setId)
    }
}
