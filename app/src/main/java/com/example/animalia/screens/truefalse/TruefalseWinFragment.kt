package com.example.animalia.screens.truefalse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.animalia.R
import com.example.animalia.databinding.FragmentTruefalseWinBinding

class TruefalseWinFragment : Fragment() {
    private lateinit var binding: FragmentTruefalseWinBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_truefalse_win, container, false)

        val appContext = requireNotNull(this.activity).application
        val viewModelFactory = TruefalseViewModelFactory(appContext)
        val viewModel: TruefalseViewModel by viewModels{viewModelFactory}

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragment = this
    }

    fun endGame() {
//        view?.findNavController()
//            ?.navigate(TruefalseWinFragmentDirections.actionTrueFalseWinFragmentToHomeFragment())
//        viewModel.endGame()
    }
}