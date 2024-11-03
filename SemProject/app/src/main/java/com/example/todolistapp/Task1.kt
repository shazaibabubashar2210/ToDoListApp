package com.example.todolistapp

data class Task1(
    val name: String,
    val description: String,
    val dueDate: String,
    val time: String,
    val priority: String,
    val isPermanent: Boolean = false // New property
)

