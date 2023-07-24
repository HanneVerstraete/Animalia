package com.example.animalia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.animalia.databinding.FragmentTruefalseLoseBinding
import com.example.animalia.models.TruefalseViewModel

class TruefalseLoseFragment : Fragment() {
    private lateinit var binding: FragmentTruefalseLoseBinding
    private val viewModel: TruefalseViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_truefalse_lose, container, false)

        binding.apply {
            viewModel = this@TruefalseLoseFragment.viewModel
            lifecycleOwner = lifecycleOwner
        }

        viewModel.shouldLeaveGame.observe(viewLifecycleOwner) {
            if (it) {
                endGame()
            }
        }

        return binding.root
    }

    private fun endGame() {
        view?.findNavController()
            ?.navigate(TruefalseLoseFragmentDirections.actionTrueFalseLoseFragmentToHomeFragment())
    }
}