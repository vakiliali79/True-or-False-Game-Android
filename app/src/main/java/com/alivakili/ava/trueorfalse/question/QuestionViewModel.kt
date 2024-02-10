package com.alivakili.ava.trueorfalse.question

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.alivakili.ava.trueorfalse.api.QuestionAnswerDTO
import com.alivakili.ava.trueorfalse.api.RetrofitClient
import com.alivakili.ava.trueorfalse.database.QuestionDao
import com.alivakili.ava.trueorfalse.database.QuestionEntity
import com.alivakili.ava.trueorfalse.form.FormModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.util.UUID

class QuestionViewModel(private val dao: QuestionDao) : ViewModel() {
    private val _state: MutableStateFlow<QuestionState> = MutableStateFlow(QuestionState.Loading)
    val state: StateFlow<QuestionState> = _state

    fun retrieveNews(form: FormModel?) {
        viewModelScope.launch {
            val call = RetrofitClient.ApiClient.apiService.getQuestions(
                form!!.amount,
                "boolean",
                form.difficulty,
                form.category
            )

            call.enqueue(object : Callback<QuestionAnswerDTO> {
                override fun onResponse(
                    call: Call<QuestionAnswerDTO>,
                    response: Response<QuestionAnswerDTO>
                ) {
                    Log.e(TAG, "onResponse: ${response.body()}", )
                    when (response.body()?.responseCode) {
                        0 -> {
                            val body = response.body()
                            val questions = body?.results?.map { dto ->
                                QuestionModel(
                                    category = dto?.category!!,
                                    correctAnswer = dto.correctAnswer!!,
                                    difficulty = dto.difficulty!!,
                                    incorrectAnswers = dto.incorrectAnswers!! as List<String>,
                                    question = dto.question!!,
                                    type = dto.type!!,
                                )
                            }
                            _state.value = QuestionState.Success(
                                questions!!
                            )
                        }

                        else -> {
                            _state.value = QuestionState.NoResult

                        }
                    }


                }

                override fun onFailure(call: Call<QuestionAnswerDTO>, t: Throwable) {
                    Log.e(TAG, "onResponse: ${t.cause}", )
                    Log.e(TAG, "onResponse: ${t.localizedMessage}", )
                    Log.e(TAG, "onResponse: ${t.message}", )
                    _state.value = QuestionState.Failure
                }
            })

        }
    }

    fun deleteDataBase(){
        viewModelScope.launch {
            dao.deleteAll()
        }
    }

    fun questionAnswered(question: QuestionModel) {
        viewModelScope.launch {
            val question = QuestionModel(
                question = question.question,
                correctAnswer = question.correctAnswer.lowercase(),
                type = question.type,
                youAnswered = question.youAnswered.lowercase(),
                difficulty = question.difficulty,
                category = question.category,
                id = UUID.randomUUID().toString(),
            )
            dao.insert(QuestionEntity(question ))
        }
    }


    companion object {
        fun factory(dao: QuestionDao): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return QuestionViewModel(dao) as T
                }
            }
        }
    }


}