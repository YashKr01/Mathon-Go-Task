package com.example.sampletask.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sampletask.model.QuestionResponse
import java.util.concurrent.Flow

@Dao
interface QuestionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: QuestionResponse)

    @Query("SELECT * FROM MATHON_GO")
    fun getAttemptedQuestions(): LiveData<List<QuestionResponse>>

}