package com.example.animalia.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animalia.QuestionBook
import kotlin.math.ceil

const val TOTAL_QUESTIONS = 3

class TruefalseViewModel : ViewModel() {
    private var myQuestionBook = QuestionBook()

    private var _currentQuestion = MutableLiveData("")
    val currentQuestion: LiveData<String>
        get() = _currentQuestion

    private val _shouldEvaluate = MutableLiveData<Boolean>()
    val shouldEvaluate: LiveData<Boolean>
        get() = _shouldEvaluate

    private val _goodQuestions = MutableLiveData<Int>()
    val goodQuestions: LiveData<Int>
        get() = _goodQuestions

    private val _currentQuestionIndex = MutableLiveData<Int>()
    val currentQuestionIndex: LiveData<Int>
        get() = _currentQuestionIndex

    init {
        _currentQuestion.value = myQuestionBook.getQuestion(0)
        _goodQuestions.value = 0
        _currentQuestionIndex.value = 0
    }

    fun answerTrue() {
        if (myQuestionBook.isAnswerTrue(_currentQuestionIndex.value!!)) {
            _goodQuestions.value = _goodQuestions.value!! + 1
        }
        getNextQuestion()
    }

    fun answerFalse() {
        if (myQuestionBook.isAnswerFalse(_currentQuestionIndex.value!!)) {
            _goodQuestions.value = _goodQuestions.value!! + 1
        }
        getNextQuestion()
    }

    private fun getNextQuestion() {
        _currentQuestionIndex.value = _currentQuestionIndex.value!! + 1
        if (isEnded()) {
            _shouldEvaluate.value = true
        } else {
            _currentQuestion.value = myQuestionBook.getQuestion(_currentQuestionIndex.value!!)
        }
    }

    private fun isEnded() = _currentQuestionIndex.value!! >= TOTAL_QUESTIONS
    fun isWon() = _goodQuestions.value!! > ceil((TOTAL_QUESTIONS / 2).toDouble())
}
