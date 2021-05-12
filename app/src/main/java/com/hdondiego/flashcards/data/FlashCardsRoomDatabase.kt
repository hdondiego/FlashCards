package com.hdondiego.flashcards.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version = 1, entities = [FlashCardSet::class, FlashCard::class], exportSchema = false)
abstract class FlashCardsRoomDatabase : RoomDatabase() {

    // Difference:
    abstract fun flashCardSetDao(): FlashCardSetDao //val
    abstract fun flashCardDao(): FlashCardDao //val

    companion object {
        @Volatile
        private var INSTANCE: FlashCardsRoomDatabase? = null

        fun getInstance(context: Context): FlashCardsRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FlashCardsRoomDatabase::class.java,
                        "flashcards_db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }


    /*abstract fun getFlashCardSetDao() : FlashCardSetDao
    abstract fun getFlashCardDao() : FlashCardDao

    companion object {
        // singleton
        @Volatile
        private var INSTANCE: FlashCardsRoomDatabase? = null

        fun getDatabase(context: Context) : FlashCardsRoomDatabase { // application: Application
            val tempInstance =
                INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,
                    FlashCardsRoomDatabase::class.java, "flashcards_db").build()
                INSTANCE = instance
                return instance
            }

            //return Room.databaseBuilder(application, FlashCardsRoomDatabase::class.java, "flashcards")
            //    .build()
        }
    }*/
}