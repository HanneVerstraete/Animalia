package com.example.animalia.screens.truefalse

import androidx.databinding.ObservableInt
import androidx.lifecycle.*
import com.example.animalia.R
import com.example.animalia.domain.QuizElement
import com.example.animalia.domain.User
import com.example.animalia.repository.QuizElementRepository
import com.example.animalia.repository.UserRepository
import kotlinx.coroutines.*
import kotlin.math.ceil

const val TOTAL_QUESTIONS = 3

class TruefalseViewModel(
    private val quizElementRepository: QuizElementRepository,
    private val userRepository: UserRepository,
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
    var wonTheGame: Boolean = false

    private val _shouldEvaluate = MutableLiveData<Boolean>()
    val shouldEvaluate: LiveData<Boolean>
        get() = _shouldEvaluate

    var timer: QuestionTimer = QuestionTimer()
    var level: Int = 0
    var xp: Int = 0
    var addedXP: Int = 0

    init {
        initializeLiveData()
        _goodQuestions.value = 0
        _numberOfQuestionsAsked.value = 0
        _shouldEvaluate.value = false
    }

    private fun initializeLiveData() {
        viewModelScope.launch {
            quizElementRepository.refreshQuizElements()
            userRepository.refreshUser()
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

    fun getQuestion() {
        if (isEnded()) {
            handleEndGame()
        } else {
            viewModelScope.launch {
                _currentQuestion.value =
                    quizElementRepository.getQuizElementByIndex(currentQuestionIndex)
            }
        }
    }

    private fun handleEndGame() {
        viewModelScope.launch {
            val user = userRepository.getUser()!!
            if (isWon()) {
                handleWonGame(user)
            } else {
                handleLostGame(user)
            }

            _shouldEvaluate.value = true
            val updatedUser = userRepository.updateUser(user)

            currentQuestionIndex = updatedUser.lastQuestionIndex
            _currentQuestion.value =
                quizElementRepository.getQuizElementByIndex(currentQuestionIndex)
        }
    }

    private fun handleWonGame(user: User) {
        addedXP = (_goodQuestions.value ?: 0) * 2
        xp = user.xp + (2 * (_goodQuestions.value ?: 0))
        level = ceil((xp).toDouble() / 10).toInt()
        resultMessage.set(R.string.textview_question_win)
        wonTheGame = true
        user.lastQuestionIndex = currentQuestionIndex
        user.xp = xp
        user.level = level
    }

    private fun handleLostGame(user: User) {
        addedXP = 0
        xp = user.xp
        level = user.level
        resultMessage.set(R.string.textview_question_lose)
        wonTheGame = false
    }

    fun endGame() {
        _goodQuestions.value = 0
        _numberOfQuestionsAsked.value = 0
        _shouldEvaluate.value = false
        timer.secondsCount = 0
    }

    private fun isEnded() = _numberOfQuestionsAsked.value!! >= TOTAL_QUESTIONS

    fun isWon() = _goodQuestions.value!! > ceil((TOTAL_QUESTIONS / 2).toDouble())

}
