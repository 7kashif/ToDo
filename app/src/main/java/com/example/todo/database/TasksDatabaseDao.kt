package com.example.todo.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TasksDatabaseDao {
    @Insert
    fun insert(tasks: Tasks)

    @Update
    fun update(tasks: Tasks)

    @Query("DELETE FROM tasks_table")
    fun clear()

    @Query("SELECT* FROM tasks_table ORDER BY taskId DESC")
    fun getAllTasks(): LiveData<List<Tasks>>

    @Query("SELECT* FROM tasks_table ORDER BY taskId DESC LIMIT 1")
    fun getLastTask():Tasks?

    @Query("SELECT * FROM tasks_table WHERE taskId=:key")
    fun getTaskWithId(key:Long):LiveData<Tasks>

    @Query("DELETE FROM tasks_table WHERE taskId=:key")
    fun delete(key: Long)
}