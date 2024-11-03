package com.example.todolistapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        val loginButton: Button = view.findViewById(R.id.loginButton)
        val moveToSignUp: TextView = view.findViewById(R.id.movetosignup)

        moveToSignUp.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SignUpFragment())
                .addToBackStack(null)
                .commit()
        }

        loginButton.setOnClickListener {
            val emailEditText = view.findViewById<TextInputEditText>(R.id.emailEditText)
            val passwordEditText = view.findViewById<TextInputEditText>(R.id.passwordEditText)

            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            login(email, password)
        }

        return view
    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Login Successful!", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, TaskCreationFragment())
                        .addToBackStack(null)
                        .commit()
                } else {
                    // Check if the exception is due to the user not existing
                    val errorMessage = task.exception?.message
                    if (task.exception is FirebaseAuthInvalidUserException) {
                        Toast.makeText(requireContext(), "Sorry, you don't have an account. Please create one first.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Sorry, you don't have an account. Please create one first.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}
