package com.hdondiego.flashcards.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hdondiego.flashcards.data.FlashCardRepository

class FlashCardViewModelFactory(private val flashCardRepository: FlashCardRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlashCardViewModel::class.java)){
            return FlashCardViewModel(flashCardRepository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}