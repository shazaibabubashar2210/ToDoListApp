package com.example.todolistapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import java.util.*

class DialogTaskCreationFragment : DialogFragment() {

    private lateinit var taskNameInput: EditText
    private lateinit var taskDescriptionInput: EditText
    private lateinit var taskDueDateInput: EditText
    private lateinit var prioritySpinner: Spinner
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button

    private var listener: OnTaskCreatedListener? = null

    interface OnTaskCreatedListener {
        fun onTaskCreated(task: Task1)
    }

    fun setOnTaskCreatedListener(listener: OnTaskCreatedListener) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog_task_creation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        taskNameInput = view.findViewById(R.id.taskNameInput)
        taskDescriptionInput = view.findViewById(R.id.taskDescriptionInput)
        taskDueDateInput = view.findViewById(R.id.taskDueDateInput)
        prioritySpinner = view.findViewById(R.id.taskPrioritySpinner)
        saveButton = view.findViewById(R.id.saveButton)
        cancelButton = view.findViewById(R.id.cancelButton)

        // Set up Spinner for task priority
        val priorities = arrayOf("Low", "Medium", "High")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, priorities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        prioritySpinner.adapter = adapter

        // Set up DatePickerDialog for due date input
        taskDueDateInput.setOnClickListener {
            showDatePickerDialog()
        }

        // Save button click listener
        saveButton.setOnClickListener {
            saveTask()
        }

        // Cancel button click listener
        cancelButton.setOnClickListener {
            dismiss()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                // Format the date and set it in the EditText
                val formattedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
                taskDueDateInput.setText(formattedDate)
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun saveTask() {
        val taskName = taskNameInput.text.toString().trim()
        val taskDescription = taskDescriptionInput.text.toString().trim()
        val taskDueDate = taskDueDateInput.text.toString().trim()
        val taskPriority = prioritySpinner.selectedItem.toString() // Get selected priority

        if (taskName.isEmpty() || taskDescription.isEmpty() || taskDueDate.isEmpty()) {
            // Show an error message if fields are empty
            return
        }

        // Create a new task object with the collected data
        val newTask = Task1(
            taskName,
            taskDescription,
            dueDate = taskDueDate,
            priority = taskPriority  // Pass the priority
        )
        listener?.onTaskCreated(newTask)
        dismiss()
    }
}
