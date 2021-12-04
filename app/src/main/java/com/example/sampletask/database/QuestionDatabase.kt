package com.example.sampletask.database

import androidx.room.Database
import com.example.sampletask.model.QuestionResponse

@Database(entities = [QuestionResponse::class], version = 1, exportSchema = false)
abstract class QuestionDatabase {

    abstract fun getQuestionDao(): QuestionDao

}