package com.hdondiego.flashcards.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.hdondiego.flashcards.models.FlashCardSet

@Dao
interface FlashCardSetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewSet(flashCardSet: FlashCardSet)

    @Update
    suspend fun updateSet(flashCardSet: FlashCardSet)

    @Delete
    suspend fun deleteSet(flashCardSet: FlashCardSet)

    @Query("DELETE FROM FlashCardSet")
    suspend fun deleteAllSets()

    // card counts for each set might not update
    /*@Query("SELECT COUNT(*) FROM FlashCardSet INNER JOIN FlashCard ON FlashCardSet.set_id = FlashCard.set_id GROUP BY FlashCardSet.set_id")
    fun getCardCounts(): LiveData<List<Int>>*/

    // DO NOT DELETE
    @Query("SELECT * FROM FlashCardSet WHERE set_id=:setId")
    suspend fun getSet(setId: Int): FlashCardSet

    // Next Task: For all CardCount functions, they must be a LEFT JOIN and must count the flashcards table's cardId column
    // example: SELECT COUNT(flashcards.cardId) FROM flashcardsets LEFT JOIN flashcards ON flashcardsets.setId = flashcards.setId GROUP BY flashcardsets.setId ORDER BY lastViewed DESC

    @Query("SELECT * FROM FlashCardSet ORDER BY collection_name ASC")
    fun getAllSetsAZ(): LiveData<List<FlashCardSet>> //List<FlashCardSet>

    @Query("SELECT COUNT(FlashCard.card_id) FROM FlashCardSet LEFT JOIN FlashCard ON FlashCardSet.set_id = FlashCard.set_id GROUP BY FlashCardSet.set_id ORDER BY collection_name ASC")
    fun getCardCountsAZ(): LiveData<List<Int>> //List<Int>

    /*-------------------------------------------------------------------------------------------*/

    @Query("SELECT * FROM FlashCardSet ORDER BY collection_name DESC")
    fun getAllSetsZA(): LiveData<List<FlashCardSet>> //List<FlashCardSet>

    @Query("SELECT COUNT(FlashCard.card_id) FROM FlashCardSet LEFT JOIN FlashCard ON FlashCardSet.set_id = FlashCard.set_id GROUP BY FlashCardSet.set_id ORDER BY collection_name DESC")
    fun getCardCountsZA(): LiveData<List<Int>> //List<Int>

    /*-------------------------------------------------------------------------------------------*/

    // largest (more recent) epoch millisecond time to smallest (in the past)
    @Query("SELECT * FROM FlashCardSet ORDER BY date_created DESC")
    fun getAllSetsDateCreatedNewest(): LiveData<List<FlashCardSet>> //List<FlashCardSet>

    @Query("SELECT COUNT(FlashCard.card_id) FROM FlashCardSet LEFT JOIN FlashCard ON FlashCardSet.set_id = FlashCard.set_id GROUP BY FlashCardSet.set_id ORDER BY date_created DESC")
    fun getCardCountsDateCreatedNewest(): LiveData<List<Int>> //List<Int>

    /*-------------------------------------------------------------------------------------------*/

    // basically the equivalent of getAllSets
    // smallest (in the past) epoch millisecond time to largest (more recent)
    @Query("SELECT * FROM FlashCardSet ORDER BY date_created ASC")
    fun getAllSetsDateCreatedOldest(): LiveData<List<FlashCardSet>> //List<FlashCardSet>

    @Query("SELECT COUNT(FlashCard.card_id) FROM FlashCardSet LEFT JOIN FlashCard ON FlashCardSet.set_id = FlashCard.set_id GROUP BY FlashCardSet.set_id ORDER BY date_created ASC")
    fun getCardCountsDateCreatedOldest(): LiveData<List<Int>> //List<Int>

    /*-------------------------------------------------------------------------------------------*/

    // largest (more recent) epoch millisecond time to smallest (in the past)
    @Query("SELECT * FROM FlashCardSet ORDER BY last_viewed DESC")
    fun getAllSetsLastViewedRecently(): LiveData<List<FlashCardSet>> //List<FlashCardSet>

    @Query("SELECT COUNT(FlashCard.card_id) FROM FlashCardSet LEFT JOIN FlashCard ON FlashCardSet.set_id = FlashCard.set_id GROUP BY FlashCardSet.set_id ORDER BY last_viewed DESC")
    fun getCardCountsLastViewedRecently(): LiveData<List<Int>> //List<Int>

    /*-------------------------------------------------------------------------------------------*/

    // smallest (in the past) epoch millisecond time to largest (more recent)
    @Query("SELECT * FROM FlashCardSet ORDER BY last_viewed ASC")
    fun getAllSetsLastViewedIdle(): LiveData<List<FlashCardSet>> //List<FlashCardSet>

    @Query("SELECT COUNT(FlashCard.card_id) FROM FlashCardSet LEFT JOIN FlashCard ON FlashCardSet.set_id = FlashCard.set_id GROUP BY FlashCardSet.set_id ORDER BY last_viewed ASC")
    fun getCardCountsLastViewedIdle(): LiveData<List<Int>> //List<Int>
}