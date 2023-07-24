package com.example.animalia.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animalia.LessonBook

class LessonViewModel : ViewModel() {
    private var myLessonBook = LessonBook()
    private var lessonNumber: Int = 0

    private var _currentLesson = MutableLiveData<String>()
    val currentLesson: LiveData<String>
        get() = _currentLesson

    private var _image = MutableLiveData<Int>()
    val image: LiveData<Int>
        get() = _image

    private val _shouldEnd = MutableLiveData<Boolean>()
    val shouldEnd: LiveData<Boolean>
        get() = _shouldEnd

    init {
        getNextLesson()
    }

    fun getNextLesson() {
        _currentLesson.value = myLessonBook.getLesson(lessonNumber)
        _image.value = myLessonBook.getLessonPicture(lessonNumber)

        if (myLessonBook.isLastLesson(lessonNumber)) {
            _shouldEnd.value = true
        } else {
            lessonNumber++
        }
    }
}