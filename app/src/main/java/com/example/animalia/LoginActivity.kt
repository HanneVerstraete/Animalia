package com.example.animalia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.animalia.databinding.ActivityLoginBinding
import com.example.animalia.utilities.SharedPreferences

val sharedPreferences: SharedPreferences by lazy {
    LoginActivity.sharedPreferences!!
}

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var backButton: Button

    companion object {
        var sharedPreferences: SharedPreferences? = null
        lateinit var instance: LoginActivity
            private set
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        instance = this
        sharedPreferences = SharedPreferences(applicationContext)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        backButton = findViewById(R.id.back_button)
        backButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}