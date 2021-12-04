package com.example.sampletask.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sampletask.model.QuestionResponse

@Database(entities = [QuestionResponse::class], version = 1, exportSchema = false)
abstract class QuestionDatabase : RoomDatabase() {

    abstract fun getQuestionDao(): QuestionDao

}