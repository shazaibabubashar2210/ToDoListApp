package com.example.todolistapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class SignUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)

        val signUpButton: Button = view.findViewById(R.id.signUpButton)
        val mtl = view.findViewById<TextView>(R.id.movetologin)

        mtl.setOnClickListener {
            parentFragmentManager.popBackStack() // Go back to LoginFragment
        }

        signUpButton.setOnClickListener {
            Toast.makeText(requireContext(), "Sign Up Successful!", Toast.LENGTH_SHORT).show()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LoginFragment())
                .addToBackStack(null)
                .commit()
        }

        return view
    }
}
