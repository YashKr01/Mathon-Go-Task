package com.example.sampletask.repository

import com.example.sampletask.database.QuestionsDao
import com.example.sampletask.model.QuestionResponse
import com.example.sampletask.network.ApiInterface
import retrofit2.Response
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val api: ApiInterface,
    private val dao: QuestionsDao
) {

    suspend fun getQuestionsList(): List<QuestionResponse> = api.getQuestionResponse()

    suspend fun insertQuestion(question: QuestionResponse) = dao.insertQuestion(question)

}