package com.tla.uchattut.presentation.tasks.view

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.tla.uchattut.R
import com.tla.uchattut.data.db.model.TaskDbModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_task.*


class TasksRecyclerAdapter(
    private val itemClickListener: OnTaskItemClickListener? = null
) : RecyclerView.Adapter<TasksRecyclerAdapter.TaskViewHolder>() {

    private var tasks = listOf<TaskDbModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view, itemClickListener)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    fun updateTasks(tasks: List<TaskDbModel>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    class TaskViewHolder(
        override val containerView: View,
        private val itemClickListener: OnTaskItemClickListener?
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(task: TaskDbModel) {
            contentTextView.text = task.content
            if (task.isCompleted) {
                completedToggleButton.isChecked = true
                contentTextView.setTextColor(containerView.resources.getColor(R.color.grey_transparent))
                contentTextView.paintFlags = contentTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                completedToggleButton.isChecked = false
                contentTextView.setTextColor(containerView.resources.getColor(android.R.color.black))
                contentTextView.paintFlags = contentTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }

            checkListenerLayout.setOnClickListener {
                itemClickListener?.onChangeTaskSelection(task.id)
            }

            containerView.setOnClickListener {
                val addDialogMenu = createAddDialogPopupMenu(containerView, this::onTaskMenuClick, task)
                addDialogMenu.show()
            }
        }

        private fun createAddDialogPopupMenu(
            view: View,
            onItemClick: (menuItem: MenuItem, task: TaskDbModel) -> Unit,
            task: TaskDbModel
        ): PopupMenu = PopupMenu(view.context, view).apply {
            menuInflater.inflate(R.menu.task_context, menu)
            setOnMenuItemClickListener {
                onItemClick(it, task)
                true
            }
        }

        private fun onTaskMenuClick(item: MenuItem, task: TaskDbModel) {
            when(item.itemId) {
                R.id.removeItem -> {
                    itemClickListener?.onRemoveTask(task.id)
                }
                R.id.editItem -> {
                    itemClickListener?.onEditTask(task.id, task)
                }
            }
        }
    }

    interface OnTaskItemClickListener {
        fun onChangeTaskSelection(id: Int)

        fun onRemoveTask(id: Int)

        fun onEditTask(id: Int, task: TaskDbModel)
    }
}