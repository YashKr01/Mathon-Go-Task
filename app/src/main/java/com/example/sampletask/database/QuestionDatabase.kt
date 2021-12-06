package com.example.sampletask.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.sampletask.model.QuestionResponse

@Database(entities = [QuestionResponse::class], version = 1, exportSchema = false)
@TypeConverters(DataConverter::class)
abstract class QuestionDatabase : RoomDatabase() {

    abstract fun getDao(): QuestionsDao

}