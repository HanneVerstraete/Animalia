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
import com.example.animalia.databinding.FragmentTruefalseLoseBinding

// TODO combine with winfragment?
class TruefalseLoseFragment : Fragment() {
    private lateinit var binding: FragmentTruefalseLoseBinding
    private val viewModel: TruefalseViewModel by activityViewModels() {
        getTruefalseViewModelFactory()
    }

    private fun getTruefalseViewModelFactory(): TruefalseViewModelFactory {
        val appContext = requireNotNull(this.activity).application
        val datasource = QuizElementDatabase.getInstance(appContext).quizElementDatabaseDao

        return TruefalseViewModelFactory(datasource, appContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_truefalse_lose, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragment = this
    }

    fun endGame() {
        view?.findNavController()
            ?.navigate(TruefalseLoseFragmentDirections.actionTrueFalseLoseFragmentToHomeFragment())
        viewModel.endGame()
    }
}