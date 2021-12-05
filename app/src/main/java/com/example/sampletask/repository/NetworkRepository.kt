package com.example.sampletask.repository

import com.example.sampletask.model.QuestionResponse
import com.example.sampletask.network.ApiInterface
import retrofit2.Response
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val api: ApiInterface) {

    suspend fun getQuestionsList(): List<QuestionResponse> = api.getQuestionResponse()

}