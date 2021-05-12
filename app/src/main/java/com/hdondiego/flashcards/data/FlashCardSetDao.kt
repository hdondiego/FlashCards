package com.hdondiego.flashcards.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hdondiego.flashcards.data.FlashCardSet

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

    @Query("SELECT * FROM FlashCardSet WHERE set_id=:setId")
    suspend fun getSpecificSet(setId: Int): FlashCardSet

    @Query("SELECT * FROM FlashCardSet")
    fun getAllSets(): LiveData<List<FlashCardSet>>

    /*@Query("SELECT * FROM FlashCardSet ORDER BY collection_name DESC")
    fun getAllSetsDesc(): LiveData<List<FlashCardSet>>

    @Query("SELECT * FROM FlashCardSet ORDER BY collection_name ASC")
    fun getAllSetsAsc(): LiveData<List<FlashCardSet>>

    @Query("SELECT * FROM FlashCardSet ORDER BY date_created DESC")
    fun getDateCreatedDesc(): LiveData<List<FlashCardSet>>

    @Query("SELECT * FROM FlashCardSet ORDER BY date_created ASC")
    fun getDateCreatedAsc(): LiveData<List<FlashCardSet>>

    @Query("SELECT * FROM FlashCardSet ORDER BY date_modified DESC")
    fun getDateModifiedDesc(): LiveData<List<FlashCardSet>>

    @Query("SELECT * FROM FlashCardSet ORDER BY date_modified ASC")
    fun getDateModifiedAsc(): LiveData<List<FlashCardSet>>*/

    //@Query("DELETE FROM flashcardcollections WHERE id=")
}