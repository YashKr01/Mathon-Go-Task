package com.example.sampletask.network

import com.example.sampletask.model.QuestionResponse
import retrofit2.http.GET

interface ApiService {

    @GET
    suspend fun getQuestionResponse(): List<QuestionResponse>

}