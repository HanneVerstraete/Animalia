package com.example.animalia.security

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
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

/**
 * A simple [Fragment] subclass
 * Displays the login functionality
 * @property account The Auth0 account information
 * @property loggedInText The text mentioning that you are logged in
 * @property welcomeText The text set to welcome a user
 * @property loggedIn The text stating whether you are logged in or not
 */
class LoginFragment: Fragment() {

    private lateinit var account : Auth0
    private lateinit var loggedInText: TextView
    private lateinit var welcomeText: TextView
    private lateinit var loginButton: Button
    private lateinit var logoutButton: Button
    private lateinit var backButton: Button
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

        backButton = binding.backButton
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
            backButton.setVisibility(View.VISIBLE)
        } else {
            loginButton.setVisibility(View.VISIBLE)
            logoutButton.setVisibility(View.GONE)
            backButton.setVisibility(View.GONE)
        }

        welcomeText = binding.welcomeText
        loggedInText = binding.loginText

        binding.loginViewModel = viewModel
        binding.lifecycleOwner = this

        checkIfToken()
        setLoggedInText()

        Timber.tag("The activity is ").i(requireActivity().localClassName)

        backButton.setOnClickListener {
//            TODO right fragment
            requireView().findNavController().navigate(R.id.aboutFragment)
        }

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    /**
     * Checks if an access token is present
     */
    private fun checkIfToken(){
        val token = CredentialsManager.getAccessToken(requireContext())
        if(token != null){
            showUserProfile(token)
        }
        else {
            Toast.makeText(context, getText(R.string.invalid_login), Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Sets the text when you are logged in
     */
    private fun setLoggedInText(email: String = "") {
        if(sharedPrefs.loggedIn == "true") {
            val welcomeMessage = StringBuilder()
            welcomeText.text = welcomeMessage.append(getText(R.string.welcome_text))
            welcomeText.text = welcomeMessage.append(" " + email)
            loggedInText.text = getText(R.string.login_text)
        }
        else {
            welcomeText.text = getText(R.string.welcome_text)
            loggedInText.text = getText(R.string.logout_text)
        }
    }

    /**
     * Method used for login, stating success or failure
     */
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
                    backButton.setVisibility(View.GONE)
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
                    backButton.setVisibility(View.VISIBLE)

                    // Check if email is returned
                    showUserProfile(result.accessToken)

                    // Go to next fragment
                    val intent = Intent (activity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    activity?.startActivity(intent)
                }
            })
    }

    /**
     * Method used to logout of the app
     */
    private fun logout() {
        WebAuthProvider.logout(account)
            .withScheme("demo")
            .start(requireContext(), object: Callback<Void?, AuthenticationException> {
                override fun onSuccess(result: Void?) {
                    Toast.makeText(context, getText(R.string.logout_ok), Toast.LENGTH_SHORT).show()
                    // Set visibility for login
                    sharedPrefs.loggedIn = "false"
                    logoutButton.setVisibility(View.GONE)
                    backButton.setVisibility(View.GONE)
                    loginButton.setVisibility(View.VISIBLE)

                    setLoggedInText("")
                }

                override fun onFailure(error: AuthenticationException) {
                    Toast.makeText(context, getText(R.string.logout_nok), Toast.LENGTH_SHORT).show()

                    // Set visibility for logout
                    sharedPrefs.loggedIn = "true"
                    loginButton.setVisibility(View.GONE)
                    logoutButton.setVisibility(View.VISIBLE)
                    backButton.setVisibility(View.VISIBLE)
                }
            })
    }

    /**
     * Method used to display the user profile
     *
     * @property accessToken The access token of the user
     */
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

                    setLoggedInText(email!!)
                    viewModel.setUserOnLogin()
                }
            })

    }
}