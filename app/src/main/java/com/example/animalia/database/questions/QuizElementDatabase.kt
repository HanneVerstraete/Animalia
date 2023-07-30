package com.example.animalia.database.questions

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [QuizElement::class], version = 1, exportSchema = false)
abstract class QuizElementDatabase : RoomDatabase() {

    abstract val quizElementDatabaseDao: QuizElementDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: QuizElementDatabase? = null

        fun getInstance(context: Context): QuizElementDatabase {
            var instance = INSTANCE

            if (instance != null) {
                return instance
            }

            synchronized(this) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        QuizElementDatabase::class.java,
                        "quiz_element_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
            }

            return instance!!
        }
    }
}