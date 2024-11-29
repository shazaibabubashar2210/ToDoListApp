package com.example.todolistapplication

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class AddInBatchModeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_in_batch_mode)

        // References to UI elements
        val taskInput: EditText = findViewById(R.id.task_input)
        val dueDateText: TextView = findViewById(R.id.due_date_text)
        val dueDateIcon: ImageView = findViewById(R.id.due_date_icon)
        val submitButton: ImageView = findViewById(R.id.submit_button)
        val listSpinner: Spinner = findViewById(R.id.list_spinner)
        val backArrow: ImageView = findViewById(R.id.back_arrow) // Reference to the back arrow

        // Handle Date Picker
        dueDateIcon.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    dueDateText.text = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                },
                year,
                month,
                day
            )
            datePicker.show()
        }

        // Handle Submit Button
        submitButton.setOnClickListener {
            val tasks = taskInput.text.toString()
            if (tasks.isBlank()) {
                Toast.makeText(this, "Please enter tasks!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Tasks added successfully!", Toast.LENGTH_SHORT).show()
                finish() // Close activity after submission
            }
        }

        // Handle Back Arrow Click
        backArrow.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent) // Launch MainActivity
            finish() // Close the current activity
        }
    }
}
