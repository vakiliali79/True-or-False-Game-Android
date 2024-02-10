package com.alivakili.ava.trueorfalse.api


import com.google.gson.annotations.SerializedName

data class QuestionAnswerDTO(
    @SerializedName("response_code")
    val responseCode: Int? = 0,
    @SerializedName("results")
    val results: List<com.alivakili.ava.trueorfalse.api.QuestionAnswerDTO.Result?>? = listOf()
) {
    data class Result(
        @SerializedName("category")
        val category: String? = "",
        @SerializedName("correct_answer")
        val correctAnswer: String? = "",
        @SerializedName("difficulty")
        val difficulty: String? = "",
        @SerializedName("incorrect_answers")
        val incorrectAnswers: List<String?>? = listOf(),
        @SerializedName("question")
        val question: String? = "",
        @SerializedName("type")
        val type: String? = ""
    )
}