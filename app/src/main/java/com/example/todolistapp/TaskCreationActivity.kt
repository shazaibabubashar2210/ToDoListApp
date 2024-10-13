package com.example.todolistapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.app.DatePickerDialog
import java.text.SimpleDateFormat
import java.util.*

class TaskCreationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_creation)

        val saveButton: Button = findViewById(R.id.saveTaskButton)
        val cancelButton: Button = findViewById(R.id.cancelButton)
        val dueDateInput: EditText = findViewById(R.id.dueDateInput)

        // Date picker for Due Date field
        dueDateInput.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    val selectedDate = "$dayOfMonth/${month + 1}/$year"
                    dueDateInput.setText(selectedDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }

        // Save task logic
        saveButton.setOnClickListener {
            Toast.makeText(this, "Task Saved", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Cancel button logic
        cancelButton.setOnClickListener {
            finish()
        }
    }
}
