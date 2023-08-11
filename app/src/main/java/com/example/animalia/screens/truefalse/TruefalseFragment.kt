package com.example.animalia.screens.truefalse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.animalia.R
import com.example.animalia.databinding.FragmentTruefalseBinding

class TruefalseFragment : Fragment() {
    private lateinit var binding: FragmentTruefalseBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_truefalse, container, false)

        val appContext = requireNotNull(this.activity).application
        val viewModelFactory = TruefalseViewModelFactory(appContext)
        val viewModel: TruefalseViewModel by viewModels{viewModelFactory}

        binding.truefalseViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.shouldEvaluate.observe(viewLifecycleOwner) {
            if (it) {
                if (viewModel.isWon()) {
                    view?.findNavController()
                        ?.navigate(TruefalseFragmentDirections.actionTruefalseFragmentToTruefalseWinFragment())
                } else {
                    view?.findNavController()
                        ?.navigate(TruefalseFragmentDirections.actionTruefalseFragmentToTruefalseLoseFragment())
                }
            }
        }

        return binding.root
    }
}