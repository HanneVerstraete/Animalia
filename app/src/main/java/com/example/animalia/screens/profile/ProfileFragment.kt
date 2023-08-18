package com.example.animalia.screens.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.animalia.R
import com.example.animalia.database.AnimaliaDatabase
import com.example.animalia.databinding.FragmentProfileBinding
import com.example.animalia.repository.UserRepository
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels() {
        getProfileViewModelFactory()
    }

    private fun getProfileViewModelFactory(): ProfileViewModelFactory {
        val appContext = requireNotNull(this.activity).application
        val database = AnimaliaDatabase.getInstance(appContext.applicationContext)
        val userRepository = UserRepository(database)
        return ProfileViewModelFactory(userRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.editButton.setOnClickListener {
            _openDialog()
        }

        return binding.root
    }

    fun _openDialog() {
        val messageBoxView = LayoutInflater.from(activity).inflate(R.layout.fragment_dialog_edit_info, null)
        context?.let { it1 ->
            MaterialAlertDialogBuilder(it1)
                .setTitle("Gegevens bewerken")
                .setView(messageBoxView)
                .setNeutralButton("Annuleer") { _, _ -> }
                .setPositiveButton("Opslaan") { _, _ ->
                    val firstname: String = (messageBoxView.findViewById(R.id.firstname_textfield) as TextInputLayout).editText?.text.toString()
                    val lastname: String = (messageBoxView.findViewById(R.id.lastname_textfield) as TextInputLayout).editText?.text.toString()

                    viewModel.editUserPersonalInfo(firstname, lastname)
                }
                .show()
        }
    }
}