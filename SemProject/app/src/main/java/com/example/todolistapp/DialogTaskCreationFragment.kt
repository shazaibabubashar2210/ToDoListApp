package com.example.todolistapp

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import java.util.*

class DialogTaskCreationFragment : DialogFragment() {

    private lateinit var taskNameInput: EditText
    private lateinit var taskDescriptionInput: EditText
    private lateinit var taskDueDateInput: EditText
    private lateinit var taskTimeInput: EditText
    private lateinit var prioritySpinner: Spinner
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button

    private var listener: OnTaskCreatedListener? = null
    private var taskToEdit: Task1? = null

    interface OnTaskCreatedListener {
        fun onTaskCreated(task: Task1)
    }

    fun setOnTaskCreatedListener(listener: OnTaskCreatedListener) {
        this.listener = listener
    }

    fun setTaskDetails(task: Task1) {
        this.taskToEdit = task
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog_task_creation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskNameInput = view.findViewById(R.id.taskNameInput)
        taskDescriptionInput = view.findViewById(R.id.taskDescriptionInput)
        taskDueDateInput = view.findViewById(R.id.taskDueDateInput)
        taskTimeInput = view.findViewById(R.id.taskTimeInput)
        prioritySpinner = view.findViewById(R.id.taskPrioritySpinner)
        saveButton = view.findViewById(R.id.saveButton)
        cancelButton = view.findViewById(R.id.cancelButton)

        val priorities = arrayOf("Low", "Medium", "High")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, priorities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        prioritySpinner.adapter = adapter

        taskDueDateInput.setOnClickListener { showDatePickerDialog() }
        taskTimeInput.setOnClickListener { showTimePickerDialog() }

        taskToEdit?.let { setTaskInputs(it) }

        saveButton.setOnClickListener { saveTask() }
        cancelButton.setOnClickListener { dismiss() }
    }

    private fun setTaskInputs(task: Task1) {
        taskNameInput.setText(task.name)
        taskDescriptionInput.setText(task.description)
        taskDueDateInput.setText(task.dueDate)
        taskTimeInput.setText(task.time)

        // Set the selected priority in the Spinner
        val priorities = resources.getStringArray(R.array.task_priority_array)
        val priorityIndex = priorities.indexOf(task.priority)
        prioritySpinner.setSelection(priorityIndex)
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
                taskDueDateInput.setText(formattedDate)
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, selectedHour, selectedMinute ->
                val formattedHour = if (selectedHour == 0) 12 else selectedHour % 12
                val formattedTime = String.format("%02d:%02d", formattedHour, selectedMinute) + if (selectedHour < 12) " AM" else " PM"
                taskTimeInput.setText(formattedTime)
            },
            hour, minute, false
        )
        timePickerDialog.show()
    }

    private fun saveTask() {
        val taskName = taskNameInput.text.toString().trim()
        val taskDescription = taskDescriptionInput.text.toString().trim()
        val taskDueDate = taskDueDateInput.text.toString().trim()
        val taskTime = taskTimeInput.text.toString().trim()
        val taskPriority = prioritySpinner.selectedItem.toString()

        if (taskName.isNotEmpty()) {
            val task = Task1(
                name = taskName,
                description = taskDescription,
                dueDate = taskDueDate,
                time = taskTime,
                priority = taskPriority
            )

            // Schedule the alarm
            scheduleAlarm(taskDueDate, taskTime, taskName)

            listener?.onTaskCreated(task)
            dismiss()
        } else {
            Toast.makeText(requireContext(), "Please enter a task name", Toast.LENGTH_SHORT).show()
        }
    }

    private fun scheduleAlarm(dueDate: String, time: String, taskName: String) {
        val calendar = Calendar.getInstance()
        val dateParts = dueDate.split("-")

        calendar.set(Calendar.YEAR, dateParts[0].toInt())
        calendar.set(Calendar.MONTH, dateParts[1].toInt() - 1) // Month is 0-based
        calendar.set(Calendar.DAY_OF_MONTH, dateParts[2].toInt())

        // Handle time input correctly
        val timeParts = time.split(":")
        if (timeParts.size == 2) {
            val hourPart = timeParts[0].trim()
            val minutePart = timeParts[1].trim().take(2) // Take the first two digits

            // Check if the hour is in the correct range
            val hour: Int = try {
                hourPart.toInt().let {
                    if (it > 12 || it < 1) throw NumberFormatException("Invalid hour")
                    it
                }
            } catch (e: NumberFormatException) {
                // Handle invalid hour format
                Toast.makeText(requireContext(), "Invalid hour format", Toast.LENGTH_SHORT).show()
                return // Exit the function if there's an error
            }

            val minute: Int = try {
                minutePart.toInt()
            } catch (e: NumberFormatException) {
                // Handle invalid minute format
                Toast.makeText(requireContext(), "Invalid minute format", Toast.LENGTH_SHORT).show()
                return // Exit the function if there's an error
            }

            // Adjust the hour for AM/PM
            val amPm = timeParts[1].trim().takeLast(2) // Take "AM" or "PM"
            if (amPm.equals("PM", ignoreCase = true) && hour != 12) {
                calendar.set(Calendar.HOUR_OF_DAY, hour + 12)
            } else if (amPm.equals("AM", ignoreCase = true) && hour == 12) {
                calendar.set(Calendar.HOUR_OF_DAY, 0) // Midnight case
            } else {
                calendar.set(Calendar.HOUR_OF_DAY, hour)
            }

            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)

            // Continue with setting the alarm
            val intent = Intent(requireContext(), AlarmReceiver::class.java)
            intent.putExtra("task_name", taskName)

            val pendingIntent = PendingIntent.getBroadcast(
                requireContext(),
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        } else {
            // Handle incorrect time format
            Toast.makeText(requireContext(), "Please enter a valid time (hh:mm AM/PM)", Toast.LENGTH_SHORT).show()
        }
    }
}
