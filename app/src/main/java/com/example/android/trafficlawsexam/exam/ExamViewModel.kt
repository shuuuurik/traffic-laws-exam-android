package com.example.android.trafficlawsexam.exam

import android.app.Application
import android.os.CountDownTimer
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

class ExamViewModel(
    private val dao: QuestionDatabaseDao,
    application: Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private lateinit var questions : MutableList<Question>
    private var _listQuestionsId = mutableListOf<Long>()
    val listQuestionsId: MutableList<Long>
        get() = _listQuestionsId

    private val _currentQuestion = MutableLiveData<Question>()
    val currentQuestion: LiveData<Question?>
        get() = _currentQuestion

    var questionIndex : MutableLiveData<Int> = MutableLiveData<Int>(0)
    var numQuestions : MutableLiveData<Int> = MutableLiveData<Int>(20)
    private var correctAnswersNumber = 0

    private var _additionalQuestionsCount = 0
    val additionalQuestionsCount: Int
        get() = _additionalQuestionsCount

    private val _areQuestionsAdded = MutableLiveData<Boolean>(false)
    val areQuestionsAdded: LiveData<Boolean?>
        get() = _areQuestionsAdded

    fun notifyAdditionalQuestions() {
        _areQuestionsAdded.value = false
    }

    private val _navigateToExamPassed = MutableLiveData<Boolean>(false)
    val navigateToExamPassed: LiveData<Boolean?>
        get() = _navigateToExamPassed

    fun doneExamPassedNavigating() {
        _navigateToExamPassed.value = false
    }

    private val _navigateToExamFailed = MutableLiveData<Boolean>(false)
    val navigateToExamFailed: LiveData<Boolean?>
        get() = _navigateToExamFailed

    fun doneExamFailedNavigating() {
        _navigateToExamFailed.value = false
    }

    companion object {
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val GAME_TIME = 1200 * ONE_SECOND
    }

    private var timer: CountDownTimer

    private var _secondsUntilEnd = MutableLiveData<Long>()
    val secondsUntilEnd: LiveData<Long>
        get() = _secondsUntilEnd

    init {
        _secondsUntilEnd.value = GAME_TIME / ONE_SECOND
        timer = object : CountDownTimer(GAME_TIME, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                _secondsUntilEnd.value = millisUntilFinished / ONE_SECOND
            }
            override fun onFinish() {
                numQuestions.value = questionIndex.value
                _navigateToExamFailed.value = true
            }
        }
        timer.start()
        uiScope.launch {
            questions = getAllQuestions()
            randomizeQuestions()
        }
    }

    private suspend fun getAllQuestions(): MutableList<Question> {
        return withContext(Dispatchers.IO) {
            dao.getAllQuestions()
        }
    }

    private suspend fun insert(question: Question) {
        withContext(Dispatchers.IO) {
            dao.insert(question)
        }
    }

    // randomize the questions and set the first question
    private fun randomizeQuestions() {
        questions.shuffle()
        questionIndex.value = 0
        setQuestion()
    }

    private fun setQuestion() {
        _currentQuestion.value = questions[questionIndex.value!!]
        _listQuestionsId.add(questionIndex.value!!, _currentQuestion.value!!.id)
    }

    fun onSubmit(checkedNumber : Int) {
        uiScope.launch {
            val oldQuestion = currentQuestion.value ?: return@launch
            if (oldQuestion.correctAnswerNumber == checkedNumber)
                correctAnswersNumber++

            if (questionIndex.value!! + 1 == 20) {
                when (correctAnswersNumber) {
                    20 -> _navigateToExamPassed.value = true
                    19 -> {
                        _additionalQuestionsCount = 5
                        numQuestions.value = 25
                        timer.cancel()
                        timer = object : CountDownTimer((_secondsUntilEnd.value!! + 300) * ONE_SECOND, ONE_SECOND) {
                            override fun onTick(millisUntilFinished: Long) {
                                _secondsUntilEnd.value = millisUntilFinished / ONE_SECOND
                            }
                            override fun onFinish() {
                                numQuestions.value = questionIndex.value
                                _navigateToExamFailed.value = true
                            }
                        }
                        timer.start()
                        _areQuestionsAdded.value = true
                    }
                    18 -> {
                        _additionalQuestionsCount = 10
                        numQuestions.value = 30
                        timer.cancel()
                        timer = object : CountDownTimer((_secondsUntilEnd.value!! + 600) * ONE_SECOND, ONE_SECOND) {
                            override fun onTick(millisUntilFinished: Long) {
                                _secondsUntilEnd.value = millisUntilFinished / ONE_SECOND
                            }
                            override fun onFinish() {
                                numQuestions.value = questionIndex.value
                                _navigateToExamFailed.value = true
                            }
                        }
                        timer.start()
                        _areQuestionsAdded.value = true
                    }
                    in 0..17 -> {_navigateToExamFailed.value = true }
                }
            }
            else if (questionIndex.value!! + 1 == 25 && numQuestions.value!! == 25)
                when (correctAnswersNumber) {
                    24 -> _navigateToExamPassed.value = true
                    else -> _navigateToExamFailed.value = true
                }
            else if (questionIndex.value!! + 1 == 30 && numQuestions.value!! == 30)
                when (correctAnswersNumber) {
                    28 -> _navigateToExamPassed.value = true
                    else -> _navigateToExamFailed.value = true
                }
            Log.i("Test", "Correct answers number: ${correctAnswersNumber}")
            oldQuestion.lastGivenAnswerNumber = checkedNumber
            questions[questionIndex.value!!] = oldQuestion
            questionIndex.value = questionIndex.value!! + 1
            update(oldQuestion)
            setQuestion()
        }
    }

    private suspend fun update(question: Question) {
        withContext(Dispatchers.IO) {
            dao.update(question)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}