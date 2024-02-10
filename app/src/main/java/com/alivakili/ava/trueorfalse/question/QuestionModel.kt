package com.alivakili.ava.trueorfalse.question

import android.os.Parcelable
import com.alivakili.ava.trueorfalse.database.QuestionEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuestionModel(
    var id:String="",
    var category: String = "",
    var correctAnswer: String = "",
    var difficulty: String = "",
    var incorrectAnswers: List<String> = listOf(),
    var question: String = "",
    var type: String = "",
    var youAnswered:String=""

) : Parcelable {

    constructor(questionEntity: QuestionEntity) : this(
        id=questionEntity.id,
        correctAnswer = questionEntity.correctAnswer,
        question = questionEntity.question,
        youAnswered = questionEntity.youAnswered
    )
}
