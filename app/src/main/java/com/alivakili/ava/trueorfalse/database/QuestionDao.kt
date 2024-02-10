package com.alivakili.ava.trueorfalse.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface QuestionDao {
    @Query("SELECT * FROM questions")
    suspend fun get():List<QuestionEntity>


    @Query("SELECT * FROM questions WHERE youAnswered = correctAnswer")
    suspend fun getCorrectAnswerResults(): List<QuestionEntity>


    @Query("SELECT * FROM questions WHERE youAnswered != correctAnswer")
    suspend fun getIncorrectAnswerResults(): List<QuestionEntity>

    @Insert
    suspend fun insert(question: QuestionEntity)

    @Update
    suspend fun update(question: QuestionEntity)

    @Query("DELETE FROM questions")
    suspend fun deleteAll()
}
