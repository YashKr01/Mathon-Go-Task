package com.example.sampletask.repository

import androidx.lifecycle.LiveData
import com.example.sampletask.database.QuestionsDao
import com.example.sampletask.model.QuestionResponse
import com.example.sampletask.network.ApiInterface
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val api: ApiInterface,
    private val dao: QuestionsDao
) {

    suspend fun getQuestionsList(): List<QuestionResponse> = api.getQuestionResponse()

    suspend fun insertQuestion(question: QuestionResponse) = dao.insertQuestion(question)

    fun getAttemptedQuestionsList(): LiveData<List<QuestionResponse>> = dao.getAttemptedQuestions()

}