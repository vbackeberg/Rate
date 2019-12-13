package com.example.myapplication.viewadapters

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.entities.Id

class DiffUtilCallback<T : Id>(
    private val old: List<T>,
    private val new: List<T>

) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition].id == new[newItemPosition].id
    }

    override fun getOldListSize(): Int = old.size

    override fun getNewListSize(): Int = new.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition] == (new[newItemPosition])
    }
}