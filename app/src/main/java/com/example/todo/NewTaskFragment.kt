package com.example.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todo.database.TasksDatabase
import com.example.todo.databinding.FragmentNewTaskBinding
import com.google.android.material.snackbar.Snackbar


class NewTaskFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentNewTaskBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_new_task,container,false)


        val application= requireNotNull(this.activity).application
        val dataSource= TasksDatabase.getInstance(application).tasksDatabaseDao
        val viewModelFactory=TasksViewModelFactory(dataSource,application)
        val taskViewModel=ViewModelProvider(this,viewModelFactory).get(TasksViewModel::class.java)
        binding.lifecycleOwner=this
        binding.taskViewModel=taskViewModel

        taskViewModel.navigate.observe(viewLifecycleOwner,{
            if(it==1) {
                taskViewModel.doneNavigation()
                this.findNavController().navigate(NewTaskFragmentDirections.actionNewTaskFragmentToTaskFragment())
            }
            else if (it==2) {
                Snackbar.make(activity!!.findViewById(android.R.id.content),getString(R.string.empty_fields),Snackbar.LENGTH_SHORT)
                    .show()
            }
        })

        return binding.root
    }

}