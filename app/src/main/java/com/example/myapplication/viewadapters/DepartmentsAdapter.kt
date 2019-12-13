package com.example.myapplication.viewadapters

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.CURRENT_DEPARTMENT_ID
import com.example.myapplication.R
import com.example.myapplication.activities.Positions
import com.example.myapplication.entities.Department
import kotlinx.android.synthetic.main.item_departments.view.*

class DepartmentsAdapter : RecyclerView.Adapter<DepartmentsAdapter.DepartmentViewHolder>() {
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

        return DepartmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: DepartmentViewHolder, position: Int) {
        holder.bind(departments[position])
    }

    override fun getItemCount() = departments.size

    class DepartmentViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.departmentName
        private var id = 0L

        init {
            view.setOnClickListener {
                view.context.startActivity(Intent(view.context, Positions::class.java))

                view.context
                    .getSharedPreferences(CURRENT_DEPARTMENT_ID, MODE_PRIVATE)
                    .edit().putLong(CURRENT_DEPARTMENT_ID, id)
                    .apply()
            }
        }

        fun bind(department: Department) {
            name.text = department.name
            id = department.id
        }
    }

}
