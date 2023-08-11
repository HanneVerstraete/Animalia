package com.example.animalia.screens.truefalse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.animalia.R
import com.example.animalia.databinding.FragmentTruefalseLoseBinding

// TODO combine with winfragment?
class TruefalseLoseFragment : Fragment() {
    private lateinit var binding: FragmentTruefalseLoseBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_truefalse_lose, container, false)

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
//        TODO
//        view?.findNavController()
//            ?.navigate(TruefalseLoseFragmentDirections.actionTrueFalseLoseFragmentToHomeFragment())
//        viewModel.endGame()
    }
}