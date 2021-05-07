package com.example.todo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Tasks::class],version = 5,exportSchema = false)
abstract class TasksDatabase: RoomDatabase() {
    abstract val tasksDatabaseDao:TasksDatabaseDao
    companion object{
        @Volatile
        private var INSTANCE:TasksDatabase?=null
        fun getInstance(context: Context):TasksDatabase{
            var instance= INSTANCE
            if(instance==null) {
                instance= Room.databaseBuilder(context.applicationContext,TasksDatabase::class.java,"tasks_database")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE=instance
            }
            return instance
        }
    }
}