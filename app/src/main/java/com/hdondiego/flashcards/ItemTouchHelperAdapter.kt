package com.hdondiego.flashcards

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int)

    fun onItemSwiped(position: Int)
}