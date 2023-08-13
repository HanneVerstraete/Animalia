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
import com.example.animalia.sharedPreferences

class TruefalseFragment : Fragment() {
    private lateinit var binding: FragmentTruefalseBinding
    private val viewModel: TruefalseViewModel by activityViewModels() {
        getTruefalseViewModelFactory()
    }

    private fun getTruefalseViewModelFactory(): TruefalseViewModelFactory {
        val appContext = requireNotNull(this.activity).application
        val database = AnimaliaDatabase.getInstance(appContext.applicationContext)
        val quizElementRepository = QuizElementRepository(database)
        return TruefalseViewModelFactory(quizElementRepository, sharedPreferences.currentQuestion)
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

        return binding.root
    }
}