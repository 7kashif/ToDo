package com.example.todo.taskdetail

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.todo.database.Tasks
import com.example.todo.database.TasksDatabaseDao

class TaskDetailViewModel(private val taskId:Long=0L,dataSource:TasksDatabaseDao): ViewModel() {
    val database=dataSource
    private val task=MediatorLiveData<Tasks>()
    fun getTask()=task
    init {
        task.addSource(dataSource.getTaskWithId(taskId),task::setValue)
    }
}