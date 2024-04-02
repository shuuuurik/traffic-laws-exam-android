package com.example.android.trafficlawsexam.examcorrectanswers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.trafficlawsexam.database.Question
import com.example.android.trafficlawsexam.database.QuestionDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExamCorrectAnswersViewModel(
    val numQuestions: Int,
    private val listQuestionsId : LongArray,
    private val dao: QuestionDatabaseDao,
) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope =  CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _currentQuestion = MutableLiveData<Question?>()
    val currentQuestion: LiveData<Question?>
        get() = _currentQuestion

    var answerIndex : MutableLiveData<Int> = MutableLiveData<Int>(-1)

    private val _nextButtonHide = MutableLiveData<Boolean>(false)
    val nextButtonHide: LiveData<Boolean>
        get() = _nextButtonHide

    private val _previousButtonHide = MutableLiveData<Boolean>(false)
    val previousButtonHide: LiveData<Boolean>
        get() = _previousButtonHide

    init {
        nextAnswer()
    }

    fun previousAnswer() {
        uiScope.launch {
            answerIndex.value = answerIndex.value!! - 1

            if (answerIndex.value!! + 1 == numQuestions) {
                _nextButtonHide.value = true
            } else
                _nextButtonHide.value = false

            if (answerIndex.value!! + 1 == 1) {
                _previousButtonHide.value = true
            } else
                _previousButtonHide.value = false

            _currentQuestion.value = get(listQuestionsId[answerIndex.value!!])
            Log.i("Test", _currentQuestion.value!!.toString())
        }
    }

    fun nextAnswer() {
        uiScope.launch {
            answerIndex.value = answerIndex.value!! + 1

            if (answerIndex.value!! + 1 == numQuestions) {
                _nextButtonHide.value = true
            } else
                _nextButtonHide.value = false

            if (answerIndex.value!! + 1 == 1) {
                _previousButtonHide.value = true
            } else
                _previousButtonHide.value = false

            _currentQuestion.value = get(listQuestionsId[answerIndex.value!!])
            Log.i("Test", _currentQuestion.value!!.toString())
        }
    }

    private suspend fun get(idQuestion: Long): Question? {
        return withContext(Dispatchers.IO) {
            dao.get(idQuestion)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}