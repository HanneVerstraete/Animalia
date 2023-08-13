package com.example.animalia.screens.lesson

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.animalia.R
import com.example.animalia.databinding.FragmentLessonBinding

class LessonFragment : Fragment() {
    private lateinit var binding: FragmentLessonBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lesson, container, false)

        val appContext = requireNotNull(this.activity).application
        val viewModelFactory = LessonViewModelFactory(appContext)
        val viewModel: LessonViewModel by viewModels{viewModelFactory}

        binding.lessonViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.shouldEnd.observe(viewLifecycleOwner) {
            if (it) {
                binding.nextlessonButton.visibility = View.GONE
            }
        }

        viewModel.isFinished.observe(viewLifecycleOwner) {
            if (it) {
                binding.animalImage.visibility = View.GONE
                binding.nextlessonButton.visibility = View.GONE
                binding.finishedLessonsText!!.visibility = View.VISIBLE
            }
        }

        return binding.root
    }
}