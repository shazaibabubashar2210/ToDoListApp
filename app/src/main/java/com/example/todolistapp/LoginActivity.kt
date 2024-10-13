package com.example.todolistapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton: Button = findViewById(R.id.loginButton)
        loginButton.setOnClickListener {
            // Perform login action (e.g., validate credentials)
            // For now, we will navigate to a dummy home screen (replace with your actual implementation)
            // startActivity(Intent(this, HomeActivity::class.java))
        }

        // Navigate to SignUpActivity when "Don't have an account? Register" is clicked
        val registerTextView: TextView = findViewById(R.id.registerTextView)
        registerTextView.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}
