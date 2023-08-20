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
import com.example.animalia.databinding.FragmentTruefalseEndBinding
import com.example.animalia.repository.QuizElementRepository
import com.example.animalia.repository.UserRepository
import com.example.animalia.sharedPreferences

class TruefalseEndFragment : Fragment() {
    private lateinit var binding: FragmentTruefalseEndBinding
    private val viewModel: TruefalseViewModel by activityViewModels() {
        getTruefalseViewModelFactory()
    }

    private fun getTruefalseViewModelFactory(): TruefalseViewModelFactory {
        val appContext = requireNotNull(this.activity).application
        val database = AnimaliaDatabase.getInstance(appContext.applicationContext)
        val quizElementRepository = QuizElementRepository(database)
        val userRepository = UserRepository(database)
        return TruefalseViewModelFactory(quizElementRepository, userRepository,sharedPreferences.currentQuestion)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_truefalse_end, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragment = this
    }

    fun endGameAndGoToHome() {
        view?.findNavController()
            ?.navigate(TruefalseEndFragmentDirections.actionTrueFalseEndFragmentToHomeFragment())
        viewModel.endGame()
    }

    fun endGameAndStartNextGame() {
        view?.findNavController()
            ?.navigate(TruefalseEndFragmentDirections.actionTrueFalseEndFragmentToTrueFalseFragment())
        viewModel.endGame()
    }
}