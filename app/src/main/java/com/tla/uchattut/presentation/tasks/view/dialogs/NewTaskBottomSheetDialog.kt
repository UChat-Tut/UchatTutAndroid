package com.tla.uchattut.presentation.tasks.view.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tla.uchattut.R
import com.tla.uchattut.data.db.model.TaskDbModel
import kotlinx.android.synthetic.main.dialog_bottom_sheet_new_task.*


class NewTaskBottomSheetDialog private constructor(
    private val onNewTask: (task: TaskDbModel) -> Unit,
    private val task: TaskDbModel?
) : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_bottom_sheet_new_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskEditText.setText(task?.content)
        saveBtton.setOnClickListener {
            val newTask = if (task == null) buildTask() else editTask(task)
            onNewTask(newTask)
            dismiss()
        }
    }

    private fun buildTask(): TaskDbModel = TaskDbModel(
        content = taskEditText.text.toString()
    )

    private fun editTask(task: TaskDbModel): TaskDbModel {
        task.content = taskEditText.text.toString()
        return task
    }

    companion object {
        fun show(
            fragmentManager: FragmentManager,
            onNewTask: (task: TaskDbModel) -> Unit,
            task: TaskDbModel? = null
        ) {
            val bottomSheetDialog = NewTaskBottomSheetDialog(onNewTask, task)
            bottomSheetDialog.show(fragmentManager, bottomSheetDialog.tag)
        }
    }
}