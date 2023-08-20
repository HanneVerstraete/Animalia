package com.example.animalia.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.animalia.domain.QuizElement
import com.example.animalia.domain.User
import com.example.animalia.repository.QuizElementRepository
import com.example.animalia.repository.UserRepository
import com.example.animalia.screens.truefalse.TruefalseViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class TruefalseViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockRepository: QuizElementRepository

    @Mock
    private lateinit var mockUserRepository: UserRepository

    private lateinit var viewModel: TruefalseViewModel

    private val testDispatcher = StandardTestDispatcher()

    private val quizElement0 =
        QuizElement("id0", 0, "dit is een question 0", "Juist", "dit is een uitleg 0")
    private val quizElement1True =
        QuizElement("id1", 1, "dit is een question 1", "Juist", "dit is een uitleg 1")
    private val quizElement2False =
        QuizElement("id2", 2, "dit is een question 2", "Fout", "dit is een uitleg 2")

    private val user = User("ida", "firstName", "lastName", "email", 0, 0, 1, 0)
    private val updatedUser = User("idb", "firstName", "lastName", "email", 0, 3, 1, 4)

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `when viewModel is initialized, all variables should be set to correct values`() = runTest {
        `when`(mockRepository.getQuizElementByIndex(0)).thenReturn(quizElement0)
        viewModel = TruefalseViewModel(mockRepository, mockUserRepository, 0)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(0, viewModel.goodQuestions.value)
        assertEquals(0, viewModel.numberOfQuestionsAsked.value)
        assertEquals(quizElement0, viewModel.currentQuestion.value)
        assertEquals(false, viewModel.shouldEvaluate.value)
    }

    @Test
    fun `when answering correct, good questions should be increased`() = runTest {
        `when`(mockRepository.getQuizElementByIndex(1)).thenReturn(quizElement1True)
        viewModel = TruefalseViewModel(mockRepository, mockUserRepository, 1)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.answerTrue()

        assertEquals(quizElement1True, viewModel.currentQuestion.value)
        assertEquals(1, viewModel.numberOfQuestionsAsked.value)
        assertEquals(1, viewModel.goodQuestions.value)

        `when`(mockRepository.getQuizElementByIndex(2)).thenReturn(quizElement2False)
        viewModel = TruefalseViewModel(mockRepository, mockUserRepository, 2)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.answerFalse()

        assertEquals(quizElement2False, viewModel.currentQuestion.value)
        assertEquals(1, viewModel.numberOfQuestionsAsked.value)
        assertEquals(1, viewModel.goodQuestions.value)
    }

    @Test
    fun `when answering incorrect, good questions should not be increased`() = runTest {
        `when`(mockRepository.getQuizElementByIndex(2)).thenReturn(quizElement2False)
        viewModel = TruefalseViewModel(mockRepository, mockUserRepository, 2)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.answerTrue()

        assertEquals(quizElement2False, viewModel.currentQuestion.value)
        assertEquals(1, viewModel.numberOfQuestionsAsked.value)
        assertEquals(0, viewModel.goodQuestions.value)

        `when`(mockRepository.getQuizElementByIndex(1)).thenReturn(quizElement1True)
        viewModel = TruefalseViewModel(mockRepository, mockUserRepository, 1)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.answerFalse()

        assertEquals(quizElement1True, viewModel.currentQuestion.value)
        assertEquals(1, viewModel.numberOfQuestionsAsked.value)
        assertEquals(0, viewModel.goodQuestions.value)
    }

    @Test
    fun `when answering 3 questions, game should be evaluated`() = runTest {
        `when`(mockRepository.getQuizElementByIndex(0)).thenReturn(quizElement0)
        `when`(mockUserRepository.getUser()).thenReturn(user)
        `when`(mockUserRepository.updateUser(user)).thenReturn(updatedUser)
        viewModel = TruefalseViewModel(mockRepository, mockUserRepository, 0)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.answerTrue()
        viewModel.answerTrue()
        viewModel.answerTrue()
        viewModel.getQuestion()

        advanceUntilIdle()

        assertEquals(true, viewModel.shouldEvaluate.value)
    }

    @Test
    fun `when game is ended, variables should be reset`() = runTest {
        `when`(mockRepository.getQuizElementByIndex(0)).thenReturn(quizElement0)
        `when`(mockUserRepository.getUser()).thenReturn(user)
        `when`(mockUserRepository.updateUser(user)).thenReturn(updatedUser)
        viewModel = TruefalseViewModel(mockRepository, mockUserRepository, 0)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.answerTrue()
        viewModel.answerTrue()
        viewModel.answerTrue()
        viewModel.getQuestion()

        advanceUntilIdle()

        assertEquals(3, viewModel.goodQuestions.value)
        assertEquals(3, viewModel.numberOfQuestionsAsked.value)
        assertEquals(true, viewModel.shouldEvaluate.value)

        viewModel.endGame()

        assertEquals(0, viewModel.goodQuestions.value)
        assertEquals(0, viewModel.numberOfQuestionsAsked.value)
        assertEquals(false, viewModel.shouldEvaluate.value)
    }

    @Test
    fun `when 3 questions were asked, player should win if he answered at least half of the questions right`() = runTest {
        `when`(mockRepository.getQuizElementByIndex(1)).thenReturn(quizElement1True)
        `when`(mockUserRepository.getUser()).thenReturn(user)
        `when`(mockUserRepository.updateUser(user)).thenReturn(updatedUser)
        viewModel = TruefalseViewModel(mockRepository, mockUserRepository, 1)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.answerTrue()
        viewModel.answerTrue()
        viewModel.answerTrue()

        assertEquals(true, viewModel.isWon())
    }

    @Test
    fun `when 3 questions were asked, player should lose if he answered less than half of the questions right`() = runTest {
        `when`(mockRepository.getQuizElementByIndex(1)).thenReturn(quizElement1True)
        `when`(mockUserRepository.getUser()).thenReturn(user)
        `when`(mockUserRepository.updateUser(user)).thenReturn(updatedUser)
        viewModel = TruefalseViewModel(mockRepository, mockUserRepository, 1)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.answerFalse()
        viewModel.answerFalse()
        viewModel.answerFalse()

        assertEquals(false, viewModel.isWon())
    }

    @OptIn(DelicateCoroutinesApi::class)
    @After
    fun close() {
        Dispatchers.shutdown()
    }
}