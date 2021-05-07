package com.example.todo

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.todo.database.Tasks


@BindingAdapter("setTitle")
fun TextView.setTitle(task: Tasks?) {
   task?.let {
        text=task.taskTitle
   }
}

@BindingAdapter("setDescription")
fun TextView.setDescription(task:Tasks?) {
    task?.let {
        text=task.taskDescription
    }
}