package com.alivakili.ava.trueorfalse.results

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.alivakili.ava.trueorfalse.database.QuestionDao
import com.alivakili.ava.trueorfalse.question.QuestionModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import java.util.UUID

class ResultViewModel (private val dao: QuestionDao) : ViewModel() {

    private val _state: MutableStateFlow<ResultState> = MutableStateFlow(ResultState.Loading)
    val state: StateFlow<ResultState> = _state

    init {
        retrieveAllQuestions()
    }

    private fun retrieveAllQuestions() {
        viewModelScope.launch {
            val question = dao.get().map(::QuestionModel)
            _state.value = ResultState.QuestionList(question)
        }
    }


    fun onCorrectClicked() {
        viewModelScope.launch {
            val newList = dao.getCorrectAnswerResults().map(::QuestionModel)
            _state.value = ResultState.QuestionList(newList)
        }
    }

    fun onIncorrectClicked() {
        viewModelScope.launch {
            val newList = dao.getIncorrectAnswerResults().map(::QuestionModel)
            _state.value = ResultState.QuestionList(newList)
        }
    }

    fun onAllClicked(){
        viewModelScope.launch {
            val newList = dao.get().map(::QuestionModel)
            _state.value = ResultState.QuestionList(newList)
        }
    }

    companion object {
        fun factory(dao: QuestionDao): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ResultViewModel(dao) as T
                }
            }
        }
    }
}
