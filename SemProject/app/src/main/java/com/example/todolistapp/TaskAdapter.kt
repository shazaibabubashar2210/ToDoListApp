package com.example.todolistapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val tasks: MutableList<Task1>,
    private val onDeleteClick: (Int) -> Unit,
    private val onEditClick: (Task1, Int) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task)

        holder.itemView.findViewById<Button>(R.id.deleteButton).setOnClickListener {
            onDeleteClick(position)
        }

        holder.itemView.findViewById<Button>(R.id.editButton).setOnClickListener {
            onEditClick(task, position)
        }
    }

    override fun getItemCount(): Int = tasks.size

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskName: TextView = itemView.findViewById(R.id.taskName)
        val taskDescription: TextView = itemView.findViewById(R.id.taskDescription)
        val taskTime: TextView = itemView.findViewById(R.id.taskTime)
        val taskDueDate: TextView = itemView.findViewById(R.id.taskDueDate)

        fun bind(task: Task1) {
            taskName.text = task.name
            taskDescription.text = task.description
            taskTime.text = task.time
            taskDueDate.text = task.dueDate // Display the due date
        }
    }
}
