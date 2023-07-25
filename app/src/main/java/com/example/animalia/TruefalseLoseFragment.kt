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

// TODO combine with winfragment?
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