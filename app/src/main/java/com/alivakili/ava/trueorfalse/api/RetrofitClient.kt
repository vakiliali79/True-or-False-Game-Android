package com.alivakili.ava.trueorfalse.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object RetrofitClient {
    private const val baseUrl="https://opentdb.com/"
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    object ApiClient {
        val apiService: Endpoint by lazy {
            retrofit.create(Endpoint::class.java)
        }
    }

}

interface Endpoint {
    @GET("api.php")
    fun getQuestions(
        @Query("amount")amount:Int,
        @Query("type")type: String,
        @Query("difficulty")difficulty:String,
        @Query("category")category:Int,
    ): Call<QuestionAnswerDTO>
}
