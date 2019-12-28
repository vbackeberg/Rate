package com.example.myapplication.viewadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.entities.Department
import kotlinx.android.synthetic.main.item_departments.view.*

class DepartmentsAdapter(
    private val onItemClickListener: View.OnClickListener
) : RecyclerView.Adapter<DepartmentsAdapter.DepartmentViewHolder>() {
    private var departments: List<Department> = emptyList()

    fun updateData(newData: List<Department>) {
        val diffResult = DiffUtil.calculateDiff(DiffUtilCallback(departments, newData))
        this.departments = newData
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_departments,
            parent,
            false
        )

        return DepartmentViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: DepartmentViewHolder, position: Int) {
        holder.bind(departments[position])
    }

    override fun getItemCount() = departments.size

    class DepartmentViewHolder(view: View, onItemClickListener: View.OnClickListener) :
        RecyclerView.ViewHolder(view) {
        var id = 0L
        val name = itemView.departmentName
        private val applicantsCount = itemView.departmentApplicantsCount

        init {
            itemView.tag = this
            itemView.setOnClickListener(onItemClickListener)
        }

        fun bind(department: Department) {
            name.text = department.name
            applicantsCount.text = "Anzahl an Bewerbern: 4"
            id = department.id
        }
    }
}
