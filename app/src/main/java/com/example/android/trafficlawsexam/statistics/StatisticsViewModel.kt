package com.example.android.trafficlawsexam.statistics

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.trafficlawsexam.database.QuestionDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StatisticsViewModel(
    private val dao: QuestionDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    private val _learnedQuestionsNumber = MutableLiveData<Int>()
    val learnedQuestionsNumber: MutableLiveData<Int>
        get() = _learnedQuestionsNumber

    private val _allQuestionsNumber = MutableLiveData<Int>()
    val allQuestionsNumber: MutableLiveData<Int>
        get() = _allQuestionsNumber

    private val _learningProgress = MutableLiveData<Int>()
    val learningProgress: MutableLiveData<Int>
        get() = _learningProgress

    init {
        uiScope.launch {
            _learnedQuestionsNumber.value = getLearnedQuestionsNumber()
            _allQuestionsNumber.value = getAllQuestionsNumber()
            _learningProgress.value = (_learnedQuestionsNumber.value!!.toDouble() / _allQuestionsNumber.value!! * 100).toInt()
        }
    }

    private suspend fun getLearnedQuestionsNumber(): Int{
        return withContext(Dispatchers.IO) {
            dao.getLearnedQuestionsNumber()
        }
    }

    private suspend fun getAllQuestionsNumber(): Int{
        return withContext(Dispatchers.IO) {
            dao.getAllQuestionsNumber()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}