package com.hdondiego.flashcards.data

import androidx.room.*
import com.hdondiego.flashcards.models.FlashCard

@Dao
interface FlashCardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewCard(flashCard: FlashCard)

    @Update
    fun updateCard(flashCard: FlashCard)

    @Delete
    fun deleteCard(flashCard: FlashCard)

    /*@Query("SELECT * FROM FlashCard WHERE set_id=:setId")
    fun getCardsInSet(setId: Int) : LiveData<List<FlashCard>>*/

    @Query("SELECT * FROM FlashCard WHERE set_id=:setId")
    fun getCardsInSet(setId: Int) : List<FlashCard>//List<FlashCard>

    /*@Query("UPDATE FlashCard SET term=:newTerm WHERE card_id = :cardId")
    fun updateCard(cardId: Int, newTerm: String)*/

    /*@Query("DELETE FROM FlashCard WHERE card_id=:cardId")
    fun deleteCardInSet(cardId: Int)*/

    @Query("DELETE FROM FlashCard WHERE set_id=:setId")
    fun deleteAllCardsInSet(setId: Int)
}