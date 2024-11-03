package com.example.todolistapp

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class DrawerMenu : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var auth: FirebaseAuth
    private lateinit var datePicker: DatePicker
    private lateinit var taskTimeInput: EditText // Add this
    private lateinit var timePickerDialog: TimePickerDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        drawerLayout = findViewById(R.id.drawerLayout)
        val navigationView: NavigationView = findViewById(R.id.nav_view)

        // Initialize DatePicker and EditText for time
        datePicker = findViewById(R.id.taskDueDateInput)
        //taskTimeInput = findViewById(R.id.taskTimeInput) // Initialize EditText

        // Set up navigation item selected listener
        navigationView.setNavigationItemSelectedListener { menuItem ->
            handleNavigationItemSelected(menuItem)
        }

        // Set up TimePickerDialog
        taskTimeInput.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                taskTimeInput.setText(String.format("%02d:%02d", selectedHour, selectedMinute)) // Formatting
            }, hour, minute, true)

            timePickerDialog.show()
        }

        // Save button click listener
        findViewById<Button>(R.id.saveButton).setOnClickListener {
            val selectedDate = "${datePicker.dayOfMonth}/${datePicker.month + 1}/${datePicker.year}"
            val selectedTime = taskTimeInput.text.toString() // Get time from EditText

            // Show selected date and time
            Toast.makeText(this, "Selected Date: $selectedDate\nSelected Time: $selectedTime", Toast.LENGTH_SHORT).show()
        }

        // Cancel button click listener
        findViewById<Button>(R.id.cancelButton).setOnClickListener {
            // Handle cancellation logic if necessary
            Toast.makeText(this, "Task entry cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.nav_sign_out -> {
                signOut()
                return true
            }
            // Handle other menu items here
        }
        return false // Indicate that we have not handled the item
    }

    private fun signOut() {
        auth.signOut() // Sign out from Firebase Auth
        Toast.makeText(this, "Signed Out Successfully!", Toast.LENGTH_SHORT).show()

        // Navigate back to the Login Fragment
        val loginFragment = LoginFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, loginFragment)
            .commit()

        drawerLayout.closeDrawers() // Close the drawer
    }
}
