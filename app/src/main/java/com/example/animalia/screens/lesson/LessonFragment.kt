package com.example.animalia.screens.lesson

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.animalia.R
import com.example.animalia.database.AnimaliaDatabase
import com.example.animalia.databinding.FragmentLessonBinding

class LessonFragment : Fragment() {
    private lateinit var binding: FragmentLessonBinding
    private val viewModel: LessonViewModel by viewModels() {
        getLessonViewModelFactory()
    }

    private fun getLessonViewModelFactory(): LessonViewModelFactory {
        val appContext = requireActivity().application
        val datasource = AnimaliaDatabase.getInstance(appContext).lessonDatabaseDao

        return LessonViewModelFactory(datasource)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lesson, container, false)

        binding.apply {
            lessonViewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        binding.apply {
            quitlessonButton.setOnClickListener {
                view?.findNavController()
                    ?.navigate(LessonFragmentDirections.actionLessonFragmentToHomeFragment())
            }
        }

        viewModel.shouldEnd.observe(viewLifecycleOwner) {
            if (it) {
                hideContinueButton()
            }
        }

        return binding.root
    }

    private fun hideContinueButton() {
        binding.nextlessonButton.visibility = View.GONE
    }
}