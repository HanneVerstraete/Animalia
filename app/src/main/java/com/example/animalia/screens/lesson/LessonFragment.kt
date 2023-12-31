package com.example.animalia.screens.lesson

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.animalia.R
import com.example.animalia.database.AnimaliaDatabase
import com.example.animalia.databinding.FragmentLessonBinding
import com.example.animalia.repository.LessonRepository
import com.example.animalia.repository.UserRepository
import com.example.animalia.sharedPreferences
import com.google.android.material.snackbar.Snackbar

class LessonFragment : Fragment() {
    private lateinit var binding: FragmentLessonBinding
    private val viewModel: LessonViewModel by viewModels() {
        getLessonViewModelFactory()
    }
    private val args: LessonFragmentArgs by navArgs()

    private fun getLessonViewModelFactory(): LessonViewModelFactory {
        val appContext = requireNotNull(this.activity).application
        val database = AnimaliaDatabase.getInstance(appContext.applicationContext)
        val lessonRepository = LessonRepository(database)
        val userRepository = UserRepository(database)
        return LessonViewModelFactory(lessonRepository, userRepository, args.lessonIndex)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lesson, container, false)
        setOnClickListeners()

        binding.lessonViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.currentLesson.observe(viewLifecycleOwner) {
            if (it.index + 1 == viewModel.totalNumberOfLessons) {
                binding.nextlessonButton.visibility = View.INVISIBLE
                binding.finishlessonButton.visibility = View.VISIBLE
            } else {
                binding.nextlessonButton.visibility = View.VISIBLE
                binding.finishlessonButton.visibility = View.INVISIBLE
            }
            if (it.index == 0) {
                binding.previouslessonButton.visibility = View.INVISIBLE
            } else {
                binding.previouslessonButton.visibility = View.VISIBLE
            }
        }

        return binding.root
    }

    fun setOnClickListeners() {
        binding.homeButton.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(LessonFragmentDirections.actionLessonFragmentToHomeFragment())
        }

        binding.finishlessonButton.setOnClickListener { view: View ->
            viewModel.getNextLesson()
            view.findNavController()
                .navigate(LessonFragmentDirections.actionLessonFragmentToHomeFragment())
        }

        binding.nextlessonButton.setOnClickListener { view: View ->
            if (sharedPreferences.currentLesson <= viewModel.currentLesson.value!!.index) {
                Snackbar.make(view, R.string.added_lesson_xp, Snackbar.LENGTH_SHORT)
                    .setAction(R.string.ok) {}
                    .setAnchorView(view)
                    .show()
            }
            viewModel.getNextLesson()
        }
    }
}