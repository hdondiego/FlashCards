package com.hdondiego.flashcards.data

import androidx.room.TypeConverter
import kotlinx.datetime.Instant

// Source: https://developer.android.com/training/data-storage/room/referencing-data
class DateTimeConverter {
    @TypeConverter
    fun fromEpochMilliseconds(value: Long?): Instant? {
        return value?.let { Instant.fromEpochMilliseconds(value) }
    }

    @TypeConverter
    fun toEpochMilliseconds(instant: Instant?): Long? {
        return instant?.toEpochMilliseconds()
    }
}