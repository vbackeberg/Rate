package com.example.myapplication.viewadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.entities.Competency

class CompetenciesAdapter : RecyclerView.Adapter<CompetenciesAdapter.CompetencyViewHolder>() {
    private var competencies: List<Competency> = emptyList()

    fun updateData(newData: List<Competency>) {
        val diffResult = DiffUtil.calculateDiff(CompetenciesDiffUtilCallback(competencies, newData))
        this.competencies = newData
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompetencyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_competencies,
            parent,
            false
        )

        return CompetencyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CompetencyViewHolder, position: Int) {
    }

    override fun getItemCount() = competencies.size

    class CompetencyViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    }
}