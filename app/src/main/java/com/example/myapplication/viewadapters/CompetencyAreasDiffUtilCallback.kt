package com.example.myapplication.viewadapters

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.entities.CompetencyArea

class CompetencyAreasDiffUtilCallback(
    private val old: List<CompetencyArea>,
    private val new: List<CompetencyArea>

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