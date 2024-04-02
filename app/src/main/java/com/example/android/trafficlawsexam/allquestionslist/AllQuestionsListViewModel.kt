package com.example.android.trafficlawsexam.allquestionslist

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.trafficlawsexam.database.Question
import com.example.android.trafficlawsexam.database.QuestionDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllQuestionsListViewModel(
    private val dao: QuestionDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    private val _questions = MutableLiveData<MutableList<Question>>()
    val questions: MutableLiveData<MutableList<Question>>
        get() = _questions

    init {
        uiScope.launch {
            _questions.value = getAllQuestions()
            Log.e("Test", _questions.value!!.toString())
        }
    }

    private suspend fun getAllQuestions(): MutableList<Question> {
        return withContext(Dispatchers.IO) {
            dao.getAllQuestions()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}