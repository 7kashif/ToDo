package com.example.todo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todo.database.Tasks
import com.example.todo.database.TasksDatabaseDao
import kotlinx.coroutines.*

class TasksViewModel(val database: TasksDatabaseDao, application: Application):AndroidViewModel(application) {
    private var viewModelJob= Job()
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope= CoroutineScope(Dispatchers.Main+viewModelJob)
    private var lastTask=MutableLiveData<Tasks?>()
    val tasks=database.getAllTasks()
    private val _navigate=MutableLiveData<Int>()
    val navigate:LiveData<Int> get()=_navigate

    fun createTask(title:String, description:String) {
        uiScope.launch{
            if(title.isNotEmpty() && description.isNotEmpty()) {
                val task=Tasks()
                insert(task)
                update(title,description)
                _navigate.value=1
            } else
                _navigate.value=2
        }
    }

    private  suspend fun getLastTask(): Tasks? {
        return withContext(Dispatchers.IO) {
            val task=database.getLastTask()
            task
        }
    }

    private fun update(title:String, description:String) {
        uiScope.launch {
            val oldTask = getLastTask()
            if (oldTask != null) {
                oldTask.taskTitle = title
                oldTask.taskDescription = description
                update(oldTask)
            }
            _navigate.value = 1
        }
    }

    private suspend fun update(task: Tasks) {
        withContext(Dispatchers.IO) {
            database.update(task)
        }
    }

    private suspend fun insert(task:Tasks) {
        withContext(Dispatchers.IO) {
            database.insert(task)
        }
    }

    fun doneNavigation() {
        _navigate.value=3
    }

    fun onClear() {
        uiScope.launch {
            clear()
            lastTask.value=null
        }
    }

    private suspend fun clear(){
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    fun deleteSingleTask(taskId:Long) {
        uiScope.launch {
            delete(taskId)
        }
    }

    private suspend fun delete (taskId:Long){
        withContext(Dispatchers.IO) {
            database.delete(taskId)
        }
    }

}