package com.alivakili.ava.trueorfalse.form

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FormModel(
    var amount: Int,
    var difficulty: String,
    var category: Int
) : Parcelable {

    constructor() : this(0, "", 0)

    fun isValid():Boolean{
        return isAmountValid()&&isCategoryValid()&&isDifficultyValid()
    }
    fun isAmountValid():Boolean{
        return amount in 10..50
    }

    fun isDifficultyValid(): Boolean {
        return difficulty=="easy"||difficulty=="medium"||difficulty=="hard"
    }

    fun isCategoryValid(): Boolean {
        return category in 21 .. 26||category==9
    }


}