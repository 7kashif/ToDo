package com.example.todo

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todo.database.TasksDatabaseDao

class TasksViewModelFactory(
    private val taskDatabaseDao: TasksDatabaseDao,
    private val application: Application):ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TasksViewModel::class.java))
            return TasksViewModel(taskDatabaseDao,application) as T
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}