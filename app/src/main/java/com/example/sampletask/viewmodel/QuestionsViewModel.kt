package com.example.sampletask.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampletask.model.QuestionResponse
import com.example.sampletask.repository.NetworkRepository
import com.example.sampletask.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(private val repository: NetworkRepository) :
    ViewModel() {

    var list = MutableLiveData<Resource<List<QuestionResponse>>>()

    fun getQuestionsList() = viewModelScope.launch {
        list.postValue(Resource.Loading(null))
        try {
            val response = repository.getQuestionsList()
            list.postValue(Resource.Success(response))
        } catch (e: IOException) {
            list.postValue(Resource.Error("No Connection", null))
        } catch (e: HttpException) {
            list.postValue(Resource.Error("An Error Occurred", null))
        }

    }

}