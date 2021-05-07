package com.example.todo.taskdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todo.R
import com.example.todo.database.TasksDatabase
import com.example.todo.databinding.FragmentTaskDetailBinding


class TaskDetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val binding:FragmentTaskDetailBinding=DataBindingUtil.inflate(inflater,
           R.layout.fragment_task_detail,container,false)
        val  application= requireNotNull(this.activity).application
        val dataSource= TasksDatabase.getInstance(application).tasksDatabaseDao
        val arguments=TaskDetailFragmentArgs.fromBundle(arguments!!)
        val viewModelFactory=TaskDetailViewModelFactory(arguments.taskId,dataSource)
        val taskDetailViewModel=ViewModelProvider(this,viewModelFactory).get(TaskDetailViewModel::class.java)

        binding.taskDetailViewModel=taskDetailViewModel
        binding.lifecycleOwner=this

        binding.closeButton.setOnClickListener {
            this.findNavController().navigate(TaskDetailFragmentDirections.actionTaskDetailFragmentToTaskFragment())
        }
        return binding.root
    }
}