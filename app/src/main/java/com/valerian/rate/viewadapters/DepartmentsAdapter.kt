package com.valerian.rate.viewadapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.valerian.rate.R
import com.valerian.rate.entities.Department
import kotlinx.android.synthetic.main.item_departments.view.*

class DepartmentsAdapter(
    private val onItemClickListener: View.OnClickListener,
    private val onItemLongClickListener: View.OnLongClickListener
) : RecyclerView.Adapter<DepartmentsAdapter.DepartmentViewHolder>() {
    private var departments = emptyList<Department>()

    fun updateData(newData: List<Department>) {
        val diffResult = DiffUtil.calculateDiff(DiffUtilCallback(departments, newData))
        this.departments = newData
        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_departments,
            parent,
            false
        )

        return DepartmentViewHolder(view, onItemClickListener, onItemLongClickListener)
    }

    override fun onBindViewHolder(holder: DepartmentViewHolder, position: Int) {
        holder.bind(departments[position])
    }

    override fun getItemCount() = departments.size

    class DepartmentViewHolder(
        view: View,
        onItemClickListener: View.OnClickListener,
        onItemLongClickListener: View.OnLongClickListener
    ) :
        RecyclerView.ViewHolder(view) {
        init {
            itemView.setOnClickListener(onItemClickListener)
            itemView.setOnLongClickListener(onItemLongClickListener)
        }

        @SuppressLint("SetTextI18n")
        fun bind(department: Department) {
            itemView.tag = department
            itemView.departmentName.text = department.name
            itemView.departmentApplicantsCount.text = "Anzahl der Bewerber: 4"
        }
    }
}
