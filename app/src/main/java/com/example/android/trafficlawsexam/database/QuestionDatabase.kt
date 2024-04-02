package com.example.android.trafficlawsexam.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(entities = [Question::class], version = 1, exportSchema = false)
abstract class QuestionDatabase : RoomDatabase() {

    abstract fun getQuestionDatabaseDao(): QuestionDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: QuestionDatabase? = null

        fun getInstance(context: Context): QuestionDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = databaseBuilder(context.applicationContext,
                        QuestionDatabase::class.java, "exam_questions_db")
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}