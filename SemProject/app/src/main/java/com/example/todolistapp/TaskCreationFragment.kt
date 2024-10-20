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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_creation, container, false)

        // Setup RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        taskAdapter = TaskAdapter(taskList)
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
        dialog.show(parentFragmentManager, "TaskCreationDialog")
    }

    override fun onTaskCreated(task: Task1) {
        // Add the created task to the list and notify the adapter
        taskList.add(task)
        taskAdapter.notifyItemInserted(taskList.size - 1)
    }
}
