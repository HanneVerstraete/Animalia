package com.example.animalia.security

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.auth0.android.result.UserProfile
import com.example.animalia.MainActivity
import com.example.animalia.R
import com.example.animalia.database.AnimaliaDatabase
import com.example.animalia.databinding.FragmentLoginBinding
import com.example.animalia.sharedPreferences
import timber.log.Timber

class LoginFragment: Fragment() {

    private lateinit var account : Auth0
    private lateinit var loginLogoutMessage: TextView
    private lateinit var loginButton: Button
    private lateinit var logoutButton: Button
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel
    private var sharedPrefs = sharedPreferences
    private var loggedIn = false
    private var email: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val policy = ThreadPolicy.Builder()
            .permitAll().build()
        StrictMode.setThreadPolicy(policy)

        setHasOptionsMenu(true)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        val app = requireNotNull(this.activity).application
        val dataSource = AnimaliaDatabase.getInstance(app).userDatabaseDao

        val viewModelFactory = LoginViewModelFactory(dataSource, app)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)

        account = Auth0(
            getString(R.string.auth_client_id),
            getString(R.string.auth_domain)
        )

        loginButton = binding.loginButton
        loginButton.setOnClickListener {
            loginWithBrowser()
        }

        logoutButton = binding.logoutButton
        logoutButton.setOnClickListener {
            logout()
        }

        Timber.tag("The login is ").i(loggedIn.toString())
        if (sharedPrefs.loggedIn == "true") {
            loginButton.setVisibility(View.GONE)
            logoutButton.setVisibility(View.VISIBLE)
        } else {
            loginButton.setVisibility(View.VISIBLE)
            logoutButton.setVisibility(View.GONE)
        }

        loginLogoutMessage = binding.loginLogoutMessage

        binding.loginViewModel = viewModel
        binding.lifecycleOwner = this

        checkIfToken()
        setLoggedInText()

        Timber.tag("The activity is ").i(requireActivity().localClassName)

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    private fun checkIfToken(){
        val token = CredentialsManager.getAccessToken(requireContext())
        if(token != null){
            showUserProfile(token)
        }
        else {
            Toast.makeText(context, getText(R.string.invalid_login), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setLoggedInText() {
        if(sharedPrefs.loggedIn == "true") {
            loginLogoutMessage.text = getText(R.string.logout_text_confirm)
        }
        else {
            loginLogoutMessage.text = getText(R.string.logout_text)
        }
    }

    private fun loginWithBrowser() {

        WebAuthProvider.login(account)
            .withScheme("demo")
            .withScope("openid profile email")
            .start(requireContext(), object : Callback<Credentials, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    loggedIn = false
                    Toast.makeText(context, getText(R.string.login_nok), Toast.LENGTH_SHORT).show()

                    // Set visibility for logout
                    sharedPrefs.loggedIn = "false"
                    logoutButton.setVisibility(View.GONE)
                    loginButton.setVisibility(View.VISIBLE)
                }
                override fun onSuccess(result: Credentials) {
                    Toast.makeText(context, getText(R.string.login_ok), Toast.LENGTH_SHORT).show()

                    CredentialsManager.saveCredentials(requireContext(), result)
                    checkIfToken()

                    // Set visibility for login
                    sharedPrefs.loggedIn = "true"
                    loginButton.setVisibility(View.GONE)
                    logoutButton.setVisibility(View.VISIBLE)

                    // Check if email is returned
                    showUserProfile(result.accessToken)

                    // Go to next fragment
                    val intent = Intent (activity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    activity?.startActivity(intent)
                }
            })
    }

    private fun logout() {
        WebAuthProvider.logout(account)
            .withScheme("demo")
            .start(requireContext(), object: Callback<Void?, AuthenticationException> {
                override fun onSuccess(result: Void?) {
                    Toast.makeText(context, getText(R.string.logout_ok), Toast.LENGTH_SHORT).show()
                    // Set visibility for login
                    sharedPrefs.loggedIn = "false"
                    logoutButton.setVisibility(View.GONE)
                    loginButton.setVisibility(View.VISIBLE)

                    setLoggedInText()
                }

                override fun onFailure(error: AuthenticationException) {
                    Toast.makeText(context, getText(R.string.logout_nok), Toast.LENGTH_SHORT).show()

                    // Set visibility for logout
                    sharedPrefs.loggedIn = "true"
                    loginButton.setVisibility(View.GONE)
                    logoutButton.setVisibility(View.VISIBLE)
                }
            })
    }

    private fun showUserProfile(accessToken: String){
        val client = AuthenticationAPIClient(account)

        client.userInfo(accessToken)
            .start(object : Callback<UserProfile, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    Timber.i(error.stackTraceToString())
                    loggedIn = false
                    setLoggedInText()
                }

                override fun onSuccess(result: UserProfile) {
                    Timber.i("SUCCESS! got the user profile")
                    loggedIn = true
                    email = result.email
                    Timber.tag("The email address setter shared prefs via Auth 0 is: ").i(email!!)
                    sharedPrefs.emailCurrentUser = email

                    setLoggedInText()
                    viewModel.setUserOnLogin()
                }
            })

    }
}