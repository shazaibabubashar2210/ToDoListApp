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

class SignUpFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        val signUpButton: Button = view.findViewById(R.id.signUpButton)
        val mtl = view.findViewById<TextView>(R.id.movetologin)

        mtl.setOnClickListener {
            parentFragmentManager.popBackStack() // Go back to LoginFragment
        }

        signUpButton.setOnClickListener {
            val emailEditText = view.findViewById<TextInputEditText>(R.id.emailEditText)
            val passwordEditText = view.findViewById<TextInputEditText>(R.id.passwordEditText)
            val confirmPasswordEditText = view.findViewById<TextInputEditText>(R.id.confirmPasswordEditText)

            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()

            // Enhanced input validation
            if (email.isEmpty()) {
                Toast.makeText(requireContext(), "Email is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                Toast.makeText(requireContext(), "Password is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            signUp(email, password)
        }

        return view
    }

    private fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Sign Up Successful!", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, LoginFragment())
                        .addToBackStack(null)
                        .commit()
                } else {
                    Toast.makeText(requireContext(), "Sign Up Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
