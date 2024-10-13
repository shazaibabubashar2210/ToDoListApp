package com.example.todolistapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val signUpButton: Button = findViewById(R.id.signUpButton)
        signUpButton.setOnClickListener {
            // TODO: Add your sign-up logic here
            // For now, navigate to Login Page after signing up
            startActivity(Intent(this, LoginActivity::class.java))
            finish() // Optional: Close SignUpActivity
        }

        // Open the Login Page when the TextView is clicked
        val alreadyHaveAccountTextView: TextView = findViewById(R.id.alreadyHaveAccountTextView)
        alreadyHaveAccountTextView.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish() // Optional: Close SignUpActivity
        }
    }
}
