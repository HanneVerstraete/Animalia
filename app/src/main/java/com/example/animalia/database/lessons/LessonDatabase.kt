package com.example.animalia.database.lessons

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Lesson::class], version = 1, exportSchema = false)
abstract class LessonDatabase : RoomDatabase() {
    abstract val lessonDatabaseDao: LessonDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: LessonDatabase? = null

        fun getInstance(context: Context): LessonDatabase {
            var instance = INSTANCE

            if (instance != null) {
                return instance
            }

            synchronized(this) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        LessonDatabase::class.java,
                        "lesson_database"
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