package com.hdondiego.flashcards.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "FlashCardSet")
data class FlashCardSet (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "set_id") var setId: Int = 0,
    @ColumnInfo(name = "collection_name") var collectionName: String,
    @ColumnInfo(name = "date_created") var dateCreated: Long = Date().time,
    @ColumnInfo(name = "date_modified") var dateModified: Long = Date().time
)