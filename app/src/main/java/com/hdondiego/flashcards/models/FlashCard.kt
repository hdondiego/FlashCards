package com.hdondiego.flashcards.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "FlashCard")
data class FlashCard (
    @PrimaryKey (autoGenerate = true) @ColumnInfo(name = "card_id") var cardId: Int = 0,
    @ColumnInfo(name = "term") var term: String,
    @ColumnInfo(name = "definition") var def: String,
    @ColumnInfo(name = "front") var front: Boolean = true,
    @ForeignKey(entity = FlashCardSet::class, parentColumns = ["set_id"], childColumns = ["set_id"], onDelete = ForeignKey.CASCADE)
    @ColumnInfo(name = "set_id")var setId: Int
)