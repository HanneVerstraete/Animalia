package com.example.animalia

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.animalia.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
//    lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val menuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.overflow_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.aboutFragment) {
                    return NavigationUI.onNavDestinationSelected(
                        menuItem,
                        requireView().findNavController()
                    )
                }
                return false
            }

        }, viewLifecycleOwner)

        return inflater.inflate(R.layout.fragment_home, container, false)
//        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
//
//        setOnClickListeners()
//
//        setHasOptionsMenu(true)
//        return binding.root
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater?.inflate(R.menu.overflow_menu, menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return NavigationUI.onNavDestinationSelected(
//            item!!,
//            requireView().findNavController()
//        )
//                || super.onOptionsItemSelected(item)
//    }
//
//    private fun setOnClickListeners() {
//        binding.startlessonButton.setOnClickListener(
//            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_lessonFragment)
//        )
//    }


}