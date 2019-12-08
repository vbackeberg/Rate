package com.example.myapplication.viewadapters

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.entities.Position

class PositionsDiffUtilCallback(
    private val old: List<Position>,
    private val new: List<Position>

) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition].id == new[newItemPosition].id
    }

    override fun getOldListSize(): Int = old.size

    override fun getNewListSize(): Int = new.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition].name == new[newItemPosition].name
    }
}