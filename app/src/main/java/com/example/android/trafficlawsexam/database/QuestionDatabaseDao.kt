package com.example.android.trafficlawsexam.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface QuestionDatabaseDao {
//    @Query ("insert into question_table (text, first_answer, second_answer, third_answer, fourth_answer," +
//        "correct_answer_number_index, last_given_answer_index, image_path) values (:question)")
    @Insert
    fun insert(question: Question)

    @Update
    fun update(night: Question)

    @Query("SELECT * FROM question_table WHERE id = :key")
    fun get(key: Long): Question?

    @Query("DELETE FROM question_table")
    fun clear()

    @Query("SELECT * FROM question_table")
    fun getAllQuestions(): MutableList<Question> // LiveData<List<Question>>

    @Query("SELECT * FROM question_table ORDER BY id DESC LIMIT 1")
    fun getLastQuestion(): Question?

    @Query("SELECT COUNT(*) FROM question_table WHERE correct_answer_number = last_given_answer_number")
    fun getLearnedQuestionsNumber(): Int

    @Query("SELECT COUNT(*) FROM question_table")
    fun getAllQuestionsNumber(): Int

}