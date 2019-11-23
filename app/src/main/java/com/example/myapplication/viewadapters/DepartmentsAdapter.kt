package com.example.myapplication.viewadapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.activities.Competencies
import com.example.myapplication.entities.Department
import kotlinx.android.synthetic.main.item_departments.view.*

class DepartmentsAdapter : RecyclerView.Adapter<DepartmentsAdapter.DepartmentViewHolder>() {
    private var departments: List<Department> = emptyList()

    fun updateData(newData: List<Department>) {
        val diffResult = DiffUtil.calculateDiff(DepartmentsDiffUtilCallback(departments, newData))
        this.departments = newData
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_departments,
            parent,
            false
        )

        return DepartmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: DepartmentViewHolder, position: Int) {
        holder.bind(departments[position])
    }

    override fun getItemCount() = departments.size

    class DepartmentViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.departmentName

        init {
            view.setOnClickListener {
                view.context.startActivity(Intent(view.context, Competencies::class.java))
            }
        }

        fun bind(department: Department) {
            name.text = department.name
        }
    }

}
