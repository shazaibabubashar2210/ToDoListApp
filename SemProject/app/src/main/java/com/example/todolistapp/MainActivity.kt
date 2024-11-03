package com.example.todolistapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Initialize views
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.nav_view)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)

        // Set up the Toolbar
        setSupportActionBar(toolbar)

        // Enable the drawer toggle button on the toolbar
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.open, R.string.close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Handle Bottom Navigation Item Clicks
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
                    if (auth.currentUser == null) { // Check if user is logged in
                        showToast("Please create an account first to access tasks.")
                    } else {
                        loadFragment(TaskCreationFragment())
                    }
                    true
                }
                else -> false
            }
        }

        // Handle Drawer Navigation Item Clicks
        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    loadFragment(LoginFragment())
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_login -> {
                    loadFragment(LoginFragment())
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_signup -> {
                    loadFragment(SignUpFragment())
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_sign_out -> { // Handle Sign Out
                    signOut()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> false
            }
        }

        // Load default fragment
        loadFragment(LoginFragment())
    }

    // Helper function to load a fragment
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    // Sign out function
    private fun signOut() {
        auth.signOut() // Sign out from Firebase Auth
        showToast("Signed Out Successfully!") // Show a toast message

        // Navigate back to the Login Fragment
        loadFragment(LoginFragment()) // Load the LoginFragment
    }

    // Helper function to show a toast message
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    // Handle back button press to close the drawer if it's open
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
