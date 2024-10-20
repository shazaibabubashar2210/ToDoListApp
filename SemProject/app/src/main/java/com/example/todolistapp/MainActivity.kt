package com.example.todolistapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_login -> {
                    loadFragment(LoginFragment())
                    true
                }
                R.id.nav_signup -> {
                    loadFragment(SignUpFragment())
                    true
                }
                R.id.nav_task_creation -> {
                    loadFragment(TaskCreationFragment())
                    true
                }
                else -> false
            }
        }

        // Load default fragment
        loadFragment(LoginFragment())
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
