package com.example.animalia.screens.truefalse

import androidx.databinding.ObservableInt
import androidx.lifecycle.*
import com.example.animalia.R
import com.example.animalia.domain.QuizElement
import com.example.animalia.repository.QuizElementRepository
import kotlinx.coroutines.*
import kotlin.math.ceil

const val TOTAL_QUESTIONS = 3

class TruefalseViewModel(
    private val quizElementRepository: QuizElementRepository,
    private var currentQuestionIndex: Int
) : ViewModel() {

    private var _currentQuestion = MutableLiveData<QuizElement>()
    val currentQuestion: LiveData<QuizElement>
        get() = _currentQuestion

    private val _numberOfQuestionsAsked = MutableLiveData<Int>()
    val numberOfQuestionsAsked: LiveData<Int>
        get() = _numberOfQuestionsAsked

    private val _goodQuestions = MutableLiveData<Int>()
    val goodQuestions: LiveData<Int>
        get() = _goodQuestions

    var resultMessage = ObservableInt()

    private val _shouldEvaluate = MutableLiveData<Boolean>()
    val shouldEvaluate: LiveData<Boolean>
        get() = _shouldEvaluate

    init {
        initializeLiveData()
        _goodQuestions.value = 0
        _numberOfQuestionsAsked.value = 0
        _shouldEvaluate.value = false
    }

    private fun initializeLiveData() {
        viewModelScope.launch {
            quizElementRepository.refreshQuizElements()
            _currentQuestion.value =
                quizElementRepository.getQuizElementByIndex(currentQuestionIndex)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
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
            if (isWon()) {
                resultMessage.set(R.string.textview_question_win)
            } else {
                resultMessage.set(R.string.textview_question_lose)
            }
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
    }

    private fun isEnded() = _numberOfQuestionsAsked.value!! >= TOTAL_QUESTIONS

    fun isWon() = _goodQuestions.value!! > ceil((TOTAL_QUESTIONS / 2).toDouble())

}
