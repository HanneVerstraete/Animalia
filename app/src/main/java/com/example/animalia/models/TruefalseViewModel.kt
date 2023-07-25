package com.example.animalia.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animalia.QuestionBook
import kotlin.math.ceil

const val TOTAL_QUESTIONS = 3

class TruefalseViewModel : ViewModel() {
    private var myQuestionBook = QuestionBook()
    private var currentQuestionIndex = 0

    private var _currentQuestion = MutableLiveData("")
    val currentQuestion: LiveData<String>
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
        _goodQuestions.value = 0
        _numberOfQuestionsAsked.value = 0
        _currentQuestion.value = myQuestionBook.getQuestion(currentQuestionIndex)
    }

    fun answerTrue() {
        if (myQuestionBook.isAnswerTrue(currentQuestionIndex)) {
            _goodQuestions.value = _goodQuestions.value!! + 1
        }
        increaseIndex()
        getQuestion()
    }

    fun answerFalse() {
        if (myQuestionBook.isAnswerFalse(currentQuestionIndex)) {
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
            _currentQuestion.value = myQuestionBook.getQuestion(currentQuestionIndex)
        }
    }

    fun endGame() {
        _goodQuestions.value = 0
        _numberOfQuestionsAsked.value = 0
        _shouldEvaluate.value = false
        _currentQuestion.value = myQuestionBook.getQuestion(currentQuestionIndex)
    }

    private fun isEnded() = _numberOfQuestionsAsked.value!! >= TOTAL_QUESTIONS

    fun isWon() = _goodQuestions.value!! > ceil((TOTAL_QUESTIONS / 2).toDouble())
}
