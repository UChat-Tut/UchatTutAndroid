package com.tla.uchattut.presentation.schedule.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tla.uchattut.R
import com.tla.uchattut.data.network.model.UserNetworkModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_found_student.*

class FoundStudentsAdapter(
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<FoundStudentsAdapter.StudentViewHolder>() {

    private var students = listOf<UserNetworkModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_found_student, parent, false)
        return StudentViewHolder(itemView)
    }

    override fun getItemCount(): Int = students.size

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(students[position], onClickListener)
    }

    class StudentViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(student: UserNetworkModel, onClickListener: OnClickListener) {
            Glide
                .with(containerView.context)
                .load(student.photoUrl)
                .into(foundStudentImage)
            foundStudentName.text = student.name
            foundStudentEmail.text = student.email
            containerView.setOnClickListener {
                onClickListener.onStudentClick(student)
            }
        }
    }

    fun setStudents(students: List<UserNetworkModel>) {
        this.students = students
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onStudentClick(student: UserNetworkModel)
    }
}