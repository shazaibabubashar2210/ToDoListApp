package com.example.todolistapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Reference to FloatingActionButton
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            // Handle FAB click event
            val intent = Intent(this, NewTaskActivity::class.java)
            startActivity(intent)
        }

        // Reference to Menu Icon (Three dots)
        val menuIcon: ImageView = findViewById(R.id.menuIcon)
        menuIcon.setOnClickListener {
            // Create and show PopupMenu
            val popupMenu = PopupMenu(this, menuIcon)
            popupMenu.menuInflater.inflate(R.menu.menu_main, popupMenu.menu) // menu_main.xml needs to be created
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.task_lists -> {
                        Toast.makeText(this, "Task Lists Clicked", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.add_in_batch_mode -> {
                        val intent = Intent(this, AddInBatchModeActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.Sendfeedback -> {
                        // Open email app for feedback
                        sendFeedbackEmail()
                        true
                    }
                    R.id.settings -> {
                        // Navigate to SettingsActivity
                        val intent = Intent(this,  SettingsActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }

            popupMenu.show()
        }

        // Reference to Quick Task Input
        val quickTaskInput: EditText = findViewById(R.id.quickTaskInput)
        quickTaskInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                Toast.makeText(this, "Ready to input task", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Method to send feedback via email
    private fun sendFeedbackEmail() {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // Only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, arrayOf("bitf21m021@example.com"))
            putExtra(Intent.EXTRA_SUBJECT, "Feedback for ToDo List App")
            putExtra(Intent.EXTRA_TEXT, "Hello,\n\nI would like to share my feedback.\n\n")
        }

        try {
            startActivity(Intent.createChooser(emailIntent, "Send Feedback"))
        } catch (e: android.content.ActivityNotFoundException) {
            Toast.makeText(this, "No email clients installed.", Toast.LENGTH_SHORT).show()
        }
    }
}
