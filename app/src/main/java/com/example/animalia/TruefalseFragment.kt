package com.example.animalia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.animalia.databinding.FragmentTruefalseBinding
import com.example.animalia.models.TruefalseViewModel

class TruefalseFragment : Fragment() {
    private lateinit var binding: FragmentTruefalseBinding
    private val viewModel: TruefalseViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_truefalse, container, false)

        binding.apply {
            truefalseViewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

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