package com.example.animalia.screens.truefalse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.animalia.R
import com.example.animalia.database.AnimaliaDatabase
import com.example.animalia.databinding.FragmentTruefalseBinding
import com.example.animalia.repository.QuizElementRepository
import com.example.animalia.repository.UserRepository
import com.example.animalia.sharedPreferences
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class TruefalseFragment : Fragment() {
    private lateinit var binding: FragmentTruefalseBinding
    private val viewModel: TruefalseViewModel by activityViewModels() {
        getTruefalseViewModelFactory()
    }

    private fun getTruefalseViewModelFactory(): TruefalseViewModelFactory {
        val appContext = requireNotNull(this.activity).application
        val database = AnimaliaDatabase.getInstance(appContext.applicationContext)
        val quizElementRepository = QuizElementRepository(database)
        val userRepository = UserRepository(database)
        return TruefalseViewModelFactory(
            quizElementRepository,
            userRepository,
            sharedPreferences.currentQuestion
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_truefalse, container, false)
        binding.truefalseViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.shouldEvaluate.observe(viewLifecycleOwner) {
            if (it) {
                view?.findNavController()
                    ?.navigate(TruefalseFragmentDirections.actionTruefalseFragmentToTruefalseEndFragment())
            }
        }

        binding.trueButton.setOnClickListener {
            openTrueDialog()
        }

        binding.falseButton.setOnClickListener {
            openFalseDialog()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.timer.startTimer()
    }

    override fun onStop() {
        super.onStop()
        viewModel.timer.stopTimer()
    }

    private fun openTrueDialog() {
        val style = if (viewModel.currentQuestion.value!!.answer == "Juist") {
            R.style.ThemeOverlay_WrightAnswer
        } else {
            R.style.ThemeOverlay_WrongAnswer
        }
        createDialog(style)
    }

    private fun openFalseDialog() {
        val style = if (viewModel.currentQuestion.value!!.answer == "Fout") {
            R.style.ThemeOverlay_WrightAnswer
        } else {
            R.style.ThemeOverlay_WrongAnswer
        }
        createDialog(style)
    }

    private fun createDialog(style: Int) {
        context?.let { it1 ->
            MaterialAlertDialogBuilder(it1, style)
                .setTitle("Antwoord: ${viewModel.currentQuestion.value!!.answer}")
                .setMessage(viewModel.currentQuestion.value!!.explanation)
                .setPositiveButton("Verder") { _, _ ->
                    viewModel.answerTrue()
                }
                .show()
        }
    }
}