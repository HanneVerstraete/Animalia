package com.example.animalia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.animalia.databinding.FragmentTruefalseWinBinding

class TruefalseWinFragment : Fragment() {
    private lateinit var binding: FragmentTruefalseWinBinding

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
        setOnClickListeners()

        return binding.root
    }

    private fun setOnClickListeners() {
        binding.continueButton.setOnClickListener {
            view?.findNavController()
                ?.navigate(TruefalseWinFragmentDirections.actionTrueFalseWinFragmentToHomeFragment())
        }
    }
}