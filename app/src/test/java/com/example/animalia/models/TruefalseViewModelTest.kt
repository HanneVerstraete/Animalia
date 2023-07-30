package com.example.animalia.models

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.animalia.QuestionBook
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

//TO DO fix test
@RunWith(JUnit4::class)
class TruefalseViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private var questionBook = QuestionBook()

    @Test
    fun `when viewModel is initialized, all variables should be set to correct values`() {
        val viewModel = TruefalseViewModel()

        assertEquals(0, viewModel.goodQuestions.value)
        assertEquals(0, viewModel.numberOfQuestionsAsked.value)
        assertEquals(questionBook.getQuestion(0), viewModel.currentQuestion.value)
    }

    @Test
    fun `when answering correct, good questions should be increased`() {
        val viewModel = TruefalseViewModel()

        viewModel.answerFalse()
        assertEquals(1, viewModel.goodQuestions.value)
        assertEquals(1, viewModel.numberOfQuestionsAsked.value)
        assertEquals(questionBook.getQuestion(1), viewModel.currentQuestion.value)

        viewModel.answerTrue()
        assertEquals(2, viewModel.goodQuestions.value)
        assertEquals(2, viewModel.numberOfQuestionsAsked.value)
        assertEquals(questionBook.getQuestion(2), viewModel.currentQuestion.value)
    }

    @Test
    fun `when answering incorrect, good questions should not be increased`() {
        val viewModel = TruefalseViewModel()

        viewModel.answerTrue()
        assertEquals(0, viewModel.goodQuestions.value)
        assertEquals(1, viewModel.numberOfQuestionsAsked.value)
        assertEquals(questionBook.getQuestion(1), viewModel.currentQuestion.value)

        viewModel.answerFalse()
        assertEquals(0, viewModel.goodQuestions.value)
        assertEquals(2, viewModel.numberOfQuestionsAsked.value)
        assertEquals(questionBook.getQuestion(2), viewModel.currentQuestion.value)
    }

    @Test
    fun `when answering 3 questions, game should be evaluated`() {
        val viewModel = TruefalseViewModel()

        viewModel.answerTrue()
        viewModel.answerTrue()
        viewModel.answerTrue()

        assertEquals(true, viewModel.shouldEvaluate.value)
    }

    @Test
    fun `when game is ended, variables should be reset`() {
        val viewModel = TruefalseViewModel()

        viewModel.answerTrue()
        viewModel.answerTrue()
        viewModel.answerTrue()
        viewModel.endGame()

        assertEquals(0, viewModel.goodQuestions.value)
        assertEquals(0, viewModel.numberOfQuestionsAsked.value)
        assertEquals(false, viewModel.shouldEvaluate.value)
        assertEquals(questionBook.getQuestion(3), viewModel.currentQuestion.value)
    }

    @Test
    fun `when 3 questions were asked, player should win if he answered at least half of the questions right`() {
        val viewModel = TruefalseViewModel()

        viewModel.answerTrue()
        viewModel.answerTrue()
        viewModel.answerTrue()

        assertEquals(true, viewModel.isWon())
    }

    @Test
    fun `when 3 questions were asked, player should lose if he answered less than half of the questions right`() {
        val viewModel = TruefalseViewModel()

        viewModel.answerFalse()
        viewModel.answerFalse()
        viewModel.answerFalse()

        assertEquals(false, viewModel.isWon())
    }
}
