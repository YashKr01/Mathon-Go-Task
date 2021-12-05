package com.example.sampletask.network

import com.example.sampletask.model.QuestionResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET
    suspend fun getQuestionResponse(): List<QuestionResponse>

}