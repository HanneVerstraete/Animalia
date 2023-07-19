package com.example.animalia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.animalia.databinding.FragmentLessonBinding

class LessonFragment : Fragment() {
    private val myLessonBook = LessonBook()

    private lateinit var binding: FragmentLessonBinding
    var lessonNumber: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lesson, container, false)

        binding.lessonBook = myLessonBook

        binding.apply {
            nextlessonButton.setOnClickListener { nextLesson() }
            quitlessonButton.setOnClickListener { quitLesson() }
        }

        return binding.root
    }

    private fun nextLesson() {
        binding.apply {
            lessonBook?.getNextLesson(lessonNumber)

            when (lessonNumber) {
                0 -> animalImage.setImageResource(R.drawable.dog)
                1 -> animalImage.setImageResource(R.drawable.lion)
                2 -> animalImage.setImageResource(R.drawable.duck)
                else -> animalImage.setImageResource(R.drawable.empty_vector)
            }

            invalidateAll()
        }

        lessonNumber += 1
    }

    private fun quitLesson() {
        // TODO
    }
}