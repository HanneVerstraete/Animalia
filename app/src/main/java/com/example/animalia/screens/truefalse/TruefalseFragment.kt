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
import com.example.animalia.database.questions.QuizElementDatabase
import com.example.animalia.databinding.FragmentTruefalseBinding

class TruefalseFragment : Fragment() {
    private lateinit var binding: FragmentTruefalseBinding
    private val viewModel: TruefalseViewModel by activityViewModels() {
        getTruefalseViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun getTruefalseViewModelFactory(): TruefalseViewModelFactory {
        val appContext = requireNotNull(this.activity).application
        val datasource = QuizElementDatabase.getInstance(appContext).quizElementDatabaseDao

        return TruefalseViewModelFactory(datasource, appContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_truefalse, container, false)

        binding.truefalseViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.shouldEvaluate.observe(viewLifecycleOwner) {
            if (it) {
                evaluateQuiz()
            }
        }

        return binding.root
    }

    private fun evaluateQuiz() {
        if (viewModel.isWon()) {
            view?.findNavController()
                ?.navigate(TruefalseFragmentDirections.actionTruefalseFragmentToTruefalseWinFragment())
        } else {
            view?.findNavController()
                ?.navigate(TruefalseFragmentDirections.actionTruefalseFragmentToTruefalseLoseFragment())
        }
    }
}