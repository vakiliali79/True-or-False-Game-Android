package com.alivakili.ava.trueorfalse.question

sealed class QuestionState {
    object Loading: QuestionState()
    data class Success(val questions:List<QuestionModel>): QuestionState()
    object Failure: QuestionState()
    object NoResult:QuestionState()
}