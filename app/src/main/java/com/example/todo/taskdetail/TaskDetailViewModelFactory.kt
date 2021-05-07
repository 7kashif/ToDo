package com.example.todo.taskdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todo.database.TasksDatabaseDao

class TaskDetailViewModelFactory(private val taskId:Long,private val dataSource:TasksDatabaseDao):ViewModelProvider.Factory {
    @Suppress("Unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TaskDetailViewModel::class.java))
            return TaskDetailViewModel(taskId,dataSource)as T
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}