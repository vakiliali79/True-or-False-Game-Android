package com.alivakili.ava.trueorfalse.form

sealed class FormState {

    object Idle:FormState()
    object Error:FormState()

    data class Success(
        val amount: Int,
        val difficulty: String,
        val category: Int
    ):FormState()

    data class InvalidForm(
        val isAmountValid:Boolean,
        val isCategoryValid:Boolean,
        val isDifficultyValid:Boolean,
    ):FormState()

}