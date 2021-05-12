package com.hdondiego.flashcards.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hdondiego.flashcards.data.FlashCardSetRepository

class FlashCardSetViewModelFactory(private val flashCardSetRepository: FlashCardSetRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlashCardSetViewModel::class.java)){
            return FlashCardSetViewModel(flashCardSetRepository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}