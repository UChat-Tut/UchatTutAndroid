package com.tla.uchattut.presentation.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tla.uchattut.R
import com.tla.uchattut.data.db.model.TaskDbModel
import com.tla.uchattut.di.DaggerContainer
import com.tla.uchattut.presentation._common.BaseFragment
import com.tla.uchattut.presentation._common.viewModel
import com.tla.uchattut.presentation.tasks.dialogs.NewTaskBottomSheetDialog
import kotlinx.android.synthetic.main.fragment_tasks.*
import javax.inject.Inject

class TasksFragment : BaseFragment(),
    TasksRecyclerAdapter.OnTaskItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        viewModel<TasksViewModel>(viewModelFactory)
    }

    private lateinit var tasksAdapter: TasksRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerContainer.tasksComponent(this)
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tasksAdapter =
            TasksRecyclerAdapter(this)
        tasksRecyclerView.adapter = tasksAdapter
        tasksRecyclerView.layoutManager = LinearLayoutManager(context!!)

        addTaskButton.setOnClickListener {
            NewTaskBottomSheetDialog.show(childFragmentManager, this::addTask)
        }

        viewModel.getTasksLiveData().observe(viewLifecycleOwner, Observer { tasks ->
            tasksAdapter.updateTasks(tasks)
        })

        viewModel.getPutDownTaskEvent().observe(viewLifecycleOwner, Observer {
            tasksRecyclerView.scrollToPosition(tasksAdapter.itemCount - 1)
        })

        viewModel.loadTasks()
    }

    private fun addTask(task: TaskDbModel) {
        viewModel.addTask(task)
    }

    override fun onChangeTaskSelection(id: Int) {
        viewModel.switchCompletionTask(id)
    }

    override fun onRemoveTask(id: Int) {
        viewModel.removeTask(id)
    }

    override fun onEditTask(id: Int, task: TaskDbModel) {
        NewTaskBottomSheetDialog.show(childFragmentManager, this::addTask, task = task)
    }

    companion object {
        const val TAG = "TasksFragment"
    }
}