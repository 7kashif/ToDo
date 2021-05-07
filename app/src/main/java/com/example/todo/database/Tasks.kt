package com.example.todo.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks_table")
data class Tasks(
    @PrimaryKey(autoGenerate = true)
    var taskId:Long=0L,
    @ColumnInfo(name = "task_title")
    var taskTitle:String="title",
    @ColumnInfo(name = "task_description")
    var taskDescription:String="description",
)