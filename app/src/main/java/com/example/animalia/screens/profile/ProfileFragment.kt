package com.example.animalia.screens.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
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
            openEditPersonalInfoDialog()
        }

        binding.profileRestButton.setOnClickListener {
            openResetProfileDialog()
        }

        return binding.root
    }

    private fun openEditPersonalInfoDialog() {
        val messageBoxView =
            LayoutInflater.from(activity).inflate(R.layout.fragment_dialog_edit_info, null)
        context?.let { it1 ->
            MaterialAlertDialogBuilder(it1)
                .setTitle(resources.getString(R.string.profile_dialog_edit_title))
                .setView(messageBoxView)
                .setNeutralButton(resources.getString(R.string.profile_dialog_edit_cancel)) { _, _ -> }
                .setPositiveButton(resources.getString(R.string.profile_dialog_edit_save)) { _, _ ->
                    val firstname: String =
                        (messageBoxView.findViewById(R.id.firstname_textfield) as TextInputLayout).editText?.text.toString()
                    val lastname: String =
                        (messageBoxView.findViewById(R.id.lastname_textfield) as TextInputLayout).editText?.text.toString()

                    viewModel.editUserPersonalInfo(firstname, lastname)
                }
                .show()
        }
    }

    private fun openResetProfileDialog() {
        context?.let { it1 ->
            MaterialAlertDialogBuilder(it1)
                .setTitle(resources.getString(R.string.profile_dialog_reset_title))
                .setMessage(resources.getString(R.string.profile_dialog_reset_message))
                .setNeutralButton(resources.getString(R.string.profile_dialog_reset_cancel)) { _, _ -> }
                .setPositiveButton(resources.getString(R.string.profile_dialog_reset_button)) { _, _ ->
                    viewModel.resetStats()
                    view?.findNavController()
                        ?.navigate(ProfileFragmentDirections.actionProfileFragmentToHomeFragment())
                    Toast.makeText(context, getString(R.string.user_reset), Toast.LENGTH_LONG).show()
                }
                .show()
        }

    }

}