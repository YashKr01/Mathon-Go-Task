package com.example.sampletask.network

import com.example.sampletask.model.QuestionResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("secure.notion-static.com/3e55add8-7606-43fb-b739-49d6e57b1383/JEE_Main_-_Gravitation.json?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20211205%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20211205T191054Z&X-Amz-Expires=86400&X-Amz-Signature=562fe0d1ac1e1634e1bd19c84928ac3eba742b68cf5544ee4d8ff7888aabcbb6&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22JEE%2520Main%2520-%2520Gravitation.json%22&x-id=GetObject")
    suspend fun getQuestionResponse(): List<QuestionResponse>

}