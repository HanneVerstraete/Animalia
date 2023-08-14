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
        return LessonViewModelFactory(lessonRepository, args.lessonIndex)
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

        viewModel.isDoingLastLesson.observe(viewLifecycleOwner) {
            if (it) {
                binding.nextlessonButton.visibility = View.GONE
            }
        }

        return binding.root
    }

    fun setOnClickListeners() {
        binding.quitlessonButton.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(LessonFragmentDirections.actionLessonFragmentToHomeFragment())
        }
    }
}