package com.example.todo

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.todo.database.TasksDatabase
import com.example.todo.databinding.FragmentTaskBinding


class TaskFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentTaskBinding =DataBindingUtil.inflate(inflater,R.layout.fragment_task,container,false)

        binding.addTask.setOnClickListener {
            it.findNavController().navigate(TaskFragmentDirections.actionTaskFragmentToNewTaskFragment())
        }
        val application= requireNotNull(this.activity).application
        val dataSource= TasksDatabase.getInstance(application).tasksDatabaseDao
        val viewModelFactory= TasksViewModelFactory(dataSource,application)
        val taskViewModel= ViewModelProvider(this,viewModelFactory).get(TasksViewModel::class.java)
        binding.lifecycleOwner=this
        binding.taskViewModel=taskViewModel

       val adapter=TasksAdapter(TasksListener {
           this.findNavController().navigate(TaskFragmentDirections.actionTaskFragmentToTaskDetailFragment(it))
       }, DeleteClickListener {
           taskViewModel.deleteSingleTask(it)
       })
        binding.tasksList.adapter=adapter
        taskViewModel.tasks.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }
        })
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,requireView().findNavController())
                ||super.onOptionsItemSelected(item)
    }
}