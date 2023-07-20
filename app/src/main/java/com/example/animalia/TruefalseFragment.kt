package com.example.animalia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.animalia.databinding.FragmentTruefalseBinding

class TruefalseFragment : Fragment() {
    private var myQuestionBook = QuestionBook()
    private lateinit var binding: FragmentTruefalseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_truefalse, container, false)

        binding.questionBook = myQuestionBook

        myQuestionBook.getNextQuestion()

        binding.apply {
            trueButton.setOnClickListener {
                myQuestionBook.evaluateTrue()
                getNextQuestion()
            }
            falseButton.setOnClickListener {
                myQuestionBook.evaluateFalse()
                getNextQuestion()
            }
        }

        return binding.root
    }

    private fun getNextQuestion() {
        binding.apply {
            if (!myQuestionBook.isEnded()) {
                myQuestionBook.getNextQuestion()
                invalidateAll()
            } else {
                if (myQuestionBook.isWon()) {
                    view?.findNavController()
                        ?.navigate(TruefalseFragmentDirections.actionTruefalseFragmentToTruefalseWinFragment())
                } else {
                    view?.findNavController()
                        ?.navigate(TruefalseFragmentDirections.actionTruefalseFragmentToTruefalseLoseFragment())

                }
            }
        }
    }
}