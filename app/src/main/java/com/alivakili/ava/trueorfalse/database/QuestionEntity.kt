package com.alivakili.ava.trueorfalse.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alivakili.ava.trueorfalse.question.QuestionModel

@Entity(tableName="questions")
data class QuestionEntity(
    @PrimaryKey val id:String,
    @ColumnInfo(name="question") val question: String,
    @ColumnInfo(name="correctAnswer") val correctAnswer:String,
    @ColumnInfo(name="youAnswered") val youAnswered:String

){
    constructor(questionModel: QuestionModel):this(
        id =questionModel.id,
        question =questionModel.question,
        correctAnswer = questionModel.correctAnswer,
        youAnswered =questionModel.youAnswered
    )
}
