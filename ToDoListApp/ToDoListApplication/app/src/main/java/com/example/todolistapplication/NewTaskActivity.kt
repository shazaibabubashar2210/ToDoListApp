package com.example.todolistapplication

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.todolistapplication.R.id.backButton
import java.util.*

class NewTaskActivity : AppCompatActivity() {

    private lateinit var backButton: ImageView
    private lateinit var editTextTask: EditText
    private lateinit var textViewDueDate: TextView
    private lateinit var calendarIcon: ImageView
    private lateinit var buttonSave: ImageButton
    private lateinit var addToListIcon: ImageView
    private lateinit var microphoneIcon: ImageView
    private lateinit var dropdownSpinner: Spinner
    private lateinit var dropdownIcon: ImageView  // Reference for the dropdown icon

    private val lists = mutableListOf("Default") // Initial list options

    private val speechRecognizer: SpeechRecognizer by lazy { SpeechRecognizer.createSpeechRecognizer(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task2)

        // Initialize Views
        backButton = findViewById(R.id.backButton)
        editTextTask = findViewById(R.id.taskInput)
        textViewDueDate = findViewById(R.id.dueDate)
        calendarIcon = findViewById(R.id.calendarIcon)
        buttonSave = findViewById(R.id.saveTaskButton)
        addToListIcon = findViewById(R.id.addToListIcon)
        microphoneIcon = findViewById(R.id.microphoneIcon)
        dropdownSpinner = findViewById(R.id.addToListSpinner)
        dropdownIcon = findViewById(R.id.dropdown)  // Initialize dropdown icon reference

        // Custom layout for Spinner items
        val adapter = ArrayAdapter(this, R.layout.custom_spinner_item, lists).apply {
            setDropDownViewResource(R.layout.custom_spinner_dropdown_item) // Use custom dropdown item layout
        }
        dropdownSpinner.adapter = adapter

        // Add to list icon logic
        addToListIcon.setOnClickListener {
            showAddListDialog(adapter)
        }

        // Calendar icon click
        calendarIcon.setOnClickListener {
            showDatePickerDialog()
        }

        // Save button logic
        buttonSave.setOnClickListener {
            saveTask()
        }

        // Back button logic
        backButton.setOnClickListener {
            finish() // Close the current activity
        }

        // Microphone icon logic (Voice Input)
        microphoneIcon.setOnClickListener {
            startVoiceInput()
        }

        // Set up the speech recognition listener
        speechRecognizer.setRecognitionListener(object : android.speech.RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onError(error: Int) {
                Toast.makeText(applicationContext, "Speech recognition error", Toast.LENGTH_SHORT).show()
            }

            override fun onResults(results: Bundle?) {
                val data = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (data != null && data.isNotEmpty()) {
                    editTextTask.setText(data[0]) // Set the recognized speech as the task
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })

        // Dropdown icon click to open spinner dropdown
        dropdownIcon.setOnClickListener {
            dropdownSpinner.performClick()  // Simulate a click on the spinner to open the dropdown
        }
    }

    private fun showAddListDialog(adapter: ArrayAdapter<String>) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("New List")

        val input = EditText(this)
        input.hint = "Enter List Name"
        builder.setView(input)

        builder.setPositiveButton("Add") { dialog, _ ->
            val listName = input.text.toString().trim()
            if (listName.isNotEmpty()) {
                lists.add(listName)
                adapter.notifyDataSetChanged()  // Notify adapter of the change
                Toast.makeText(this, "List '$listName' added!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "List name cannot be empty!", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
        builder.create().show()
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            textViewDueDate.text = formattedDate
        }, year, month, day)
        datePickerDialog.show()
    }

    private fun saveTask() {
        val task = editTextTask.text.toString().trim()
        val dueDate = textViewDueDate.text.toString()
        val selectedList = dropdownSpinner.selectedItem.toString()

        if (task.isBlank()) {
            Toast.makeText(this, "Task cannot be empty!", Toast.LENGTH_SHORT).show()
        } else if (dueDate == "Date not set") {
            Toast.makeText(this, "Please set a due date", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task added to list: $selectedList", Toast.LENGTH_SHORT).show()
            finish() // Close activity
        }
    }

    private fun startVoiceInput() {
        // Check if speech recognition is available
        if (SpeechRecognizer.isRecognitionAvailable(this)) {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say your task") // Prompt message
            speechRecognizer.startListening(intent)
        } else {
            Toast.makeText(this, "Speech recognition is not available", Toast.LENGTH_SHORT).show()
        }
    }
}
