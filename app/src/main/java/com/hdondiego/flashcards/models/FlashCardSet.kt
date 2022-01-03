package com.hdondiego.flashcards.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

//import java.util.Date



@Entity(tableName = "FlashCardSet")
data class FlashCardSet (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "set_id") var setId: Int = 0,
    @ColumnInfo(name = "collection_name") var collectionName: String,
    @ColumnInfo(name = "date_created") var dateCreated: Instant = Clock.System.now(),
    @ColumnInfo(name = "last_viewed") var lastViewed: Instant = Instant.fromEpochMilliseconds(0)//Clock.System.now()
)