package com.example.animalia.screens.truefalse

import android.app.Application
import androidx.lifecycle.*
import com.example.animalia.database.AnimaliaDatabase
import com.example.animalia.database.questions.DatabaseQuizElement
import com.example.animalia.domain.QuizElement
import com.example.animalia.repository.QuizElementRepository
import kotlinx.coroutines.*
import kotlin.math.ceil

const val TOTAL_QUESTIONS = 3
// TODO fix issue when playing second time

class TruefalseViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AnimaliaDatabase.getInstance(application.applicationContext)
    private val quizElementRepository = QuizElementRepository(database)

    var questions: MutableList<DatabaseQuizElement> = mutableListOf()

    // TODO remember questions that are already done
    private var currentQuestionIndex = 0

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _currentQuestion = MutableLiveData<QuizElement>()
    val currentQuestion: LiveData<QuizElement>
        get() = _currentQuestion

    private val _numberOfQuestionsAsked = MutableLiveData<Int>()
    val numberOfQuestionsAsked: LiveData<Int>
        get() = _numberOfQuestionsAsked

    private val _goodQuestions = MutableLiveData<Int>()
    val goodQuestions: LiveData<Int>
        get() = _goodQuestions

    private val _shouldEvaluate = MutableLiveData<Boolean>()
    val shouldEvaluate: LiveData<Boolean>
        get() = _shouldEvaluate

    init {
        initializeLiveData()
        _goodQuestions.value = 0
        _numberOfQuestionsAsked.value = 0
    }

    private fun initializeLiveData() {
        uiScope.launch {
            quizElementRepository.refreshQuizElements()
            _currentQuestion.value =
                quizElementRepository.getQuizElementByIndex(currentQuestionIndex)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun answerTrue() {
        if (_currentQuestion.value!!.answer == "Juist") {
            _goodQuestions.value = _goodQuestions.value!! + 1
        }
        increaseIndex()
        getQuestion()
    }

    fun answerFalse() {
        if (_currentQuestion.value!!.answer == "Fout") {
            _goodQuestions.value = _goodQuestions.value!! + 1
        }
        increaseIndex()
        getQuestion()
    }

    private fun increaseIndex() {
        _numberOfQuestionsAsked.value = _numberOfQuestionsAsked.value!! + 1
        currentQuestionIndex++
    }

    private fun getQuestion() {
        if (isEnded()) {
            _shouldEvaluate.value = true
        } else {
            viewModelScope.launch {
                _currentQuestion.value =
                    quizElementRepository.getQuizElementByIndex(currentQuestionIndex)
            }
        }
    }

    fun endGame() {
        _goodQuestions.value = 0
        _numberOfQuestionsAsked.value = 0
        _shouldEvaluate.value = false
        // TODO
//        _currentQuestion.value = ""
    }

    private fun isEnded() = _numberOfQuestionsAsked.value!! >= TOTAL_QUESTIONS

    fun isWon() = _goodQuestions.value!! > ceil((TOTAL_QUESTIONS / 2).toDouble())

}
