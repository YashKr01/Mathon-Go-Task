package com.example.sampletask.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.sampletask.model.QuestionResponse

@Dao
interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(questionResponse: QuestionResponse)

    @Delete
    suspend fun deleteQuestion(questionResponse: QuestionResponse)

}