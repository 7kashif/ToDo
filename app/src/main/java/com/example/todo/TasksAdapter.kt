package com.example.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.database.Tasks
import com.example.todo.databinding.ListItemTaskBinding

class TasksAdapter(private val clickListener:TasksListener,private val deleteClickListener: DeleteClickListener):ListAdapter<Tasks,TasksAdapter.ViewHolder>(TasksListDiffCallBack()) {

    class ViewHolder private constructor(private val binding: ListItemTaskBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Tasks, tasksListener: TasksListener, deleteClickListener: DeleteClickListener) {
           binding.apply {
               tasks=item
               clickListener=tasksListener
               deleteClick=deleteClickListener
           }
        }
        companion object {
            fun from(parent:ViewGroup) : ViewHolder {
                val layoutInflater=LayoutInflater.from(parent.context)
                val binding=ListItemTaskBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position),clickListener,deleteClickListener)
    }
}

class TasksListDiffCallBack:DiffUtil.ItemCallback<Tasks>() {
    override fun areItemsTheSame(oldItem: Tasks, newItem: Tasks): Boolean {
        return oldItem.taskId==newItem.taskId
    }

    override fun areContentsTheSame(oldItem: Tasks, newItem: Tasks): Boolean {
        return oldItem==newItem
    }
}

class TasksListener(val clickListener:(taskId:Long)->Unit){
    fun onClick(task:Tasks)=clickListener(task.taskId)
}

class DeleteClickListener(val clickListener:(taskId:Long)->Unit) {
    fun onDeleteClicked(task:Tasks)=clickListener(task.taskId)
}