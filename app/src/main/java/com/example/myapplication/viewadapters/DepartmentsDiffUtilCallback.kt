package com.example.myapplication.viewadapters

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.entities.Department

class DepartmentsDiffUtilCallback(
    private val old: List<Department>,
    private val aNew: List<Department>

) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition].id == aNew[newItemPosition].id
    }

    override fun getOldListSize(): Int = old.size

    override fun getNewListSize(): Int = aNew.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition].name == aNew[newItemPosition].name
    }
}