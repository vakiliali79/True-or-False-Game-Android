package com.alivakili.ava.trueorfalse.results

import com.alivakili.ava.trueorfalse.question.QuestionModel

sealed class ResultState {
    object Loading: ResultState()
    data class QuestionList(val questions:List<QuestionModel>): ResultState()

}