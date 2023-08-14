package com.example.animalia.screens.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.animalia.R
import com.example.animalia.databinding.FragmentHomeBinding
import com.example.animalia.sharedPreferences

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        setOnClickListeners()

        setHasOptionsMenu(true)

        val appContext = requireNotNull(this.activity).application
        val viewModelFactory = HomeViewModelFactory(appContext)
        val viewModel: HomeViewModel by viewModels { viewModelFactory }

        binding.homeViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.isFinishedLessons.observe(viewLifecycleOwner) {
            if (it) {
                binding.startLessonButton.visibility = View.GONE
                binding.startLessonFinishedText.visibility = View.VISIBLE
            }
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()
        )
    }

    private fun setOnClickListeners() {
        binding.startTruefalseButton.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(HomeFragmentDirections.actionHomeFragmentToTruefalseFragment())
        }
        binding.startLessonButton.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(
                    HomeFragmentDirections.actionHomeFragmentToLessonFragment(
                        sharedPreferences.currentLesson
                    )
                )
        }
    }
}