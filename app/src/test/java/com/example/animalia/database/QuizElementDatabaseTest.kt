package com.example.animalia.database

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.animalia.database.questions.DatabaseQuizElement
import com.example.animalia.database.questions.QuizElementDatabaseDao

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class QuizElementDatabaseTest {

    private lateinit var quizElementDao: QuizElementDatabaseDao
    private lateinit var db: AnimaliaDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        db = Room.inMemoryDatabaseBuilder(context, AnimaliaDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        quizElementDao = db.quizElementDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun `database should insert and fetch one item`() = runBlocking {
        val testQuizElement0 = DatabaseQuizElement("id0", 0, "question", "Juist", "explanation")
        quizElementDao.insert(testQuizElement0)
        val quizElement = quizElementDao.get("id0")
        assertEquals(testQuizElement0, quizElement)
    }

    @Test
    @Throws(Exception::class)
    fun `database should insert and fetch multiple items`() = runBlocking {
        val testQuizElement1 = DatabaseQuizElement("id1", 1, "question", "Juist", "explanation")
        val testQuizElement2 = DatabaseQuizElement("id2", 2, "question", "Juist", "explanation")
        val elements = arrayOf(testQuizElement1, testQuizElement2)
        quizElementDao.insertAll(*elements)
        val quizElement1 = quizElementDao.get("id1")
        val quizElement2 = quizElementDao.get("id2")
        assertEquals(testQuizElement1, quizElement1)
        assertEquals(testQuizElement2, quizElement2)
    }

    @Test
    @Throws(Exception::class)
    fun `database should update item`() = runBlocking {
        val testQuizElement3 = DatabaseQuizElement("id3", 3, "question", "Juist", "explanation")
        val testQuizElement3Updated = DatabaseQuizElement("id3", 3, "question updated", "Juist", "explanation")
        quizElementDao.insert(testQuizElement3)
        quizElementDao.update(testQuizElement3Updated)
        val quizElement = quizElementDao.get("id3")
        assertEquals("question updated", quizElement?.question)
    }

    @Test
    @Throws(Exception::class)
    fun `database should clear all items`() = runBlocking {
        val testQuizElement4 = DatabaseQuizElement("id4", 4, "question", "Juist", "explanation")
        val testQuizElement5 = DatabaseQuizElement("id5", 5, "question", "Juist", "explanation")
        val elements = arrayOf(testQuizElement4, testQuizElement5)
        quizElementDao.insertAll(*elements)
        quizElementDao.clear()
        val numberOfItems = quizElementDao.getNumberOfQuizElements()
        assertEquals(0, numberOfItems)
    }

    @Test
    @Throws(Exception::class)
    fun `database should fetch all items`() = runBlocking {
        val testQuizElement6 = DatabaseQuizElement("id6", 6, "question", "Juist", "explanation")
        val testQuizElement7 = DatabaseQuizElement("id7", 7, "question", "Juist", "explanation")
        val elements = arrayOf(testQuizElement6, testQuizElement7)
        quizElementDao.insertAll(*elements)
        val items = quizElementDao.getAllQuizElements()
        assertEquals(testQuizElement6, items[0])
        assertEquals(testQuizElement7, items[1])
    }

    @Test
    @Throws(Exception::class)
    fun `database should count all items`() = runBlocking {
        val testQuizElement8 = DatabaseQuizElement("id8", 8, "question", "Juist", "explanation")
        quizElementDao.insert(testQuizElement8)
        val count = quizElementDao.getNumberOfQuizElements()
        assertEquals(1, count)

        val testQuizElement9 = DatabaseQuizElement("id9", 9, "question", "Juist", "explanation")
        quizElementDao.insert(testQuizElement9)
        val count2 = quizElementDao.getNumberOfQuizElements()
        assertEquals(2, count2)
    }

    @Test
    @Throws(Exception::class)
    fun `database should fetch item by index`() = runBlocking {
        val testQuizElement10 = DatabaseQuizElement("id10", 10, "question", "Juist", "explanation")
        quizElementDao.insert(testQuizElement10)
        val quizElement = quizElementDao.getByIndex(10)
        assertEquals(testQuizElement10, quizElement)
    }
}