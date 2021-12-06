package com.example.sampletask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampletask.model.QuestionResponse
import com.example.sampletask.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionDetailViewModel @Inject constructor(private val repository: NetworkRepository) :
    ViewModel() {

    fun insertQuestion(question: QuestionResponse) = viewModelScope.launch {
        repository.insertQuestion(question)
    }

}