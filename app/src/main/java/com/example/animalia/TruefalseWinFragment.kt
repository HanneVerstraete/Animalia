package com.example.animalia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.animalia.databinding.FragmentTruefalseWinBinding
import com.example.animalia.models.TruefalseViewModel

class TruefalseWinFragment : Fragment() {
    private lateinit var binding: FragmentTruefalseWinBinding
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
            DataBindingUtil.inflate(inflater, R.layout.fragment_truefalse_win, container, false)

        binding.apply {
            viewModel = this@TruefalseWinFragment.viewModel
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
            ?.navigate(TruefalseWinFragmentDirections.actionTrueFalseWinFragmentToHomeFragment())
    }
}