package com.example.android.trafficlawsexam.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question_table")
data class Question(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0L,

    @ColumnInfo(name = "text")
    var text: String = "",

    @ColumnInfo(name = "first_answer")
    var firstAnswer: String = "",

    @ColumnInfo(name = "second_answer")
    var secondAnswer: String = "",

    @ColumnInfo(name = "third_answer")
    var thirdAnswer: String? = null,

    @ColumnInfo(name = "fourth_answer")
    var fourthAnswer: String? = null,

    @ColumnInfo(name = "correct_answer_number")
    var correctAnswerNumber: Int = -2,

    @ColumnInfo(name = "last_given_answer_number")
    var lastGivenAnswerNumber: Int = -1,

    @ColumnInfo(name = "image_path")
    var imagePath: String? = null
)