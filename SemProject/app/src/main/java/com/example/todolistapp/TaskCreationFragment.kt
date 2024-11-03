package com.example.todolistapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TaskCreationFragment : Fragment(), DialogTaskCreationFragment.OnTaskCreatedListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private val taskList = mutableListOf<Task1>()
    private var editingPosition: Int? = null // Track the position of the editing task

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_creation, container, false)

        // Setup RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize the adapter and set it to the RecyclerView
        taskAdapter = TaskAdapter(taskList, { position -> deleteTask(position) }) { task, position ->
            editTask(task, position)
        }
        recyclerView.adapter = taskAdapter

        // Setup Add Task button
        val addTaskButton: Button = view.findViewById(R.id.addTaskButton)
        addTaskButton.setOnClickListener {
            showDialog()
        }

        return view
    }

    private fun showDialog() {
        val dialog = DialogTaskCreationFragment()
        dialog.setOnTaskCreatedListener(this)
        editingPosition?.let { dialog.setTaskDetails(taskList[it]) } // Pass existing task details if editing
        dialog.show(parentFragmentManager, "TaskCreationDialog")
    }

    override fun onTaskCreated(task: Task1) {
        editingPosition?.let { // If we are editing a task
            taskList[it] = task // Update existing task
            editingPosition = null // Reset editing position
        } ?: run { // If we are adding a new task
            taskList.add(task) // Add the created task to the list
        }
        taskAdapter.notifyDataSetChanged() // Notify adapter about data change
    }

    private fun deleteTask(position: Int) {
        if (position in taskList.indices) {
            taskList.removeAt(position)
            taskAdapter.notifyItemRemoved(position)
        }
    }

    private fun editTask(task: Task1, position: Int) {
        editingPosition = position // Set the position of the task being edited
        showDialog() // Show the dialog with task details
    }
}
