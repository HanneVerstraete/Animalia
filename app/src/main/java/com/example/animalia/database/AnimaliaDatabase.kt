package com.example.animalia.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.animalia.database.lessons.DatabaseLesson
import com.example.animalia.database.lessons.LessonDatabaseDao
import com.example.animalia.database.questions.DatabaseQuizElement
import com.example.animalia.database.questions.QuizElementDatabaseDao

@Database(entities = [DatabaseLesson::class, DatabaseQuizElement::class], version = 3, exportSchema = false)
abstract class AnimaliaDatabase : RoomDatabase() {
    abstract val lessonDatabaseDao: LessonDatabaseDao
    abstract val quizElementDatabaseDao: QuizElementDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: AnimaliaDatabase? = null

        fun getInstance(context: Context): AnimaliaDatabase {
            var instance = INSTANCE

            if (instance != null) {
                return instance
            }

            synchronized(this) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AnimaliaDatabase::class.java,
                        "animalia_database"
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