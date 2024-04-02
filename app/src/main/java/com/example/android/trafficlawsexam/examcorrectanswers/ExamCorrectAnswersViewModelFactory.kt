package com.example.android.trafficlawsexam.examcorrectanswers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.trafficlawsexam.database.QuestionDatabaseDao

class ExamCorrectAnswersViewModelFactory(
    private val numQuestions: Int,
    private val listQuestionsId : LongArray,
    private val dao: QuestionDatabaseDao,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExamCorrectAnswersViewModel::class.java)) {
            return ExamCorrectAnswersViewModel(numQuestions, listQuestionsId, dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}